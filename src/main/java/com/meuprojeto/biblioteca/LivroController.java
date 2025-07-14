package com.meuprojeto.biblioteca;

import com.meuprojeto.biblioteca.dto.LivroRequestDTO;
import com.meuprojeto.biblioteca.dto.LivroResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;
    
    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> salvar(@RequestBody LivroRequestDTO requestDTO) {
        LivroResponseDTO livroSalvo = livroService.salvar(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(livroSalvo.getId())
                .toUri();
        return ResponseEntity.created(location).body(livroSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(@PathVariable Long id, @RequestBody LivroRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(livroService.atualizar(id, requestDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<List<LivroResponseDTO>> buscarLivrosExternos(@RequestParam String titulo) {
        return ResponseEntity.ok(googleBooksService.buscarLivros(titulo));
    }
}
