package com.web.rantbuddy.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.rantbuddy.model.Rant;
import com.web.rantbuddy.model.RantDetails;
import com.web.rantbuddy.model.RantRequest;
import com.web.rantbuddy.service.RantService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/rant")
@RequiredArgsConstructor
public class RantController {
    @Autowired
    RantService rantService;


    @PostMapping("/createRant")
    public ResponseEntity<?> createRant(
            @RequestBody RantRequest rantRequest,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String username = jwt.getClaimAsString("username");

        RantDetails rant = rantService.createRant(username, rantRequest.getRantText());

        return ResponseEntity.accepted().body(Map.of(
                "rantId", rant.getRantId(),
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