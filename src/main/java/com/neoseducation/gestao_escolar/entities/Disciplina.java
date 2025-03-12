package com.neoseducation.gestao_escolar.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da disciplina não pode estar vazio")
    private String nome;

    @NotBlank(message = "Código da disciplina é obrigatório")
    @Column(unique = true) // Garante que o código seja único no banco de dados
    private String codigo;

    @Positive(message = "Carga horária deve ser um valor positivo")
    private int cargaHoraria;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor; // Relacionamento com a entidade Professor

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}