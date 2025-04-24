package com.neoseducation.gestao_escolar.controllers;

import com.neoseducation.gestao_escolar.entities.Turma;
import com.neoseducation.gestao_escolar.entities.Professor;
import com.neoseducation.gestao_escolar.entities.Disciplina;
import com.neoseducation.gestao_escolar.repository.TurmaRepository;
import com.neoseducation.gestao_escolar.repository.ProfessorRepository;
import com.neoseducation.gestao_escolar.repository.DisciplinaRepository;

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
    
            // Salva a turma
            turma.setProfessor(professorOptional.get());
            turma.setDisciplina(disciplinaOptional.get());
            Turma novaTurma = turmaRepository.save(turma);
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

    // Atualizar uma turma existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTurma(@PathVariable Long id, @RequestBody Turma turmaAtualizada) {
        try {
            Optional<Turma> turmaOptional = turmaRepository.findById(id);
            if (turmaOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada.");
            }

            Turma turma = turmaOptional.get();

            // Atualiza os campos da turma
            turma.setCodigo(turmaAtualizada.getCodigo());
            turma.setTurno(turmaAtualizada.getTurno());
            turma.setAnoLetivo(turmaAtualizada.getAnoLetivo());

            // Atualiza o professor, se enviado
            if (turmaAtualizada.getProfessor() != null) {
                Optional<Professor> professorOptional = professorRepository.findById(turmaAtualizada.getProfessor().getId());
                if (professorOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Professor não encontrado.");
                }
                turma.setProfessor(professorOptional.get());
            }

            // Atualiza a disciplina, se enviada
            if (turmaAtualizada.getDisciplina() != null) {
                Optional<Disciplina> disciplinaOptional = disciplinaRepository.findById(turmaAtualizada.getDisciplina().getId());
                if (disciplinaOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Disciplina não encontrada.");
                }
                turma.setDisciplina(disciplinaOptional.get());
            }

            Turma turmaAtualizadaSalva = turmaRepository.save(turma);
            return ResponseEntity.ok(turmaAtualizadaSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar turma: " + e.getMessage());
        }
    }

    // Excluir uma turma
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirTurma(@PathVariable Long id) {
        try {
            Optional<Turma> turmaOptional = turmaRepository.findById(id);
            if (turmaOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma não encontrada.");
            }

            turmaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir turma: " + e.getMessage());
        }
    }
}