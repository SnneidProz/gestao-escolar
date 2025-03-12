package com.neoseducation.gestao_escolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neoseducation.gestao_escolar.entities.Disciplina;

public interface DisciplinaRepository extends JpaRepository <Disciplina, Long> {
    
    Optional<Disciplina> findById(Long Id);

}