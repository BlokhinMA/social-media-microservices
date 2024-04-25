package ru.sstu.authentication.models;

import lombok.Data;

@Data
public class LogRequest {

    private String login;
    private String password;

}
