package com.meuprojeto.biblioteca;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleBooks", url = "https://www.googleapis.com/books/v1")
public interface GoogleBooksClient {

    @GetMapping("/volumes")
    GoogleBooksResponse buscarLivrosPorTitulo(@RequestParam("q") String titulo);
}
