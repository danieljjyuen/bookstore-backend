package org.example.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "books", url = "${name.service.url}")
public interface BooksProxy {

    @GetMapping("/")
    String searchBooks(@RequestParam("q") String query, @RequestParam("key") String apiKey);

}
