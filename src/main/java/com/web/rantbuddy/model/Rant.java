package com.web.rantbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rant {
    private String rantId;
    private String username;
    private String status; // PROCESSING / COMPLETED
}