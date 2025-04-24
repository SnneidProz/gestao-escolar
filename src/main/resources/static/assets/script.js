function carregarProfessores() {
    fetch("http://localhost:8080/api/professor/listar")
        .then(response => response.json())
        .then(professores => {
            const selectProfessor = document.querySelector("#professor");
            professores.forEach(professor => {
                const option = document.createElement("option");
                option.value = professor.id;
                option.textContent = professor.nome;
                selectProfessor.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao buscar professores:", error));
}
carregarProfessores();

function carregarDisciplinas() {
    fetch("http://localhost:8080/api/disciplina/listar")
        .then(response => response.json())
        .then(disciplinas => {
            const selectDisciplina = document.querySelector("#disciplina");
            disciplinas.forEach(disciplina => {
                const option = document.createElement("option");
                option.value = disciplina.id;
                option.textContent = disciplina.nome;
                selectDisciplina.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao buscar disciplinas:", error));
}
carregarDisciplinas();
function carregarEstados() {
    fetch("https://servicodados.ibge.gov.br/api/v1/localidades/estados")
        .then(response => response.json())
        .then(estados => {
            const selectEstado = document.querySelector("#estado");
            estados.forEach(estado => {
                const option = document.createElement("option");
                option.value = estado.sigla; // Sigla do estado (ex.: SP, RJ)
                option.textContent = estado.nome; // Nome do estado
                selectEstado.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao buscar estados:", error));
}
carregarEstados();