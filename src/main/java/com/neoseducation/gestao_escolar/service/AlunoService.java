package com.neoseducation.gestao_escolar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoseducation.gestao_escolar.entities.Aluno;
import com.neoseducation.gestao_escolar.repository.Alunorepository;

@Service
public class AlunoService {
    @Autowired
    private Alunorepository alunoRepository;

    public Optional<Aluno> buscarPorCpf(String cpf) {
        return alunoRepository.findAll().stream()
                .filter(aluno -> aluno.getCpf().equals(cpf))
                .findFirst();
    }


    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }
}
