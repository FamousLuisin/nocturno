# Especificações do Projeto

## Escopo do sistema

Esse projeto tem como base o X (falecido twitter), e tem como objetivo permitir que os usuarios posssam publicar posts, comentar nesses postas, curtir e salvar posts, seguir outros usuarios, conversar via chat com outras pessoas e enviar posts como mensagem.

- **Obs:** Esse é um projeto pessoal para aprendizado.

## Requisitos funcionais

- Usuário pode se cadastrar, logar.
- Usuário pode editar seu perfil.
- Usuário pode seguir outro usuário e deixar de seguir.
- Usuário pode criar, editar, apagar, curtir e salvar posts.
- Usuário pode comentar no post e responder comentários e curtir coemntários.
- Usuário pode criar e participar de chats.
- Usuário pode enviar convites para chats em grupo.
- Usuário pode criar, editar, curtir e apagar mensagens.

## Requisitos não funcionais

- Segurança: Senhas criptografadas usando bcryp.
- Segurança: Autenticação via jwt.
- Usabilidade: Interface simples e responsiva.
- Usabilidade: Seguir padrões de UX/UI.
- Usabilidasde: Desing responsivo para desktop e mobile.
- Performance: API deve responder em menos de 200ms para requisições comuns.

## Front-end

O **Client** é desenvolvido utilizando **TypeScript** com **React**, por ser uma biblioteca reativa, eficiente e bastante consolidada no mercado com uma grande comunidade ativa. Além disso, possuo familiaridade com ambas tecnologias. Também é usado o **Vite** como build tool, por ser bom, simples e interagir muito bem com **React**.

## Back-end

A **API** é desenvolvida com **Java** utilizando o **Spring Boot**, escolhidos pela robustez, segurança e pela grande quantidade de conteúdo e soluções na internet. A estrutura do projeto seguirá boas práticas de versionamento de banco de dados com o uso do **Flyway**. Além disso, é usado o **Gradle** como build tool, por ser bem simples, rápida e também por minha familiaridade. Também sera usado como meio de autenticação e autorização o token **jwt** que será criptografado usando criptográfia assimétrica.

## Banco de dados

O sistema utilizará **PostgreSQL** como banco de dados relacional, pela sua confiabilidade, desempenho e compatibilidade com ferramentas modernas de desenvolvimento.
