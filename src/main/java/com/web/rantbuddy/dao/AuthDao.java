package com.web.rantbuddy.dao;

import com.web.rantbuddy.model.UserDetails;

public interface AuthDao {

    public boolean register(UserDetails userDetails);

}
