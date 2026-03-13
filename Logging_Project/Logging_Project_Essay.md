# Engenharia de Software II

## Épico 1 - **Componente para Registo de Logs**

[Untitled](https://www.notion.so/31f03cc612d180aeac30e9b065013df2?pvs=21)

### **Objetivo**

O objetivo deste trabalho é aplicar **padrões de desenho de software** para criar um sistema de logs **flexível, extensível e eficiente**, capaz de evoluir com novas funcionalidades e necessidades da aplicação.

### Descrição

Pretende-se desenvolver uma aplicação que implemente um **sistema de registo de logs** para monitorização de aplicações, permitindo registar eventos, erros e informações relevantes de forma estruturada e extensível.

A aplicação deverá ser composta pelos seguintes módulos:

**M1. Módulo de Configuração Centralizada:**

Crie um módulo responsável por armazenar as configurações globais do sistema de logs (níveis de log, destino dos registos, formato das mensagens, entre outros), garantindo um **ponto de acesso único** às configurações e evitando a criação de múltiplas instâncias inconsistentes ao longo da aplicação.

**M2. Módulo de Criação de Registos de Log:**

Implemente um mecanismo que permita a criação de diferentes tipos de registos (por exemplo: INFO, WARNING, ERROR, DEBUG), encapsulando a lógica de criação dos objetos de log. O sistema deverá permitir a adição futura de novos tipos de registos sem alterar o código existente.

**M3. Módulo de Abstração de Destino de Logs:**

Desenvolva um sistema que suporte múltiplos destinos para armazenamento dos logs, como **ficheiro local, consola, base de dados ou serviços remotos**, permitindo alterar ou adicionar novos destinos sem modificar o restante sistema.

**M4. Módulo de Estruturação de Registos:**

Implemente uma estrutura que permita organizar os logs por **categorias ou componentes da aplicação** (por exemplo: autenticação, base de dados, interface, rede), tratando de forma uniforme grupos de logs e registos individuais, facilitando a filtragem e análise.

**M5. Módulo de Otimização de Recursos:**

Crie um mecanismo que permita **reutilizar objetos frequentemente utilizados**, como formatadores de mensagens ou conexões a destinos de armazenamento, evitando a criação repetida de objetos que consomem muitos recursos.

**M6. Módulo de Armazenamento de Estado do Sistema de Logs:**

Implemente uma funcionalidade que permita **guardar e restaurar o estado do sistema de logs**, incluindo configurações de níveis ativos, destinos utilizados e filtros aplicados, sem expor os detalhes internos da implementação.

**M7. Módulo de Extensão de Funcionalidades:**

Adicione a capacidade de **integrar novas funcionalidades dinamicamente**, como envio automático de alertas para administradores, integração com sistemas de monitorização ou análise automática de padrões de erro, sem necessidade de modificar a estrutura principal do sistema.

## Avaliação

A avaliação será feita semanalmente no regime de sprints durante o período da aula.

### Critérios

O trabalho será avaliado de acordo com os seguintes critérios:

- Cumprimentos dos objetivos (30%)
- Qualidade da solução (40%)
- Qualidade da Documentação (30%)
- **Defesa do trabalho (fator transversal à avaliação)**

Cada grupo deverá apresentar uma solução para cada uma das tarefas (diagrama de Classes e Código) e será responsável pela privacidade/integridade do seu código. O plágio de soluções e de código implica a anulação dos trabalhos.

## Regras do Sprint

No início (*kickoff*) de cada sprint (início da aula), deverá ser eleito, dentro do grupo, um responsável (Product Owner) pela definição das tarefas e pela sua execução.

Na conclusão do sprint, deverá ser publicado um *commit* (**pelo Product Owner do sprint terminado**) com a nova versão do código no repositório de código do grupo (Git), **marcado com a mensagem** ***Sprint n*** e o respetivo **Diagrama de Classes**, com a marcação dos papéis das classes que fazem parte do padrão.

**O Product Owner desse sprint será responsável por defender o trabalho na aula seguinte à data de conclusão do mesmo.**

Embora não considerados para efeitos de avaliação, pode haver *commits* intermédios.