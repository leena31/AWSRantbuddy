package com.web.rantbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String expiredIn;
    private String username;

}
