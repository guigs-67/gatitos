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

// Função que pega os objeto criado, e usa seus dados para preencher o html
function preencherEExibirNota(notaFiscal) {
    document.getElementById('cpf-nota').textContent = notaFiscal.cliente.cpf;
    document.getElementById('data-nota').textContent = new Date(notaFiscal.dataHora).toLocaleString('pt-BR');
    document.getElementById('idNota').textContent = notaFiscal.id;
    
    // Preenche a tabela de serviços
    const tabelaBody = document.getElementById('tabela-servicos');
    tabelaBody.innerHTML = ''; // Limpa qualquer conteúdo anterior da tabela
    let quantidadeTotalItens = 0;

    notaFiscal.itens.forEach(item => {
        const linha = document.createElement('tr');
        // Cria uma linha na tabela para cada item da nota
        linha.innerHTML = `
            <td>${item.descricao}</td>
            <td>${item.quantidade}</td>
            <td>R$ ${item.valorUnitario.toFixed(2)}</td>
            <td>R$ ${item.valorTotal.toFixed(2)}</td>
        `;
        tabelaBody.appendChild(linha);
        quantidadeTotalItens += item.quantidade;
    });

    // Preenche os totais da nota
    document.getElementById('qtdTotal').textContent = quantidadeTotalItens;
    document.getElementById('valorTotal').textContent = notaFiscal.totalFinal.toFixed(2);

    // leva até a nota finalizada
    goTo('nota');
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

// Função  para criar a nota e guardar o seu id
async function _iniciarNotaFiscal(cpf) {
    try {
        const response = await fetch('/api/notas-fiscais/iniciar', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ cpfCliente: cpf })
        });
        if (!response.ok) {
            const erroMsg = await response.text();
            throw new Error(erroMsg);
        }
        const novaNota = await response.json();
        idNotaFiscalAtiva = novaNota.id; // guarda o id da nota
        console.log(`Nota fiscal #${idNotaFiscalAtiva} iniciada.`);
        
        // Leva para o menu de serviços
        goTo('servicoMenu');
    } catch (error) {
        alert(`Erro ao iniciar nota: ${error.message}`);
        goTo('menu');
    }
}

// Função para adicionar serviço na nota
async function selecionarServico(tipoServico) {
    if (!idNotaFiscalAtiva) {
        alert("Erro: Nenhuma nota fiscal ativa. Por favor, reinicie o processo.");
        return;
    }
    try {
        const response = await fetch(`/api/notas-fiscais/${idNotaFiscalAtiva}/adicionar-servico`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ servico: tipoServico })
        });
        if (!response.ok) {
            const erroMsg = await response.text();
            throw new Error(erroMsg);
        }
        alert(`Serviço '${tipoServico}' adicionado com sucesso!`);
        // volta para o menu de serviços para o usuário poder adicionar mais
        goTo('servicoMenu');
    } catch (error) {
        alert(`Erro ao adicionar serviço: ${error.message}`);
    }
}

