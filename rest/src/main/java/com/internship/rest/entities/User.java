package com.internship.rest.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is required")
    private String name;

    @NotNull(message = "Field is required")
    private String surname;

    @NotNull(message = "Field is required")
    private Integer age;

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.age = userDTO.getAge();
        this.surname = userDTO.getSurname();
    }
}
