package com.network_social.academic.dtos;

import java.io.Serializable;

import com.network_social.academic.models.Disciplina;

public class DisciplinaBaseComentarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String comentario;

    public DisciplinaBaseComentarioDTO() {
    }

    public DisciplinaBaseComentarioDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
        this.comentario = disciplina.getComentario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}