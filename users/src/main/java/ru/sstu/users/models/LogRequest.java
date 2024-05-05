package ru.sstu.users.models;

import lombok.Data;

@Data
public class LogRequest {

    private String login;
    private String password;

}
