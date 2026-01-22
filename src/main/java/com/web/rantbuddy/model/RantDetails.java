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
        private String username;

        @DynamoDBAttribute(attributeName = "timestamp")
        private String timestamp;

        @DynamoDBAttribute(attributeName = "status")
        private String status;

        @DynamoDBAttribute(attributeName = "sentiment")
        private String sentiment;

        @DynamoDBAttribute(attributeName = "emotion")
        private String emotion;

        @DynamoDBAttribute(attributeName = "analysisResult")
        private String analysisResult;
}
