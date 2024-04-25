package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.BookSearchResponseDTO;
import org.example.proxy.BooksProxy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BooksService {

    private final String googleBooksApiKey;
    private final BooksProxy booksProxy;
    private final ObjectMapper objectMapper;

    public BooksService(String googleBooksApiKey, BooksProxy booksProxy, ObjectMapper objectMapper){
        this.googleBooksApiKey = googleBooksApiKey;
        this.booksProxy = booksProxy;
        this.objectMapper = objectMapper;
    }

    public String searchBooks(String query)  {
        String modifiedQuery = query.replace(" ", "+");
        String jsonResponse = booksProxy.searchBooks(modifiedQuery,"paid-ebooks", googleBooksApiKey);

        try{
            //parse into object
            BookSearchResponseDTO response = objectMapper.readValue(jsonResponse, BookSearchResponseDTO.class);
            System.out.println(response.toString());
        } catch(IOException e){
            e.printStackTrace();
        }

        return jsonResponse;
    }
}
