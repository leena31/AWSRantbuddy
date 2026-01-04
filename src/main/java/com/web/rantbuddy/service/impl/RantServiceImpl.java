package com.web.rantbuddy.service.impl;

import com.web.rantbuddy.model.Rant;
import com.web.rantbuddy.service.RantService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RantServiceImpl implements RantService {

    private final Map<String, Rant> rants = new HashMap<>();
    @Override
    public Rant createRant(String userId, String rantText) {
        String rantId = UUID.randomUUID().toString();
        Rant rant = new Rant(rantId, userId, rantText, "PROCESSING");
        rants.put(rantId, rant);
        return rant;
    }

    @Override
    public Rant getRant(String rantId,String userId) {
        return rants.get(rantId);
    }

    @Override
    public List<Rant> getAllRants(String userId) {
        List<Rant> list = new ArrayList<>();
        for (Rant r : rants.values()) {
            if (r.getUserId().equals(userId))
                list.add(r);
        }
        return list;
    }
}
