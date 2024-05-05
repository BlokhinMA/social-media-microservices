package ru.sstu.users.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegRequest {

    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String password;

}
