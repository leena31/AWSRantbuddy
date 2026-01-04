package com.web.rantbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rant {
    private String rantId;
    private String userId;
    private String rantText;
    private String status; // PROCESSING / COMPLETED
}