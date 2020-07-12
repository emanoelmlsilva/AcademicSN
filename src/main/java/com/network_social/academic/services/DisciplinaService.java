package com.network_social.academic.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            System.out.println("inputStream " + inputStream);
            System.out.println("mapper " + mapper);
            List<Disciplina> teste = mapper.readValue(inputStream, typeReference);
            // List<Disciplina> disciplinas = mapper.readValue(inputStream, typeReference);
            System.out.println("passou 1");
            this.disciplinaRepository.saveAll(teste);
            System.out.println("passou 2");
            System.out.println("Disciplinas salvas no BD!");
        } catch (IOException erro) {
            System.out.println("Não foi possivel salvar as disciplinas no BD!" + erro.getMessage());
        }

    }

    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
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
}