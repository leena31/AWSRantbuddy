package com.web.rantbuddy.service;

import com.web.rantbuddy.model.Rant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RantService{
        public Rant createRant(String username, String rantText) ;

        public Rant getRant(String rantId,String username);

        public List<Rant> getAllRants(String username);
}
