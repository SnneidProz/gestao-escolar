document.addEventListener("DOMContentLoaded", function () {
    const formTurma = document.querySelector("#formTurma");

    if (!formTurma) {
        console.error("Formulário de turma não encontrado.");
        return;
    }

    formTurma.addEventListener("submit", function (event) {
        event.preventDefault();

        // Obtém os valores dos campos do formulário
        const codigo = document.querySelector("#nomeTurma").value.trim();
        const anoLetivo = parseInt(document.querySelector("#anoLetivo").value);
        const turno = document.querySelector("#turno").value.trim();
        const periodo = document.querySelector("#periodo").value.trim();
        const professorId = parseInt(document.querySelector("#professor").value);
        const disciplinaId = parseInt(document.querySelector("#disciplina").value);

        // Obtém os nomes dos alunos selecionados
        const alunosSelecionados = Array.from(document.querySelector("#aluno").selectedOptions)
            .map(option => {
                return { id: parseInt(option.value)};
            });

        // Valida os campos
        if (!codigo || isNaN(anoLetivo) || anoLetivo < 2023 || anoLetivo > 2100 || isNaN(professorId) || isNaN(disciplinaId) || alunosSelecionados.length === 0) {
            alert("Por favor, preencha todos os campos corretamente.");
            return;
        }

        // Cria o objeto turma
        const turma = {
            codigo: codigo,
            anoLetivo: anoLetivo,
            professor: { id: professorId },
            disciplina: { id: disciplinaId },
            turno: turno,
            periodo: periodo,
            alunos: alunosSelecionados
        };

        // Envia os dados para o backend
        fetch("http://localhost:8080/api/turma", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(turma)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao cadastrar turma.");
            }
            return response.json();
        })
        .then(data => {
            alert("Turma cadastrada com sucesso!");
            formTurma.reset();
        })
        .catch(error => {
            console.error("Erro ao cadastrar turma:", error);
            alert("Ocorreu um erro ao cadastrar a turma. Tente novamente.");
        });
    });
});