package com.neoseducation.gestao_escolar.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoseducation.gestao_escolar.entities.Professor;
import com.neoseducation.gestao_escolar.repository.ProfessorRepository;
import com.neoseducation.gestao_escolar.service.ProfessorService;

@RestController
@RequestMapping("api/professor")
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;


    @PostMapping
    public ResponseEntity<Professor> CreateProfessor (@RequestBody Professor professor){
        return ResponseEntity.status((HttpStatus.CREATED)).body(professorRepository.save(professor));
    }

    @GetMapping("/listar")
    public List<Professor> GetProfessor(){
        return professorRepository.findAll();
    }

    @PostMapping("/registrar")
    public ResponseEntity<Professor> registrarProfessor(@RequestBody Professor professor){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(professorRepository.save(professor));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/cpf")
    public ResponseEntity<Professor> buscarPorCpf(@PathVariable String cpf) {
        Optional<Professor> professor = professorService.buscarPorCpf(cpf);
        if (professor.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(professor.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor (@PathVariable Long id, @RequestBody Professor professor){
        Optional<Professor> professorOptional = professorRepository.findById(id);
        if (professorOptional.isPresent()){
            Professor professorData = professorOptional.get();
            professorData.setNome(professor.getNome());
            professorData.setCpf(professor.getCpf());
            professorData.setTelefone(professor.getTelefone());
            professorData.setEmail(professor.getEmail());
            professorData.setEndereco(professor.getEndereco());
            professorData.setDisciplinaIds(professor.getDisciplinaIds());
            return ResponseEntity.status(HttpStatus.OK).body(professorRepository.save(professorData));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
}
