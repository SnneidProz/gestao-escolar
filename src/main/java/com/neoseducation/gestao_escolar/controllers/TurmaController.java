package com.neoseducation.gestao_escolar.controllers;

import com.neoseducation.gestao_escolar.entities.Turma;
import com.neoseducation.gestao_escolar.entities.Professor;
import com.neoseducation.gestao_escolar.entities.Aluno;
import com.neoseducation.gestao_escolar.entities.Disciplina;
import com.neoseducation.gestao_escolar.repository.TurmaRepository;
import com.neoseducation.gestao_escolar.repository.ProfessorRepository;
import com.neoseducation.gestao_escolar.repository.DisciplinaRepository;
import com.neoseducation.gestao_escolar.repository.Alunorepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/turma")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private Alunorepository alunoRepository; // Adicionado o repositório de alunos

    // Cadastrar uma nova turma
    @PostMapping
    public ResponseEntity<?> criarTurma(@RequestBody Turma turma) {
        try {
            Optional<Professor> professorOptional = professorRepository.findById(turma.getProfessor().getId());
            if (professorOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor não encontrado.");
            }
    
            Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(turma.getDisciplina().getId());
            if (disciplinaOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Disciplina não encontrada.");
            }
    
            List<Aluno> alunosCompletos = turma.getAlunos().stream()
                .map(aluno -> alunoRepository.findById(aluno.getId())
                    .orElseThrow(() -> new RuntimeException("Aluno com ID " + aluno.getId() + " não encontrado.")))
                .toList();
    
            turma.setProfessor(professorOptional.get());
            turma.setDisciplina(disciplinaOptional.get());
    
            Turma novaTurma = turmaRepository.save(turma); // Primeiro salva a turma para gerar o ID
    
            for (Aluno aluno : alunosCompletos) {
                aluno.setTurma(novaTurma); // Agora associa a turma recém-criada nos alunos
                alunoRepository.save(aluno); // Salva o aluno atualizado
            }
    
            novaTurma.setAlunos(alunosCompletos); // Atualiza a lista de alunos na turma (opcional)
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTurma);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar turma: " + e.getMessage());
        }
    }

    // Listar todas as turmas
   
    @GetMapping("/listar")
    public ResponseEntity<List<Turma>> listarTurmas() {
        List<Turma> turmas = turmaRepository.findAll();
        return ResponseEntity.ok(turmas);
    }

    // Buscar uma turma por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTurmaPorId(@PathVariable Long id) {
        Optional<Turma> turmaOptional = turmaRepository.findById(id);
        if (turmaOptional.isPresent()) {
            return ResponseEntity.ok(turmaOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada.");
    }
}