package com.network_social.academic.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.network_social.academic.dtos.DisciplinaBaseDTO;
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
            System.out.println("bdDisciplinas " + bdDisciplinas.size());

            if (bdDisciplinas.size() > 0) {

                List<Disciplina> newList = disciplinas.stream()
                        .filter(bdDisciplina -> bdDisciplinas.stream()
                                .anyMatch(disciplina -> !disciplina.getNome().equals(bdDisciplina.getNome())))
                        .collect(Collectors.toList());
                disciplinas = newList;
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

    // public Disciplina insert(Disciplina disciplina) {
    // disciplina.setId(null);
    // disciplinaRepository.save(disciplina);
    // return disciplina;
    // }

    // public Disciplina findById(Long id) {
    // Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
    // return disciplina;
    // }

    // public Disciplina update(Disciplina disciplina) throws
    // ArrayIndexOutOfBoundsException {
    // findById(disciplina.getId());
    // disciplinas.set(disciplina.getId(), disciplina);
    // return disciplina;

    // }

    // public Disciplina delete(int id) throws ArrayIndexOutOfBoundsException {
    // findById(id);
    // return disciplinas.remove(id);
    // }

    public List<DisciplinaBaseDTO> fromToBaseDisciplina(List<Disciplina> discilinas) {
        return discilinas.stream().map(disciplina -> new DisciplinaBaseDTO(disciplina)).collect(Collectors.toList());
    }
}