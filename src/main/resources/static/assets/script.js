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
