document.addEventListener("DOMContentLoaded", function () {
    const formTurma = document.querySelector("#formularioTurma form");

    formTurma.addEventListener("submit", function (event) {
        event.preventDefault();

        const codigo = document.querySelector("#codigo").value;
        const anoLetivo = parseInt(document.querySelector("#anoLetivo").value);
        const periodo = parseInt(document.querySelector("#periodo").value);
        const turno = document.querySelector("#turno").value;
        const disciplinaId = parseInt(document.querySelector("#disciplina").value);
        const professorId = parseInt(document.querySelector("#professor").value);

        if (!codigo || isNaN(anoLetivo) || isNaN(periodo) || !turno || isNaN(disciplinaId) || isNaN(professorId)) {
            alert("Por favor, preencha todos os campos corretamente.");
            return;
        }

        const turma = {
            codigo: codigo,
            anoLetivo: anoLetivo,
            periodo: periodo,
            turno: turno,
            disciplina: { id: disciplinaId },
            professor: { id: professorId }
        };

        fetch("http://localhost:8080/api/turma", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(turma)
        })
        .then(response => response.json())
        .then(data => {
            alert("Turma cadastrada com sucesso!");
            formTurma.reset();
        })
        .catch(error => console.error("Erro ao cadastrar turma:", error));
    });
});