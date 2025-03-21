
//Cadastro de aluno
document.addEventListener("DOMContentLoaded", function () {
    const formAluno = document.querySelector("#formularioAluno form");

    formAluno.addEventListener("submit", function (event) {
        event.preventDefault(); // Evita o reload da página

        const aluno = {
            nome: document.querySelector("#nome").value,
            dataNascimento: document.querySelector("#dataNascimento").value,
            cpf: document.querySelector("#cpf").value,
            endereco: document.querySelector("#endereco").value,
            telefone: document.querySelector("#telefone").value,
            email: document.querySelector("#email").value
        };

        fetch("http://localhost:8080/api/aluno", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(aluno)
        })
        .then(response => response.json())
        .then(data => {
            alert("Aluno cadastrado com sucesso!");
            formAluno.reset();
        })
        .catch(error => console.error("Erro ao cadastrar aluno:", error));
    });
});
//Cadastro de professor
document.addEventListener("DOMContentLoaded", function () {
    const formProfessor = document.querySelector("#formularioProfessor form");

    formProfessor.addEventListener("submit", function (event) {
        event.preventDefault(); // Evita o reload da página

        const professor = {
            nome: document.querySelector("#nome").value,
            dataNascimento: document.querySelector("#dataNascimento").value,
            cpf: document.querySelector("#cpf").value,
            endereco: document.querySelector("#endereco").value,
            telefone: document.querySelector("#telefone").value,
            email: document.querySelector("#email").value
        };

        fetch("http://localhost:8080/api/professor/registrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(professor)
        })
        .then(response => response.json())
        .then(data => {
            alert("Professor cadastrado com sucesso!");
            formProfessor.reset();
        })
        .catch(error => console.error("Erro ao cadastrar professor:", error));
    });
});
//Campos disciplina no professor
document.addEventListener("DOMContentLoaded", function () {
    const selectDisciplina = document.querySelector("#disciplina");

    // Função para buscar disciplinas na API
    function carregarDisciplinas() {
        fetch("http://localhost:8080/api/disciplina/listar")
            .then(response => response.json())
            .then(disciplinas => {
                disciplinas.forEach(disciplina => {
                    const option = document.createElement("option");
                    option.value = disciplina.codigo; // Supondo que o código está no campo 'codigo'
                    option.textContent = `${disciplina.codigo} - ${disciplina.nome}`;
                    selectDisciplina.appendChild(option);
                });
            })
            .catch(error => console.error("Erro ao buscar disciplinas:", error));
    }

    carregarDisciplinas();
});
//Cadastro de disciplina
document.addEventListener("DOMContentLoaded", function () {
    const formDisciplina = document.querySelector("#formularioDisciplina form");

    formDisciplina.addEventListener("submit", function (event) {
        event.preventDefault(); // Evita o reload da página

        const disciplina = {
            nomedadisciplina: document.querySelector("#nome").value,
            codigo: document.querySelector("#codigo").value,
            cargaHoraria: document.querySelector("#cargaHoraria").value,
            professor: document.querySelector("#professor").value
        };

        fetch("http://localhost:8080/api/disciplina/registrar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(disciplina)
        })
        .then(response => response.json())
        .then(data => {
            alert("disciplina cadastrado com sucesso!");
            formDisciplina.reset();
        })
        .catch(error => console.error("Erro ao cadastrar disciplina:", error));
    });
});
document.addEventListener("DOMContentLoaded", function () {
    const selectProfessor = document.querySelector("#professor");

    // Função para buscar Professores na API
    function carregarProfessores() {
        fetch("http://localhost:8080/api/professor/listar")
            .then(response => response.json())
            .then(professor => {
                professor.forEach(professor => {
                    const option = document.createElement("option");
                    option.value = professor.nome; // Supondo que o nome está no campo 'nome'
                    option.textContent = `${professor.nome}`;
                    selectProfessor.appendChild(option);
                });
            })
            .catch(error => console.error("Erro ao buscar Professores:", error));
    }

    carregarProfessores();
});