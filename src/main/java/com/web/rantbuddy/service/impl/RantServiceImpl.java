package com.web.rantbuddy.service.impl;

import com.web.rantbuddy.dao.RantDao;
import com.web.rantbuddy.model.Rant;
import com.web.rantbuddy.model.RantDetails;
import com.web.rantbuddy.service.EventPublisher;
import com.web.rantbuddy.service.RantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RantServiceImpl implements RantService {

    @Autowired
    RantDao rantDao;
    @Autowired
    private EventPublisher eventPublisher;

    private final Map<String, Rant> rants = new HashMap<>();
    @Override
    public Rant createRant(String username, String rantText) {
        String rantId = UUID.randomUUID().toString();
        Rant rant = new Rant();
        rant.setRantId(rantId);
        rant.setStatus("PENDING");
        RantDetails rantDetails = new RantDetails();
        rantDetails.setRantId(rantId);
        rantDetails.setUsername(username);
        rantDetails.setStatus("PENDING");
        rantDetails.setTimestamp(LocalDateTime.now().toString());
        rantDao.saveRantDetails(rantDetails);
        eventPublisher.publishRantCreatedEvent(rantId);


        return rant;
    }

    @Override
    public Rant getRant(String rantId,String userId) {

        return rants.get(rantId);
    }

    @Override
    public List<Rant> getAllRants(String userId) {
        List<Rant> list = new ArrayList<>();

        return list;
    }
}
