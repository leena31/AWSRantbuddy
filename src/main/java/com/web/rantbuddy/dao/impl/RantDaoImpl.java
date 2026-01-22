package com.web.rantbuddy.dao.impl;

import com.web.rantbuddy.dao.RantDao;
import com.web.rantbuddy.model.RantDetails;
import org.springframework.stereotype.Repository;

@Repository
public class RantDaoImpl implements RantDao {

    @Override
    public boolean saveRantDetails(RantDetails rantDetails) {
        return false;
    }
}
