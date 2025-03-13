package com.neoseducation.gestao_escolar.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar vazio")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Column(unique = true)
    private String cpf;

    @Past(message = "Data de nascimento deve ser uma data válida no passado")
    private LocalDate dataNascimento;

    private String endereco;
    private String telefone;
    
    @Email(message = "E-mail inválido")
    private String email;


    @ElementCollection
    @CollectionTable(name = "professor_disciplinas", joinColumns = @JoinColumn(name = "professor_id"))
    @Column(name = "disciplina_id")
    private List<Long> disciplinaIds;
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Long> getDisciplinaIds() { return disciplinaIds; }

    public void setDisciplinaIds(List<Long> disciplinaIds) { this.disciplinaIds = disciplinaIds;}
}
