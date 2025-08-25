let cpfClienteAtual = '';

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
    const nome = document.getElementById('cliente-nome').value;
    const endereco = document.getElementById('cliente-endereco').value;
    const telefone = document.getElementById('cliente-telefone').value;
    const cpf = document.getElementById('cliente-cpf').value;

    // Armazena o CPF na variável global antes de enviar
    cpfClienteAtual = cpf;

    const clienteData = { nome, endereco, telefone, cpf, animais: [] };

    try {
        const response = await fetch('/api/clientes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(clienteData)
        });

        if (response.ok) {
            alert('Cliente cadastrado com sucesso! Agora cadastre os pets.');
            // Limpa o formulário de cliente
            document.getElementById('cliente-nome').value = '';
            document.getElementById('cliente-endereco').value = '';
            document.getElementById('cliente-telefone').value = '';
            document.getElementById('cliente-cpf').value = '';
            goTo('cadastro-pet');
        } else {
            alert('Erro ao cadastrar cliente.');
            cpfClienteAtual = ''; // Limpa o CPF em caso de erro
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Não foi possível conectar ao servidor.');
        cpfClienteAtual = ''; // Limpa o CPF em caso de erro
    }
}

async function realizarCadastroPet() {
    // 1. Verifica se temos um CPF de cliente para associar o pet
    if (!cpfClienteAtual) {
        alert('Erro: Nenhum cliente selecionado. Por favor, cadastre o cliente primeiro.');
        goTo('cadastro-cliente');
        return;
    }

    // 2. Pega os valores do formulário do pet
    const nome = document.getElementById('pet-nome').value;
    const especie = document.getElementById('pet-especie').value;
    const raca = document.getElementById('pet-raca').value;
    const porte = document.getElementById('pet-porte').value;
    const peso = parseInt(document.getElementById('pet-peso').value);
    
    // Simples lógica para pegar o sexo (pode ser melhorada)
    const sexo = 'M'; // Assumindo 'M' por padrão, você pode adicionar a lógica para os botões

    // 3. Cria o objeto do animal, incluindo o cpfDono
    const animalData = {
        nome,
        especie,
        raca,
        porte,
        peso,
        sexo,
        cpfDono: cpfClienteAtual // Vincula o pet ao cliente
    };

    try {
        // 4. Envia os dados para o novo endpoint no back-end
        const response = await fetch('/api/animais', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(animalData)
        });

        if (response.ok) {
            alert(`Pet '${nome}' cadastrado com sucesso para o cliente de CPF ${cpfClienteAtual}!`);
            // Limpa o formulário do pet para adicionar outro
            document.getElementById('pet-nome').value = '';
            document.getElementById('pet-especie').value = '';
            document.getElementById('pet-raca').value = '';
            document.getElementById('pet-porte').value = '';
            document.getElementById('pet-peso').value = '';
        } else {
            alert('Erro ao cadastrar o pet.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Não foi possível conectar ao servidor.');
    }
}