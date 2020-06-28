package com.network_social.academic.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.network_social.academic.models.Disciplina;

import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

    private List<Disciplina> disciplinas = new ArrayList<Disciplina>();

    public List<Disciplina> findAll() {
        return disciplinas;
    }

    public Disciplina insert(Disciplina disciplina) {
        disciplina.setId(disciplinas.size());
        disciplinas.add(disciplina);
        return disciplina;
    }

    public Disciplina findById(int id) {
        if (disciplinas.isEmpty() || id < 0 || id >= disciplinas.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return disciplinas.get(id);
    }

    public Disciplina update(Disciplina disciplina) throws ArrayIndexOutOfBoundsException {
        findById(disciplina.getId());
        disciplinas.set(disciplina.getId(), disciplina);
        return disciplina;

    }

    public Disciplina delete(int id) throws ArrayIndexOutOfBoundsException {
        findById(id);
        return disciplinas.remove(id);
    }
}