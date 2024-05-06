package org.example.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "books", url = "${name.service.url}")
public interface BooksProxy {

    @GetMapping(value = "/",  produces = "application/json")
    String searchBooks(
            //keyword example intitle:xyz+inauthor:xyz
            @RequestParam("q") String query,
            @RequestParam("filter") String filter,
            @RequestParam("filter") String filter1,
            @RequestParam("filter") String filter2,
            @RequestParam("key") String apiKey);

}
