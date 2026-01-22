package com.web.rantbuddy.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.rantbuddy.model.Rant;
import com.web.rantbuddy.model.RantRequest;
import com.web.rantbuddy.service.RantService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rant")
@RequiredArgsConstructor
public class RantController {
    @Autowired
    RantService rantService;

    @PostMapping(value = "/createRant")
    public ResponseEntity<?> createRant(@RequestBody RantRequest rantRequest,
                                        @RequestHeader("Authorization") String authorization
    ) {
        String token = authorization.replace("Bearer ", "");

        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getClaim("cognito:username").asString();
        Rant rant = rantService.createRant(username, rantRequest.getRantText());
        return ResponseEntity.ok(Map.of(
                "rantId", rant.getRantId(),
                "status", rant.getStatus(),
                "message", "Your rant is being analyzed"
        ));
    }

    @GetMapping("/getRant/{rantId}")
    public ResponseEntity<?> getRant(@PathVariable String rantId,@RequestHeader("x-amzn-oidc-data") String oidcData) {
        // Decode the header (base64) to get JWT claims
        String[] tokenParts = oidcData.split("\\.");
        String payload = new String(Base64.getDecoder().decode(tokenParts[1]));
        JSONObject json = new JSONObject(payload);

        String username = json.getString("cognito:username");

        Rant rant = rantService.getRant(rantId,username);
        if (rant == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rant);
    }

    @GetMapping("/getAllRants")
    public ResponseEntity<?> getAllRants(@RequestHeader("x-amzn-oidc-data") String oidcData) {
        // Decode the header (base64) to get JWT claims
        String[] tokenParts = oidcData.split("\\.");
        String payload = new String(Base64.getDecoder().decode(tokenParts[1]));
        JSONObject json = new JSONObject(payload);

        String username = json.getString("cognito:username");
        List<Rant> rants = rantService.getAllRants(username);
        return ResponseEntity.ok(rants);
    }
}