package com.neoseducation.gestao_escolar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neoseducation.gestao_escolar.entities.Aluno;

public interface Alunorepository extends JpaRepository <Aluno, Long> {

    Optional<Aluno> findByCpf(String cpf);


    
}
