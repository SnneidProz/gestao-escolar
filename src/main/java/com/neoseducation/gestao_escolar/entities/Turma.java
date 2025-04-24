package com.neoseducation.gestao_escolar.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Período é obrigatório")
    private String periodo;
    
    

    @NotBlank(message = "Código da turma é obrigatório")
    @Column(unique = true) // Garante que o código seja único no banco de dados
    private String codigo;

    @NotBlank(message = "Turno é obrigatório")
    private String turno;

    @NotNull(message = "Ano letivo é obrigatório")
    @Min(value = 2023, message = "Ano letivo deve ser maior ou igual a 2023")
    @Max(value = 2100, message = "Ano letivo deve ser menor ou igual a 2100")
    private int anoLetivo;


    @NotNull(message = "Professor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @NotNull(message = "Disciplina é obrigatória")
    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;


    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    public int getAnoLetivo() {
        return anoLetivo;
    }
    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }


}