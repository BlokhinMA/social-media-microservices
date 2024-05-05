package ru.sstu.users.models;

import ru.sstu.users.models.enums.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String password;
    private String role;
    //private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

}
