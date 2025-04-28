package com.neoseducation.gestao_escolar.repository;

import com.neoseducation.gestao_escolar.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface Alunorepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByMatricula(String matricula);
    Optional<Aluno> findByNome(String nome);
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByTelefone(String telefone);
    Optional<Aluno> findByEndereco(String endereco);
    Optional<Aluno> findByDataNascimento(LocalDate dataNascimento);
    Optional<Aluno> findById(Long id);
}   