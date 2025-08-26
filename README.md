"# gatitos" #

## Gatitos Pet Care - SIstema de Gestão

*Gatitos é um projeto de Sistema de gestão para clínicas veterinárias e petshops, projeto desenvolvido para a disciplina de Programação Orientada a Objetos na Universidade Federal de Sergipe - Ufs. O projeto foi desenvolvido em Java utilizando os conceitos de Programação Orientada a Objeto, focando em garantir os pilares, utilizar classes abstratas, persistência de dados e padrão de projeto além da arquitetura em camadas (controller, service e model).*

---

## Sumário
1. Descrição do Projeto
2. Funcionalidades
3. Status do Projeto
4. Tecnologias Utilizadas
5. Padrões de Projeto
6. Autores

---

## Descrição do Projeto

*Gatitos é um projeto de Sistema de gestão para clínicas veterinárias e petshops, projeto desenvolvido para a disciplina de Programação Orientada a Objetos na Universidade Federal de Sergipe - Ufs. O projeto foi desenvolvido em Java utilizando os conceitos de Programação Orientada a Objeto, focando em garantir os pilares, utilizar classes abstratas, persistência de dados e padrão de projeto Composite, além da arquitetura em camadas MVC (controller, service e model).*

---

##  Funcionalidades

- Cadastrar Cliente.
- Atualizar Cliente.
- Remover Cliente.
- Cadastrar Animal e relacionar ao cliente.
- Atualizar animal relacionado ao cliente.
- Remover o Animal relacionado ao cliente.
- Adição de Serviços individuais ("Saúde = Consulta e vacinação" e "Estética = Tosa, Banho) à nota fiscal do cliente.
- Adição de pacotes de serviços usando o padrão de projeto Composite (Estético = Dia de spa, Combo banho e tosa)
- Cálculos automáticos de subtotais e total final em cada nota
- Visualização da nota fiscal com detalhes do cliente, serviços escolhidos, valores, subtotal, preços totais e id da nota.
- Impressão nota fiscal.
- Persistência de dados para Cliente e Animal 

## Possíveis Funcionalidades Futuras

- Pesquisar nota fiscais por ID para visualização e impressão.
- Adicionar serviços e remover serviços

## Status do Projeto

> MVP Finalizado.

## Tecnologias Utilizadas

- Java 
- HTTML
- CSS
- Java Script
- Spring Boot
- Maven

## Padrão de Projeto

- MVC: O MVC foi escolhido como padrão de projeto base ele serve para organizar o nosso código em três componentes principais que serão interligados ele facilita o desenvolvimento e a manuntenção do código. O Model age como o coração da aplicação contem a representação dos dados, e as lógicas de negócio e o estado da aplicação (nesse projeto ele é a junção da Model com a Service) a View é a interface com o usuário que captura as ações do usuário e envia para a Controller e a Controller e a sessão intermediária que recebe as resquisições da view e solicita para a Model.


- Composite: O Composite foi escolhido como padrão de projeto porque ele habilita a possibilidade de compor objetos em estrutura de árvores mas trabalhar com essas estruturas como se fossem objetos individuais, esse padrão de projeto funciona muito bem em sistemas que precisem lidar com vendas de combos, por exemplo. No nosso sistema como há vendas de serviços e em alguns casos o cliente poderá solicitar um combo desses serviços em prol de receber algum desconto, um objeto em estrutura de árvore pode agir como a caixa Combo que abriga as informações de outros objetos e trata-os como um único objeto facilitando todo o processo.
 

## Autores
Davison de Jesus Santos 
Jeferson Gonzaga dos Santos Filho
Guilherme Santana Mendes
