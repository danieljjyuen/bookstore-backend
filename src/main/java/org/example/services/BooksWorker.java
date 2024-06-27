package org.example.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksWorker {

    private final BooksService booksService;
    private final AmazonSQS amazonSQS;

    @Autowired
    public BooksWorker(AmazonSQS amazonSQS, BooksService booksService) {
        this.booksService = booksService;
        this.amazonSQS = amazonSQS;
    }

//    @SqsListener(value = "${aws.sqs.queue.name}")
//    public void receiveMessage(String query) {
//        try {
//            System.out.println("inside book worker 1  -----|");
//            System.out.println("book worker query : " + query);
//            booksService.processSearch(query);
//            System.out.println("inside book worker 2 -----|");
//        } catch (Exception e) {
//            System.out.println("error book worker " + e.getMessage());
//        }
//    }
//
//    private final AmazonSQS amazonSQS;
//    private final SqsService sqsService;
//    private final BooksService booksService;

//    @Autowired
//    public BooksWorker(AmazonSQS amazonSQS, SqsService sqsService, BooksService booksService) {
//        this.amazonSQS = amazonSQS;
//        this.sqsService = sqsService;
//        this.booksService = booksService;
//    }
//
    @Scheduled(fixedDelay = 5000)
    public void pollQueue() {
        System.out.println("poll --");
//        String queueUrl = sqsService.getQueueUrl();
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest("http://localhost:4566/000000000000/google-books-queue")
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(20);
        try {
            List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
            for (Message message : messages) {
                String query = message.getBody();
                System.out.println("book worker before process --");
                booksService.processSearch(query);
                System.out.println("book worker after process --");
                amazonSQS.deleteMessage("http://localhost:4566/000000000000/google-books-queue", message.getReceiptHandle());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error " + e.getMessage());
        }

    }
}
