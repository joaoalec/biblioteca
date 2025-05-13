package com.meuprojeto.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;
    
    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping
    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Livro salvar(@RequestBody Livro livro) {
        return livroService.salvar(livro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        return livroService.buscarPorId(id)
                .map(l -> {
                    livro.setId(id);
                    return ResponseEntity.ok(livroService.salvar(livro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(l -> {
                    livroService.deletar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/externo")
    public List<Livro> buscarLivrosExternos(@RequestParam String titulo) {
        return googleBooksService.buscarLivros(titulo);
    }


}

