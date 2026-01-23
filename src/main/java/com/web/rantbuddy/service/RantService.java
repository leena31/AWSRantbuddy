package com.web.rantbuddy.service;

import com.web.rantbuddy.model.Rant;
import com.web.rantbuddy.model.RantDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RantService{
        public RantDetails createRant(String username, String rantText) ;

        public Rant getRant(String rantId,String username);

        public List<Rant> getAllRants(String username);
}
