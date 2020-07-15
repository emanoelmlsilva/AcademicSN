package com.network_social.academic.repositories;

import java.util.List;

import com.network_social.academic.dtos.DisciplinaBaseLikesDTO;
import com.network_social.academic.dtos.DisciplinaBaseNotaDTO;
import com.network_social.academic.models.Disciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    List<DisciplinaBaseNotaDTO> findByOrderByNotaAsc();

    List<DisciplinaBaseLikesDTO> findByOrderByLikesDesc();

}