document.addEventListener("DOMContentLoaded", function () {
    const formAluno = document.querySelector("#formularioAluno");

    formAluno.addEventListener("submit", function (event) {
        event.preventDefault();

        // Captura os valores do formulário
        const aluno = {
            nome: document.querySelector("#nome").value,
            dataNascimento: document.querySelector("#dataNascimento").value,
            cpf: document.querySelector("#cpf").value,
            endereco: document.querySelector("#endereco").value,
            telefone: document.querySelector("#telefone").value,
            email: document.querySelector("#email").value
        };

        // Envia os dados para o backend
        fetch("http://localhost:8080/api/aluno", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(aluno)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao cadastrar aluno.");
            }
            return response.json();
        })
        .then(data => {
            alert("Aluno cadastrado com sucesso!");
            formAluno.reset();
        })
        .catch(error => console.error("Erro ao cadastrar aluno:", error));
    });
});