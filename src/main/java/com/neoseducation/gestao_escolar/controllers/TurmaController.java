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
            // Verifica se o professor existe
            Optional<Professor> professorOptional = professorRepository.findById(turma.getProfessor().getId());
            if (professorOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor não encontrado.");
            }

            // Verifica se a disciplina existe
            Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(turma.getDisciplina().getId());
            if (disciplinaOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Disciplina não encontrada.");
            }

            // Processa os IDs dos alunos e busca os dados completos
            List<Aluno> alunosCompletos = turma.getAlunos().stream()
            .map(aluno -> {
                Aluno alunoCompleto = alunoRepository.findById(aluno.getId())
                    .orElseThrow(() -> new RuntimeException("Aluno com ID " + aluno.getId() + " não encontrado."));
                alunoCompleto.setTurma(turma); // Configura o relacionamento inverso
                return alunoCompleto;
            })
            .toList();
        
            turma.setAlunos(alunosCompletos); // Associa os alunos completos à turma 
            turma.setProfessor(professorOptional.get());
            turma.setDisciplina(disciplinaOptional.get());

            // Salva a turma no banco de dados
            Turma novaTurma = turmaRepository.save(turma);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTurma);
        } catch (Exception e) {
            e.printStackTrace(); // Log detalhado do erro
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