// Emite a nota fiscal com seus dados corretos
async function visualizarNotaFiscalAtiva() {
    if (!idNotaFiscalAtiva) {
        alert("Nenhum serviço foi adicionado para emitir a nota.");
        return;
    }
    try {
        const response = await fetch(`/api/notas-fiscais/${idNotaFiscalAtiva}`);
        if (!response.ok) {
            const erroMsg = await response.text();
            throw new Error(erroMsg);
        }
        const notaFiscalFinal = await response.json();
        preencherEExibirNota(notaFiscalFinal);
        idNotaFiscalAtiva = null; // Limpa a nota ativa, pois o "carrinho" foi fechado
    } catch (error) {
        alert(`Erro ao buscar nota fiscal: ${error.message}`);
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

// lista os clientes para esolher quem será removido
async function listarClientesParaRemover() {
    try {
        const response = await fetch('/api/clientes');
        if (!response.ok) throw new Error('Falha ao buscar clientes.');

        const clientes = await response.json();
        const container = document.getElementById('container-lista-clientes-remover');
        container.innerHTML = ''; // Limpa a lista antes de preencher

        if (clientes.length === 0) {
            container.innerHTML = '<p style="font-size:28px;">Nenhum cliente cadastrado.</p>';
        } else {
            clientes.forEach(cliente => {
                const btn = document.createElement('div');
                btn.className = 'btn btn-danger';
                // Ao clicar, chama a função de confirmação, passando o CPF e o NOME
                btn.onclick = () => confirmarRemocaoCliente(cliente.cpf, cliente.nome); 
                btn.innerHTML = `<span>${cliente.nome}<br><small>CPF: ${cliente.cpf}</small></span>`;
                container.appendChild(btn);
            });
        }
        
        // Leva o usuário para a tela com a lista de remoção
        goTo('lista-clientes-para-remover');

    } catch (error) {
        console.error('Erro ao listar clientes:', error);
        alert('Não foi possível carregar a lista de clientes.');
    }
}

// Pede confirmação e chama o endpoint DELETE.
async function confirmarRemocaoCliente(cpf, nome) {
    const querRemover = confirm(`Tem certeza que deseja remover o cliente "${nome}"? \n\nEsta ação não pode ser desfeita.`);

    if (querRemover) { // O código só continua se o usuário clicar em OK
        try {
            const response = await fetch(`/api/clientes/${cpf}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                const mensagemSucesso = await response.text();
                alert(mensagemSucesso);
                // Após remover, volta para o menu principal
                goTo('menu');
            } else {
                const mensagemErro = await response.text();
                alert(mensagemErro);
            }
        } catch (error) {
            console.error('Erro na requisição de remoção:', error);
            alert('Não foi possível conectar ao servidor.');
        }
    }
    // Se o usuário clicar em "Cancelar", nada acontece.
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
    const cpf = document.getElementById('cpf-busca').value;
    if (!cpf) {
        alert('Por favor, insira um CPF.');
        return;
    }
    cpfClienteAtual = cpf;
    try {
        const response = await fetch(`/api/animais/cliente/${cpf}`);
        if (response.ok) {
            const animais = await response.json();
            const petsContainer = document.getElementById('pets-container');
            petsContainer.innerHTML = '';

            if (animais.length > 0) {
                animais.forEach(animal => {
                    const petButton = document.createElement('div');
                    petButton.className = 'btn';
                    petButton.onclick = () => selecionarPetParaServico(animal); 
                    petButton.innerHTML = `<span>${animal.nome}<br><small>(${animal.raca})</small></span>`;
                    petsContainer.appendChild(petButton);
                });
            } else {
                petsContainer.innerHTML = '<p style="font-size:28px;">Nenhum pet encontrado para este CPF.</p>';
            }
            goTo('pets');
        } else {
            alert('Cliente não encontrado ou erro ao buscar pets.');
        }
    } catch (error) {
        console.error('Erro de rede ao buscar pets:', error);
        alert('Não foi possível conectar ao servidor.');
    }
}
// Ela é chamada pelo clique no botão do pet.
function selecionarPetParaServico(animal) {
    // Primeiro, ela salva qual pet foi escolhido
    petSelecionado = animal; 
    console.log("Pet selecionado:", petSelecionado);
    
    // Depois, ela inicia a nota fiscal
    _iniciarNotaFiscal(cpfClienteAtual);
}

// Cadastra o pet e, em caso de sucesso, volta para o menu principal.
async function realizarCadastroPetEVoltar() {
    // Verifica se temos um CPF de cliente para associar o pet
    if (!cpfClienteAtual) {
        alert('Erro: Nenhum cliente selecionado. Por favor, cadastre o cliente primeiro.');
        goTo('cadastro-cliente');
        return;
    }

    // Pega os valores do formulário do pet
    const nome = document.getElementById('pet-nome').value;
    const especie = document.getElementById('pet-especie').value;
    const raca = document.getElementById('pet-raca').value;
    const porte = document.getElementById('pet-porte').value;
    const peso = parseInt(document.getElementById('pet-peso').value);
    const sexo = 'M'; // Assumindo 'M' por padrão

    const animalData = { nome, especie, raca, porte, peso, sexo, cpfDono: cpfClienteAtual };

    try {
        // Envia os dados para o back-end
        const response = await fetch('/api/animais', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(animalData)
        });

        if (response.ok) {
            alert(`Pet '${nome}' cadastrado com sucesso!`);
            // Limpa o formulário do pet
            document.getElementById('pet-nome').value = '';
            document.getElementById('pet-especie').value = '';
            document.getElementById('pet-raca').value = '';
            document.getElementById('pet-porte').value = '';
            document.getElementById('pet-peso').value = '';
            
            // Volta para o menu
            goTo('menu'); 
        } else {
            alert('Erro ao cadastrar o pet.');
        }
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Não foi possível conectar ao servidor.');
    }
}
}