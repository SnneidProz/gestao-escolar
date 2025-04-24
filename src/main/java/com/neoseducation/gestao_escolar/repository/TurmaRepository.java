package com.neoseducation.gestao_escolar.repository;
import com.neoseducation.gestao_escolar.entities.Disciplina;
import com.neoseducation.gestao_escolar.entities.Professor;
import com.neoseducation.gestao_escolar.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByProfessor(Professor professor);
    List<Turma> findByDisciplina(Disciplina disciplina);
    List<Turma> findByAnoLetivo(int anoLetivo);
    List<Turma> findByPeriodo(String periodo);
    List<Turma> findByTurno(String turno);
    List<Turma> findByAnoLetivoAndPeriodoAndTurno(int anoLetivo, String periodo, String turno);
    List<Turma> findByAnoLetivoAndTurno(int anoLetivo, String turno);
    List<Turma> findByAnoLetivoAndPeriodo(int anoLetivo, String periodo);

}
