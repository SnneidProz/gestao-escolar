package com.neoseducation.gestao_escolar.service;

import com.neoseducation.gestao_escolar.entities.Disciplina;
import com.neoseducation.gestao_escolar.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Optional<Disciplina> buscarPorCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo);
    }
}