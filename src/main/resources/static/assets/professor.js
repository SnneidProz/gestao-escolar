document.addEventListener("DOMContentLoaded", function () {
    const formProfessor = document.querySelector("#formularioProfessor");

    formProfessor.addEventListener("submit", function (event) {
        event.preventDefault();

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
            headers: { "Content-Type": "application/json" },
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