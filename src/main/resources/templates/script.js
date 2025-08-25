function goTo(id) {
    // Esconde todas as seções
    document.querySelectorAll("section").forEach(sec => {
        sec.classList.remove("active");
    });

    // Mostra apenas a seção com o ID correspondente
    const targetSection = document.getElementById(id);
    if (targetSection) {
        targetSection.classList.add("active");
    } else {
        console.error("Erro: Nenhuma seção encontrada com o ID:", id);
    }
}

async function realizarCadastroCliente() {
    // Pega os valores dos campos de input
    const nome = document.getElementById('cliente-nome').value;
    const endereco = document.getElementById('cliente-endereco').value;
    const telefone = document.getElementById('cliente-telefone').value;
    const cpf = document.getElementById('cliente-cpf').value;

    // Cria um objeto com os dados do cliente
    const clienteData = {
        nome: nome,
        endereco: endereco,
        telefone: telefone,
        cpf: cpf,
        animais: []
    };

    try {
        // Envia os dados para o back-end (Java) usando fetch
        const response = await fetch('/api/clientes', {
            method: 'POST', // Método HTTP
            headers: {
                'Content-Type': 'application/json' // Informa que estamos enviando JSON
            },
            body: JSON.stringify(clienteData) // Converte o objeto JS para uma string JSON
        });

        // Verifica se a resposta do servidor foi bem-sucedida
        if (response.ok) {
            alert('Cliente cadastrado com sucesso!');
            // Limpa os campos do formulário
            document.getElementById('cliente-nome').value = '';
            document.getElementById('cliente-endereco').value = '';
            document.getElementById('cliente-telefone').value = '';
            document.getElementById('cliente-cpf').value = '';
            
            // Leva o usuário para a tela de cadastrar pets
            goTo('cadastro-pet');
        } 
		else {
            // Se o servidor retornou um erro
            alert('Erro ao cadastrar cliente. Verifique os dados e tente novamente.');
        }
    } 
	catch (error) {
        // Se houve um erro de rede (ex: back-end não está rodando)
        console.error('Erro na requisição:', error);
        alert('Não foi possível conectar ao servidor.');
    }
}