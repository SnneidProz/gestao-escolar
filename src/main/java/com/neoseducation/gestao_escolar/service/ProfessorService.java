package com.neoseducation.gestao_escolar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neoseducation.gestao_escolar.entities.Professor;
import com.neoseducation.gestao_escolar.repository.ProfessorRepository;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public Professor cadastrarProfessor (Professor professor){
        if (professorRepository.findByCpf(professor.getCpf()).isPresent()){
            throw new RuntimeException("CPF já cadastrado");
        }
        return professorRepository.save(professor);
    }

    public List<Professor> listarProfessores(){
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarPorCpf(String cpf){
        return professorRepository.findByCpf(cpf);
    }
}
