package io.devai.tutorials.app.dto;

import io.devai.tutorials.app.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    protected String email;
    protected String name;
    protected List<Role> roles;
    protected String accessToken;
    protected String error;
}
