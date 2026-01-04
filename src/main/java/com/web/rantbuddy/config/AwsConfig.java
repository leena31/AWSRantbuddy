package com.web.rantbuddy.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.region}")
    String awsRegion;

    @Value("${aws.accessKeyId}")
    String accessKeyId;

    @Value("${aws.secretKey}")
    String secretKey;
    @Bean
    public AWSCognitoIdentityProvider cognitoClient() {
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKeyId, secretKey)))
                .build();
    }
}