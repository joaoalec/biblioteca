package com.meuprojeto.biblioteca;

import com.meuprojeto.biblioteca.dto.LivroRequestDTO;
import com.meuprojeto.biblioteca.dto.LivroResponseDTO;
import com.meuprojeto.biblioteca.mapper.LivroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;

    public List<LivroResponseDTO> listarTodos() {
        return livroMapper.toResponseDTO(livroRepository.findAll());
    }

    public Optional<LivroResponseDTO> buscarPorId(Long id) {
        return livroRepository.findById(id).map(livroMapper::toResponseDTO);
    }

    public LivroResponseDTO salvar(LivroRequestDTO requestDTO) {
        Livro livro = livroMapper.toEntity(requestDTO);
        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toResponseDTO(livroSalvo);
    }
    
    public LivroResponseDTO atualizar(Long id, LivroRequestDTO requestDTO) {
        livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));
        Livro livroParaAtualizar = livroMapper.toEntity(requestDTO);
        livroParaAtualizar.setId(id);
        return livroMapper.toResponseDTO(livroRepository.save(livroParaAtualizar));
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }
}
