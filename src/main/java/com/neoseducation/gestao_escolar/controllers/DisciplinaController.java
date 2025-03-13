package com.neoseducation.gestao_escolar.controllers;

import com.neoseducation.gestao_escolar.entities.Disciplina;
import com.neoseducation.gestao_escolar.repository.DisciplinaRepository;
import com.neoseducation.gestao_escolar.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaService disciplinaService;

    // Cadastrar uma nova disciplina
    @PostMapping
    public ResponseEntity<Disciplina> criarDisciplina(@RequestBody Disciplina disciplina) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaRepository.save(disciplina));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Disciplina> registrarDisciplina(@RequestBody Disciplina disciplina) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaRepository.save(disciplina));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Listar todas as disciplinas
    @GetMapping("/listar")
    public ResponseEntity<List<Disciplina>> listarDisciplinas() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return ResponseEntity.ok(disciplinas);
    }

    // Buscar uma disciplina por ID
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarDisciplinaPorId(@PathVariable Long id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        return disciplina.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar uma disciplina por código
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Disciplina> buscarDisciplinaPorCodigo(@PathVariable String codigo) {
        Optional<Disciplina> disciplina = disciplinaService.buscarPorCodigo(codigo);
        return disciplina.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar uma disciplina existente
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizarDisciplina(@PathVariable Long id, @RequestBody Disciplina disciplinaAtualizada) {
        try {
            Optional<Disciplina> disciplinaExistente = disciplinaRepository.findById(id);

            if (disciplinaExistente.isPresent()) {
                Disciplina disciplina = disciplinaExistente.get();
                disciplina.setNome(disciplinaAtualizada.getNome());
                disciplina.setCodigo(disciplinaAtualizada.getCodigo());
                disciplina.setCargaHoraria(disciplinaAtualizada.getCargaHoraria());
               

                Disciplina disciplinaAtualizadaSalva = disciplinaRepository.save(disciplina);
                return ResponseEntity.ok(disciplinaAtualizadaSalva);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Excluir uma disciplina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDisciplina(@PathVariable Long id) {
        try {
            disciplinaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}