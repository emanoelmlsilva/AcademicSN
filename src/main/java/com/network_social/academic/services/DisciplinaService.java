package com.network_social.academic.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.network_social.academic.dtos.DisciplinaBaseComentarioDTO;
import com.network_social.academic.dtos.DisciplinaBaseDTO;
import com.network_social.academic.dtos.DisciplinaBaseLikesDTO;
import com.network_social.academic.dtos.DisciplinaBaseNotaDTO;
import com.network_social.academic.models.Disciplina;
import com.network_social.academic.repositories.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public DisciplinaService() {
    }

    @PostConstruct
    public void initDisciplinas() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Disciplina>> typeReference = new TypeReference<List<Disciplina>>() {
        };
        InputStream inputStream = ObjectMapper.class.getResourceAsStream("/json/disciplinas.json");
        try {
            List<Disciplina> disciplinas = mapper.readValue(inputStream, typeReference);

            List<Disciplina> bdDisciplinas = this.disciplinaRepository.findAll();

            if (bdDisciplinas.size() > 0) {

                disciplinas.removeAll(bdDisciplinas);

            }

            this.disciplinaRepository.saveAll(disciplinas);
            System.out.println("Disciplinas salvas no BD!");

        } catch (

        IOException erro) {
            System.out.println("Não foi possivel salvar as disciplinas no BD!" + erro.getMessage());
        }

    }

    public List<DisciplinaBaseDTO> findAll() {
        return fromToBaseDisciplina(disciplinaRepository.findAll());
    }

    public Optional<Disciplina> findById(Long id) {
        return disciplinaRepository.findById(id);
    }

    // public Disciplina insert(Disciplina disciplina) {
    // disciplina.setId(null);
    // disciplinaRepository.save(disciplina);
    // return disciplina;
    // }

    public DisciplinaBaseLikesDTO updateLikes(Long id) throws NoSuchElementException {
        Optional<Disciplina> newDisciplina = findById(id);
        newDisciplina.get().setLikes(newDisciplina.get().getLikes() + 1);
        return fromToBaseLikesDisciplina(this.disciplinaRepository.save(newDisciplina.get()));

    }

    public DisciplinaBaseNotaDTO updateNota(Long id, Double nota) throws NoSuchElementException {
        Optional<Disciplina> newDisciplina = findById(id);
        if (newDisciplina.get().getNota() == null) {
            newDisciplina.get().setNota(nota);
        } else {
            Double media = (newDisciplina.get().getNota() + nota) / 2;
            newDisciplina.get().setNota(media);
        }
        return fromToBaseNotaDisciplina(this.disciplinaRepository.save(newDisciplina.get()));
    }

    public DisciplinaBaseComentarioDTO updateComentario(Long id, String comentario) throws NoSuchElementException {
        Optional<Disciplina> newDisciplina = findById(id);
        if (newDisciplina.get().getComentario() == null || newDisciplina.get().getComentario().equals("")) {
            newDisciplina.get().setComentario(comentario);
        } else {
            String concatenarComentario = newDisciplina.get().getComentario() + " , " + comentario;
            newDisciplina.get().setComentario(concatenarComentario);
        }

        return fromToBaseComentarioDisciplina(this.disciplinaRepository.save(newDisciplina.get()));

    }

    // public Disciplina delete(int id) throws ArrayIndexOutOfBoundsException {
    // findById(id);
    // return disciplinas.remove(id);
    // }

    public List<DisciplinaBaseDTO> fromToBaseDisciplina(List<Disciplina> disciplinas) {
        return disciplinas.stream().map(disciplina -> new DisciplinaBaseDTO(disciplina)).collect(Collectors.toList());
    }

    public DisciplinaBaseLikesDTO fromToBaseLikesDisciplina(Disciplina disciplina) {
        return new DisciplinaBaseLikesDTO(disciplina);
    }

    public DisciplinaBaseNotaDTO fromToBaseNotaDisciplina(Disciplina disciplina) {
        return new DisciplinaBaseNotaDTO(disciplina);
    }

    public DisciplinaBaseComentarioDTO fromToBaseComentarioDisciplina(Disciplina disciplina) {
        return new DisciplinaBaseComentarioDTO(disciplina);
    }

}