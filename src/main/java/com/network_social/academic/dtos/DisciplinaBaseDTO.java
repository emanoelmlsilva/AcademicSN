package com.network_social.academic.dtos;

import java.io.Serializable;

import com.network_social.academic.models.Disciplina;

public class DisciplinaBaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    Long id;
    private String nome;

    public DisciplinaBaseDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
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

}