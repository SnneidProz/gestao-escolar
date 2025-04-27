package com.neoseducation.gestao_escolar.repository;

import com.neoseducation.gestao_escolar.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Alunorepository extends JpaRepository<Aluno, Long> {
}   