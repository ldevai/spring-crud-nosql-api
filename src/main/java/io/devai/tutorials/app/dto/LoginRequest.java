package io.devai.tutorials.app.dto;

import lombok.Data;

@Data
public class LoginRequest {
    protected String email;
    protected String password;
}
