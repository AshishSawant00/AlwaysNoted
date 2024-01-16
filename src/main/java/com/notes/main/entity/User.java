	package com.notes.main.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;
import org.hibernate.annotations.Where;

@Entity
@Table(name  = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//soft delete
@SQLDelete(sql = "UPDATE users SET deleted_at = current_timestamp() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class User extends AbstractDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @Column(name = "user_name")
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    
    @Column(unique = true)
    @UuidGenerator(style = Style.AUTO)
    private String uuid;
}
