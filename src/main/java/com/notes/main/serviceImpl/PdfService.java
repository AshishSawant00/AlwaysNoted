package com.notes.main.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

	public byte[] generatePdf(String content) throws IOException {

		try (PDDocument document = new PDDocument()) {

			addNewPage(document, content);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			document.save(baos);

			return baos.toByteArray();

		}

	}

	private void addNewPage(PDDocument document, String content) throws IOException {

		PDPage page = new PDPage();

		document.addPage(page);

		float margin = 20;

		float yStart = page.getMediaBox().getHeight() - margin;

		float yPosition = yStart;

		try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			contentStream.beginText();

			contentStream.newLineAtOffset(margin, yPosition);

			String[] lines = content.split("\\r?\\n");

			for (String line : lines) {

				float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(line) / 1000 * 12;

				float remainingWidth = page.getMediaBox().getWidth() - 2 * margin;

				if (textWidth > remainingWidth) {

					// Word wrap - break line into parts that fit within the page width

					int lastSpace = line.lastIndexOf(' ', (int) (remainingWidth / 12 * 1000));

					if (lastSpace > 0) {

						line = line.substring(0, lastSpace).trim();

					}

				}

				contentStream.showText(line);

				yPosition -= 12;

				if (yPosition < margin) {

					// Move to the next page if the remaining space is not sufficient

					contentStream.endText();

					contentStream.close();

					addNewPage(document, line);

					return;

				}

				contentStream.newLineAtOffset(0, -12);

			}

			contentStream.endText();

		}

	}
}
