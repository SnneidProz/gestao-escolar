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

    public Aluno cadastrarAluno (Aluno aluno){
        if (alunoRepository.findByCpf(aluno.getCpf()).isPresent()){
            throw new RuntimeException("CPF já cadastrado");
        }
        return alunoRepository.save(aluno);
    } 

    public List<Aluno> listarAlunos(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorCpf(String cpf){
        return alunoRepository.findByCpf(cpf);
    }

}
