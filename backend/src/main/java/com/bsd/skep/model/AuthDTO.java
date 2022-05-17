package com.bsd.skep.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthDTO {

    private boolean success;
    private String message;
    private String username;

    public AuthDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthDTO(String username) {
        this.success = true;
        this.username = username;
    }
}
