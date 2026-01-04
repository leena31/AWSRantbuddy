package com.web.rantbuddy.service.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.web.rantbuddy.dao.AuthDao;
import com.web.rantbuddy.model.LoginResponse;
import com.web.rantbuddy.model.User;
import com.web.rantbuddy.model.UserDetails;
import com.web.rantbuddy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class AuthServiceImpl implements AuthService {

//        private final Map<String, User> users = new HashMap<>();

        @Value("${aws.userpoolid}")
        String userPoolId;

        @Value("${aws.clientid}")
        String clientId;

    @Value("${aws.clientSecret}")
    String clientSecret;

        @Autowired
        AWSCognitoIdentityProvider cognitoClient;

        @Autowired
        DynamoDBMapper dynamoDBMapper;

        @Autowired
        AuthDao authDao;

        @Override
        public boolean register(User user) {
//            👉 Who creates the user?
//✅ AWS Cognito User Pool
//
//            Spring Boot is just an admin client.
            // 1️⃣ Create user in Cognito
            //Creates a user record sets user status as FORCE_CHANGE_PASSWORD
            AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(user.getUsername())
                    .withTemporaryPassword(user.getPassword())
                    .withUserAttributes(
                            new com.amazonaws.services.cognitoidp.model.AttributeType()
                                    .withName("email")
                                    .withValue(user.getEmail())
                    );

            try {
                AdminCreateUserResult result = cognitoClient.adminCreateUser(cognitoRequest);
                // 2️⃣ Set password as PERMANENT (CRITICAL)
                //why this? = I trust this password. No need to force change. user status as CONFIRMED
                AdminSetUserPasswordRequest passwordRequest =
                        new AdminSetUserPasswordRequest()
                                .withUserPoolId(userPoolId)
                                .withUsername(user.getUsername())
                                .withPassword(user.getPassword())
                                .withPermanent(true);

                cognitoClient.adminSetUserPassword(passwordRequest);

            } catch (UsernameExistsException e) {
                throw new RuntimeException("User already exists");
            } catch (Exception e) {
                throw new RuntimeException("Error registering user", e);
            }

            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(user.getUsername());
            userDetails.setEmail(user.getEmail());
            userDetails.setCreatedAt(LocalDateTime.now().toString());
            dynamoDBMapper.save(userDetails);
            return true;
        }
        @Override
        public LoginResponse login(String username, String password) {
            LoginResponse loginResponse = new LoginResponse();
//            Cognito:
//	•	Hashes password
//	•	Compares with stored hash
//	•	Checks user state is CONFIRMED
            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                    .withUserPoolId(userPoolId)
                    .withClientId(clientId)
                    .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .addAuthParametersEntry("USERNAME", username)
                    .addAuthParametersEntry("PASSWORD", password)
                    .addAuthParametersEntry("SECRET_HASH", calculateSecretHash(username, clientId, clientSecret));

            AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);

            loginResponse.setAccessToken(authResult.getAuthenticationResult().getAccessToken());
            loginResponse.setExpiredIn(authResult.getAuthenticationResult().getExpiresIn().toString());
            loginResponse.setRefreshToken(authResult.getAuthenticationResult().getRefreshToken());
            loginResponse.setUsername(username);
            return loginResponse;
        }

    private String calculateSecretHash(String username, String clientId, String clientSecret) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(
                    clientSecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal((username + clientId).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating secret hash", e);
        }
    }

}
