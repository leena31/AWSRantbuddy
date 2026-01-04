package com.web.rantbuddy.dao.impl;

import com.web.rantbuddy.dao.AuthDao;
import com.web.rantbuddy.model.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDaoImpl implements AuthDao {


    @Override
    public boolean register(UserDetails userDetails) {
        return false;
    }
}
