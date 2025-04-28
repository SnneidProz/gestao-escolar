
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

function validarCPF() {
    let cpf = document.getElementById('cpf').value.replace(/\D/g, '');
    if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) {
        document.getElementById('cpf').setCustomValidity('CPF inválido.');
        return;
    }
    let soma = 0, resto;
    for (let i = 1; i <= 9; i++) soma += parseInt(cpf[i - 1]) * (11 - i);
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf[9])) {
        document.getElementById('cpf').setCustomValidity('CPF inválido.');
        return;
    }
    soma = 0;
    for (let i = 1; i <= 10; i++) soma += parseInt(cpf[i - 1]) * (12 - i);
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf[10])) {
        document.getElementById('cpf').setCustomValidity('CPF inválido.');
        return;
    }
    document.getElementById('cpf').setCustomValidity('');
}
function validarDataNascimentoAluno() {
    let inputData = document.getElementById('data_nascimento');
    let dataSelecionada = new Date(inputData.value);
    let hoje = new Date();
    let idadeMinima = 10;
    let dataLimite = new Date(hoje.getFullYear() - idadeMinima, hoje.getMonth(), hoje.getDate());
    
    if (dataSelecionada > dataLimite) {
        inputData.setCustomValidity('O Aluno deve ter pelo menos 10 anos de idade.');
    } else {
        inputData.setCustomValidity('');
    }
}
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
function validarDataNascimentoProf() {
    let inputData = document.getElementById('data_nascimento');
    let dataSelecionada = new Date(inputData.value);
    let hoje = new Date();
    let idadeMinima = 18;
    let dataLimite = new Date(hoje.getFullYear() - idadeMinima, hoje.getMonth(), hoje.getDate());
    
    if (dataSelecionada > dataLimite) {
        inputData.setCustomValidity('O professor deve ter pelo menos 18 anos de idade.');
    } else {
        inputData.setCustomValidity('');
    }
}
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

        const professorId = document.querySelector("#professor").value;

        // Verifique se o valor é um número e não "undefined"
        if (!professorId || isNaN(professorId)) {
            alert("Por favor, selecione um professor válido.");
            return;
        }

        
        const disciplina = {
            nome: document.querySelector("#nomedisciplina").value,
            codigo: document.querySelector("#codigodisciplina").value,
            cargaHoraria: document.querySelector("#cargahoraria").value,
            professor: { id: parseInt(professorId) } // Certifique-se de passar um objeto
        };
        
        console.log("Enviando disciplina:", JSON.stringify(disciplina, null, 2));
        
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


document.addEventListener("DOMContentLoaded", function () {
    const selectProfessor = document.querySelector("#professor");

    // Função para buscar Professores na API
    function carregarProfessores() {
        fetch("http://localhost:8080/api/professor/listar")
            .then(response => response.json())
            .then(professor => {
                professor.forEach(professor => {
                    const option = document.createElement("option");
                    option.value = professor.id; // Supondo que o nome está no campo 'nome'
                    option.textContent = `${professor.nome}`;
                    selectProfessor.appendChild(option);
                });
            })
            
            .catch(error => console.error("Erro ao buscar Professores:", error));
    }

    carregarProfessores();
});

// Cadastro de turma
document.addEventListener("DOMContentLoaded", function () {
    const formTurma = document.querySelector("#formularioTurma form");

    formTurma.addEventListener("submit", function (event) {
        event.preventDefault(); // Evita o reload da página

        // Variáveis para os campos do formulário
        const codigo = document.querySelector("#codigo").value; // ID correto para o campo de código
        const anoLetivo = parseInt(document.querySelector("#anoLetivo").value);
        const periodo = parseInt(document.querySelector("#periodo").value);
        const turno = document.querySelector("#turno").value;
        const disciplinaId = parseInt(document.querySelector("#disciplina").value);
        const professorId = parseInt(document.querySelector("#professor").value);

        // Validação dos campos
        if (!codigo || isNaN(anoLetivo) || isNaN(periodo) || !turno || isNaN(disciplinaId) || isNaN(professorId)) {
            alert("Por favor, preencha todos os campos corretamente.");
            return;
        }

        // Objeto turma
        const turma = {
            codigo: codigo,
            anoLetivo: anoLetivo,
            periodo: periodo,
            turno: turno,
            disciplina: { id: disciplinaId },
            professor: { id: professorId }
        };

        console.log("Enviando turma:", JSON.stringify(turma, null, 2));

        // Envia a requisição para o backend
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
        .catch(error => console.error("Erro ao cadastrar turma:", error));
    });
});

// Campos disciplina no turma
document.addEventListener("DOMContentLoaded", function () {
    const selectDisciplina = document.querySelector("#disciplina");

    // Função para buscar disciplinas na API
    function carregarDisciplinas() {
        fetch("http://localhost:8080/api/disciplina/listar")
            .then(response => response.json())
            .then(disciplinas => {
                disciplinas.forEach(disciplina => {
                    const option = document.createElement("option");
                    option.value = disciplina.id; // Supondo que o código está no campo 'codigo'
                    option.textContent = `${disciplina.codigo} - ${disciplina.nome}`;
                    selectDisciplina.appendChild(option);
                });
            })
            .catch(error => console.error("Erro ao buscar disciplinas:", error));
    }

    carregarDisciplinas();
});
// Campos professor no turma
document.addEventListener("DOMContentLoaded", function () {
    const selectProfessor = document.querySelector("#professor");

    // Função para buscar Professores na API
    function carregarProfessores() {
        fetch("http://localhost:8080/api/professor/listar")
            .then(response => response.json())
            .then(professores => {
                professores.forEach(professor => {
                    const option = document.createElement("option");
                    option.value = professor.id; // Supondo que o nome está no campo 'nome'
                    option.textContent = `${professor.nome}`;
                    selectProfessor.appendChild(option);
                });
            })
            .catch(error => console.error("Erro ao buscar Professores:", error));
    }

    carregarProfessores();
});
