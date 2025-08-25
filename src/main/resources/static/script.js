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
            //Lê a resposta de erro como texto
            const mensagemErro = await response.text();
            
            // Exibe a mensagem específica a depender do erro
            alert(mensagemErro);
            
            cpfClienteAtual = ''; // Limpa o CPF em caso de erro
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Não foi possível conectar ao servidor.');
        cpfClienteAtual = ''; // Limpa o CPF em caso de erro
    }
}


// busca um cliente através de seu cpf para poder atualiza-lo
async function buscarClienteParaAtualizar() {
    const cpf = document.getElementById('cpf-busca-atualizacao').value;
    if (!cpf) {
        alert('Por favor, insira um CPF.');
        return;
    }

    try {
        // Usa o endpoint para buscar um cliente específico
        const response = await fetch(`/api/clientes/${cpf}`);

        if (response.ok) {
            const cliente = await response.json();

            // Preenche os campos do formulário de edição com os dados encontrados
            document.getElementById('update-cpf').value = cliente.cpf;
            document.getElementById('update-nome').value = cliente.nome;
            document.getElementById('update-endereco').value = cliente.endereco;
            document.getElementById('update-telefone').value = cliente.telefone;

            // Leva o usuário para a tela de edição, já com os dados preenchidos
            goTo('atualizar-cliente');
        } else {
            alert(`Cliente com CPF ${cpf} não encontrado.`);
        }
    } catch (error) {
        console.error('Erro ao buscar cliente:', error);
        alert('Não foi possível conectar ao servidor.');
    }
}

// 3. Envia os dados atualizados para o backend
async function realizarAtualizacaoCliente() {
    // Pega os dados dos campos do formulário de edição
    const cpf = document.getElementById('update-cpf').value;
    const nome = document.getElementById('update-nome').value;
    const endereco = document.getElementById('update-endereco').value;
    const telefone = document.getElementById('update-telefone').value;

    const dadosAtualizados = { nome, endereco, telefone, cpf };

    try {
        const response = await fetch(`/api/clientes/${cpf}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosAtualizados)
        });

        if (response.ok) {
            alert('Cliente atualizado com sucesso!');
            goTo('menu'); // Volta para o menu principal
        } else {
            const mensagemErro = await response.text();
            alert(mensagemErro);
        }
    } catch (error) {
        console.error('Erro na requisição de atualização:', error);
        alert('Não foi possível conectar ao servidor.');
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

// Função para buscar pets pelo CPF do cliente
async function buscarPetsPorCPF() {
    // Pega o CPF digitado pelo usuário
    const cpf = document.getElementById('cpf-busca').value;
    if (!cpf) {
        alert('Por favor, insira um CPF.');
        return;
    }

    // Armazena o CPF para uso futuro (ao criar a nota fiscal)
    cpfClienteAtual = cpf;

    try {
        // Chama o endpoint do back-end para buscar os animais
        // A URL corresponde ao @GetMapping("/cliente/{cpf}") no AnimalController
        const response = await fetch(`/api/animais/cliente/${cpf}`);

        if (response.ok) {
            const animais = await response.json(); // Converte a resposta em um array de objetos

            // Pega o contêiner onde os botões dos pets serão exibidos
            const petsContainer = document.getElementById('pets-container');
            petsContainer.innerHTML = ''; // Limpa qualquer conteúdo anterior

            if (animais.length > 0) {
                // Para cada animal encontrado, cria um botão
                animais.forEach(animal => {
                    const petButton = document.createElement('div');
                    petButton.className = 'btn';
                    // Futuramente, este onclick irá para a seleção de serviço
                    petButton.onclick = () => alert(`Você selecionou ${animal.nome}`); 
                    petButton.innerHTML = `<span>${animal.nome}<br><small>(${animal.raca})</small></span>`;
                    petsContainer.appendChild(petButton);
                });
            } else {
                petsContainer.innerHTML = '<p style="font-size:28px;">Nenhum pet encontrado para este CPF.</p>';
            }

            // Leva o usuário para a tela de seleção de pets
            goTo('pets');

        } else {
            alert('Cliente não encontrado ou erro ao buscar pets.');
        }
    } catch (error) {
        console.error('Erro de rede ao buscar pets:', error);
        alert('Não foi possível conectar ao servidor.');
    }
}