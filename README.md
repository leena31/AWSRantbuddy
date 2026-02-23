## Overview

This project demonstrates how generative AI services can be integrated into a cloud-native backend using an event-driven architecture.
The system allows users to submit text (“rants”), processes them asynchronously, and enriches them with AI-driven sentiment and tone analysis using AWS Bedrock.

## Architecture
Client → Spring Boot API → DynamoDB → EventBridge → Lambda → AWS Bedrock → Analysis Stored Back in DynamoDB → Client Retrieves Result
<img width="2051" height="1409" alt="AWS Infrastructure drawio (4)" src="https://github.com/user-attachments/assets/81419063-d2fd-4150-a0e6-49d0c6335fc5" />


## Tech Stack
- Java + Spring Boot
- AWS Cognito (Authentication)
- AWS DynamoDB (Storage)
- AWS EventBridge (Event routing)
- AWS Lambda (Async processing)
- AWS Bedrock (AI analysis)
- REST APIs

## Security
- JWT authentication via Cognito
- Protected endpoints require valid token
- No sensitive data stored locally

## Workflow
1. Client sends request to Spring Boot API.
2. Spring Boot API authenticates user via Cognito and stores the rant in DynamoDB.
3. An event "RantCreated" is published to Amazon EventBridge.
4. EventBridge triggers an AWS Lambda function.
5. Lambda retrieves the rant from DynamoDB and invokes AWS Bedrock for analysis.
6. Bedrock returns sentiment, tone, and mood insights.
7. Lambda updates the analysis results back into DynamoDB.
8. Client calls Get API to retrieve the enriched analysis.

