package com.internship.rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private Integer age;

    public UserDTO(User user) {
        this.id = user.getId();
        this.age = user.getAge();
        this.name = user.getName();
        this.surname = user.getSurname();
    }
}
