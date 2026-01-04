package com.web.rantbuddy.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "RantDetails")
public class RantDetails {
        @DynamoDBHashKey(attributeName = "rantId")
        private String rantId;

        @DynamoDBAttribute(attributeName = "username")
        private String username; // user who created the rant

        @DynamoDBAttribute(attributeName = "title")
        private String title;

        @DynamoDBAttribute(attributeName = "description")
        private String description;

        @DynamoDBAttribute(attributeName = "createdAt")
        private String createdAt;



}
