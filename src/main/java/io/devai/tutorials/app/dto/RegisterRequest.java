package io.devai.tutorials.app.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    String email;
    String password;
    String name;
}
