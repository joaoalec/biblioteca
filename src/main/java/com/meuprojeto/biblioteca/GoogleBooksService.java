package com.meuprojeto.biblioteca;

import com.meuprojeto.biblioteca.dto.LivroResponseDTO;
import com.meuprojeto.biblioteca.mapper.LivroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooksService {

    @Autowired
    private GoogleBooksClient googleBooksClient;

    @Autowired
    private LivroMapper livroMapper;

    public List<LivroResponseDTO> buscarLivros(String titulo) {
        GoogleBooksResponse response = googleBooksClient.buscarLivrosPorTitulo("intitle:" + titulo);
        List<Livro> livros = new ArrayList<>();

        if (response.getItems() != null) {
            for (GoogleBookItem item : response.getItems()) {
                Livro livro = new Livro();
                var info = item.getVolumeInfo();
                livro.setTitulo(info.getTitle());
                if (info.getAuthors() != null && info.getAuthors().length > 0) {
                    livro.setAutor(info.getAuthors()[0]);
                }
                livro.setDescricao(info.getDescription());
                try {
                    String ano = info.getPublishedDate();
                    if (ano != null && ano.length() >= 4) {
                        livro.setAnoPublicacao(Integer.parseInt(ano.substring(0, 4)));
                    }
                } catch (Exception e) {}
                livro.setIsbn("N/A");
                livros.add(livro);
            }
        }
        return livroMapper.toResponseDTO(livros);
    }
}

