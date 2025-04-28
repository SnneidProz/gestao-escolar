 package com.neoseducation.gestao_escolar.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoseducation.gestao_escolar.entities.Aluno;
import com.neoseducation.gestao_escolar.repository.Alunorepository;
import com.neoseducation.gestao_escolar.service.AlunoService;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/api/aluno")
public class AlunoController {

    @Autowired
    private Alunorepository alunoRepository;

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Aluno> CreateAluno (@RequestBody Aluno aluno){
        return ResponseEntity.status((HttpStatus.CREATED)).body(alunoRepository.save(aluno));
    }
    
    @GetMapping("/listar")
    
    public List<Aluno> GetAluno(){
        return alunoRepository.findAll();
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Aluno> registrarAluno(@RequestBody Aluno aluno) {
        try {
            // Gera a matrícula automaticamente
            String matricula = "MAT" + System.currentTimeMillis(); // Exemplo: MAT + timestamp
            aluno.setMatricula(matricula);

            // Salva o aluno no banco de dados
            Aluno novoAluno = alunoRepository.save(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/cpf")
    public ResponseEntity<Aluno> buscarPorCpf(@PathVariable String cpf) {
        Optional<Aluno> aluno = alunoService.buscarPorCpf(cpf);

        return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
    try {
        // Busca o aluno existente pelo ID
        Optional<Aluno> alunoExistente = alunoRepository.findById(id);

        if (alunoExistente.isPresent()) {
            Aluno aluno = alunoExistente.get();

            // Atualiza os campos do aluno existente com os dados enviados
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setCpf(alunoAtualizado.getCpf());
            aluno.setDataNascimento(alunoAtualizado.getDataNascimento());
            aluno.setEndereco(alunoAtualizado.getEndereco());
            aluno.setTelefone(alunoAtualizado.getTelefone());
            aluno.setEmail(alunoAtualizado.getEmail());

            // Salva o aluno atualizado no banco de dados
            Aluno alunoAtualizadoSalvo = alunoRepository.save(aluno);

            return ResponseEntity.ok(alunoAtualizadoSalvo);
        } else {
            // Se o aluno não existir, retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        // Em caso de erro, retorna 400 Bad Request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}



    


}