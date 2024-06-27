package org.example.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    private final AmazonSQS amazonSQS;

    private final String queueName = "google-books-queue";

    @Autowired
    public SqsService(AmazonSQS customAmazonSQS) {
        this.amazonSQS = customAmazonSQS;
        createQueue();
    }

    private void createQueue() {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
        amazonSQS.createQueue(createQueueRequest);
        System.out.println("Created SQS queue: " + queueName);
    }

    public String getQueueUrl() {
        String queueUrl = amazonSQS.getQueueUrl(queueName).getQueueUrl();
        System.out.println("Retrieved SQS queue URL: " + queueUrl);
        return queueUrl;
    }
}
