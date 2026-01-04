package com.web.rantbuddy.service;

import com.web.rantbuddy.model.LoginResponse;
import com.web.rantbuddy.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    public boolean register(User user);

    public LoginResponse login(String username, String password);
}
