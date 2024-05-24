<p align="center">
  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/Yara-R/DuvidaZero?color=353949">

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/Yara-R/DuvidaZero">

  <a href="https://github.com/Yara-R/DuvidaZero/commits/main">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Yara-R/DuvidaZero">
  </a>

   <img alt="License" src="https://img.shields.io/badge/license-MIT-brightgreen">

  <a href="https://www.linkedin.com/in/yara-rodrigues-b14203236/">
    <img alt="Made by Yara Rodrigues" src="https://img.shields.io/badge/made_by-Yara_Rodrigues-353949">
  </a>
</p>


# DúvidaZero

Este é o repositório da aplicação web do Cursinho Duvida Zero, desenvolvida em Java com o framework Quarkus. O sistema tem como objetivo principal gerenciar informações relacionadas a professores, alunos e aulas, facilitando as operações do cursinho e oferecendo suporte completo para suas atividades educacionais.

## Software Necessário

  - JDK 21 ou superior
  - Maven
  - MySQL



## Dependências

As dependências da aplicação são gerenciadas pelo Maven e estão definidas no arquivo pom.xml. Não é necessário instalar nada manualmente; todas as dependências serão resolvidas automaticamente durante o build.



## Configuração

A aplicação utiliza um banco de dados MySQL. Certifique-se de configurar as propriedades de conexão nos arquivos Service.java localizados no diretorio 'duvida_zero/src/main/java/br/com/yaraf'



## Executando a Aplicação

Para executar a aplicação localmente, siga os passos abaixo:



### Clone o repositório:

    git clone https://github.com/Yara-R/DuvidaZero.git
    cd ./DuvidaZero
    cd ./duvida_zero



### Compile e inicie a aplicação:

    ./mvnw compile quarkus:dev

### Acesse a interface web:


No seu navegador acesse:

    http://localhost:8080/home
