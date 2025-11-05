🧾 Descrição do Projeto – Sistema de Ordem de Serviço (Projeto 0S)

O Sistema de Ordem de Serviço (Projeto 0S) foi desenvolvido como parte do Trabalho Semestral Integrador, com o objetivo de aplicar conceitos avançados de Programação Orientada a Objetos em Java, boas práticas de projeto e padrões de arquitetura em camadas.

O sistema tem como finalidade gerenciar clientes e ordens de serviço (OS) de forma simples, eficiente e modular, permitindo o cadastro de clientes, criação de ordens de serviço, e o acompanhamento do status das OS (Abertas, Em Andamento e Finalizadas).

A aplicação foi implementada utilizando o padrão MVC (Model-View-Controller), separando claramente as responsabilidades:

O Model contém as classes principais de domínio, como Cliente e OS;

O DAO (Data Access Object) é responsável pela persistência dos dados em arquivos JSON, utilizando Collections (List, Map) para organizar e acessar as informações;

A View foi construída com Swing, oferecendo uma interface gráfica intuitiva, com abas para cada funcionalidade do sistema.

O projeto também aplica polimorfismo através da interface genérica GenericoDAO<T>, implementada pelas classes ClienteDAO e OSDAO, promovendo reuso e flexibilidade.

Além disso, o código foi modularizado em pacotes (model, dao, view), o que melhora a legibilidade, facilita a manutenção e reduz a complexidade geral do sistema.

O sistema grava e lê os dados em arquivos JSON com o auxílio da biblioteca Gson, garantindo persistência e fácil visualização dos dados armazenados.

Em resumo, este projeto demonstra a aplicação prática dos principais conceitos de engenharia de software, incluindo:

Orientação a Objetos (encapsulamento, herança e polimorfismo);

Padrões de projeto e modularização;

Coleções e Mapas para gerenciamento eficiente de dados;

Tratamento de exceções robusto;

Persistência simples em arquivos JSON;

Controle de versão com Git e GitHub;

Interface gráfica desenvolvida com Swing.
