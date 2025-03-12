package com.neoseducation.gestao_escolar.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.neoseducation.gestao_escolar.entities.Professor;


public interface ProfessorRepository extends JpaRepository <Professor, Long> {
    
    Optional<Professor> findByCpf(String cpf);

    
}