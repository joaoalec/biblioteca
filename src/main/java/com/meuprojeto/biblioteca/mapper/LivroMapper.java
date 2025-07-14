package com.meuprojeto.biblioteca.mapper;

import com.meuprojeto.biblioteca.Livro;
import com.meuprojeto.biblioteca.dto.LivroRequestDTO;
import com.meuprojeto.biblioteca.dto.LivroResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroResponseDTO toResponseDTO(Livro livro);

    List<LivroResponseDTO> toResponseDTO(List<Livro> livros);
    
    @Mapping(target = "id", ignore = true)
    Livro toEntity(LivroRequestDTO requestDTO);
}
