document.addEventListener("DOMContentLoaded", function () {
    const formDisciplina = document.querySelector("#formularioDisciplina form");

    formDisciplina.addEventListener("submit", function (event) {
        event.preventDefault();

        const professorId = document.querySelector("#professor").value;

        if (!professorId || isNaN(professorId)) {
            alert("Por favor, selecione um professor válido.");
            return;
        }

        const disciplina = {
            nome: document.querySelector("#nomedisciplina").value,
            codigo: document.querySelector("#codigodisciplina").value,
            cargaHoraria: document.querySelector("#cargahoraria").value,
            professor: { id: parseInt(professorId) }
        };

        fetch("http://localhost:8080/api/disciplina/registrar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(disciplina)
        })
        .then(response => response.json())
        .then(data => {
            alert("Disciplina cadastrada com sucesso!");
            formDisciplina.reset();
        })
        .catch(error => console.error("Erro ao cadastrar disciplina:", error));
    });
});