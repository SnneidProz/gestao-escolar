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

// carregar alunos
function carregarAlunos() {
    fetch("http://localhost:8080/api/aluno/listar")
        .then(response => response.json())
        .then(alunos => {
            const selectAluno = document.querySelector("#aluno");
            alunos.forEach(aluno => {
                const option = document.createElement("option");
                option.value = aluno.id;
                option.textContent = aluno.nome;
                selectAluno.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao buscar alunos:", error));
}
carregarAlunos();
// carregar turmas
function carregarTurmas() {
    fetch("http://localhost:8080/api/turma/listar")
        .then(response => response.json())
        .then(turmas => {
            const selectTurma = document.querySelector("#turma");
            turmas.forEach(turma => {
                const option = document.createElement("option");
                option.value = turma.id;
                option.textContent = turma.codigo;
                selectTurma.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao buscar turmas:", error));
}
carregarTurmas();
// carregar disciplinas

/**
 * Aplica a máscara de CPF no campo de entrada.
 * @param {HTMLInputElement} campo - O campo de entrada onde o CPF será digitado.
 */
function mascararCPF(campo) {
    campo.value = campo.value
        .replace(/\D/g, "") // Remove caracteres não numéricos
        .replace(/(\d{3})(\d)/, "$1.$2") // Adiciona o primeiro ponto
        .replace(/(\d{3})(\d)/, "$1.$2") // Adiciona o segundo ponto
        .replace(/(\d{3})(\d{1,2})$/, "$1-$2"); // Adiciona o traço
}

/**
 * Valida o CPF formatado.
 * @param {HTMLInputElement} campo - O campo de entrada onde o CPF será digitado.
 */
function validarCPF(campo) {
    const cpf = campo.value.replace(/\D/g, ""); // Remove caracteres não numéricos
    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) {
        campo.setCustomValidity("CPF inválido");
        campo.classList.add("is-invalid");
    } else {
        let soma = 0, resto;
        for (let i = 1; i <= 9; i++) soma += parseInt(cpf[i - 1]) * (11 - i);
        resto = (soma * 10) % 11;
        if (resto === 10 || resto === 11) resto = 0;
        if (resto !== parseInt(cpf[9])) {
            campo.setCustomValidity("CPF inválido");
            campo.classList.add("is-invalid");
            return;
        }
        soma = 0;
        for (let i = 1; i <= 10; i++) soma += parseInt(cpf[i - 1]) * (12 - i);
        resto = (soma * 10) % 11;
        if (resto === 10 || resto === 11) resto = 0;
        if (resto !== parseInt(cpf[10])) {
            campo.setCustomValidity("CPF inválido");
            campo.classList.add("is-invalid");
        } else {
            campo.setCustomValidity("");
            campo.classList.remove("is-invalid");
            campo.classList.add("is-valid");
        }
    }
}

/**
 * Valida o telefone para garantir que tenha 11 dígitos.
 * @param {HTMLInputElement} campo - O campo de entrada onde o telefone será digitado.
 */
function validarTelefone(campo) {
    const telefone = campo.value.replace(/\D/g, ""); // Remove caracteres não numéricos
    if (telefone.length !== 11) {
        campo.setCustomValidity("O telefone deve conter 11 números.");
        campo.classList.add("is-invalid");
    } else {
        campo.setCustomValidity("");
        campo.classList.remove("is-invalid");
        campo.classList.add("is-valid");
    }
}

/**
 * Valida a data de nascimento para garantir que o usuário tenha pelo menos 10 anos.
 * @param {HTMLInputElement} campo - O campo de entrada onde a data será digitada.
 */
function validarDataNascimento(campo, idadeMinima = 10) {
    const dataNascimento = new Date(campo.value);
    const hoje = new Date();
    const idade = hoje.getFullYear() - dataNascimento.getFullYear();
    if (idade < idadeMinima || (idade === idadeMinima && hoje < new Date(dataNascimento.setFullYear(hoje.getFullYear())))) {
        campo.setCustomValidity(`A idade mínima é ${idadeMinima} anos.`);
        campo.classList.add("is-invalid");
    } else {
        campo.setCustomValidity("");
        campo.classList.remove("is-invalid");
        campo.classList.add("is-valid");
    }
}

/**
 * Aplica automaticamente as máscaras e validações ao carregar a página.
 */
document.addEventListener("DOMContentLoaded", () => {
    // Máscara e validação de CPF
    const camposCPF = document.querySelectorAll("#cpf");
    camposCPF.forEach(campo => {
        campo.addEventListener("input", () => mascararCPF(campo));
        campo.addEventListener("blur", () => validarCPF(campo));
    });

    // Validação de telefone
    const camposTelefone = document.querySelectorAll("#telefone");
    camposTelefone.forEach(campo => {
        campo.addEventListener("input", () => validarTelefone(campo));
        campo.addEventListener("blur", () => validarTelefone(campo));
    });

    // Validação de data de nascimento
    const camposDataNascimento = document.querySelectorAll("#data_nascimento, #dataNascimento");
    camposDataNascimento.forEach(campo => {
        campo.addEventListener("blur", () => validarDataNascimento(campo, campo.id === "dataNascimento" ? 18 : 10));
    });
});
function validarCPF() {
    const input = event.target;
    let value = input.value.replace(/\D/g, ''); // Remove tudo que não for número

    // Formata automaticamente para o padrão XXX.XXX.XXX-XX
    if (value.length > 3 && value.length <= 6) {
        value = value.replace(/(\d{3})(\d+)/, '$1.$2');
    } else if (value.length > 6 && value.length <= 9) {
        value = value.replace(/(\d{3})(\d{3})(\d+)/, '$1.$2.$3');
    } else if (value.length > 9) {
        value = value.replace(/(\d{3})(\d{3})(\d{3})(\d+)/, '$1.$2.$3-$4');
    }
    
    input.value = value;

    // Quando tiver todos os 11 dígitos, valida o CPF
    if (value.length === 14) { 
        if (!isCPFValido(value)) {
            input.setCustomValidity('CPF inválido');
        } else {
            input.setCustomValidity('');
        }
    } else {
        input.setCustomValidity('CPF incompleto');
    }
}

function isCPFValido(cpf) {
    cpf = cpf.replace(/[^\d]+/g, ''); // Remove pontos e traço
    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) {
        return false;
    }
    let soma = 0;
    for (let i = 0; i < 9; i++) {
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    }
    let resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(9))) return false;

    soma = 0;
    for (let i = 0; i < 10; i++) {
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    }
    resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(10))) return false;

    return true;
}
function validarTelefone() {
    const input = event.target;
    let value = input.value.replace(/\D/g, ''); // Remove tudo que não for número

    // Formatação para (XX) XXXXX-XXXX
    if (value.length > 2 && value.length <= 7) {
        value = value.replace(/(\d{2})(\d+)/, '($1) $2');
    } else if (value.length > 7) {
        value = value.replace(/(\d{2})(\d{5})(\d+)/, '($1) $2-$3');
    }

    input.value = value;

    // Validação
    if (value.length === 15) { // formato completo: (00) 00000-0000 => 15 caracteres
        input.setCustomValidity('');
    } else {
        input.setCustomValidity('Telefone inválido. Deve conter 11 números.');
    }
}
