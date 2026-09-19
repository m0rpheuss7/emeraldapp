# Emerald Vault — Android

Aplicativo Android que disponibiliza, em formato mobile, a plataforma educacional **Emerald Vault**. O app usa uma interface em **Jetpack Compose** e integra a aplicação web publicada no **Firebase Hosting** por meio de uma `WebView` configurada para navegação responsiva.

> Projeto acadêmico desenvolvido no curso Técnico em Desenvolvimento de Sistemas do SENAI. A plataforma web foi construída por uma equipe de 8 integrantes; esta versão Android foi desenvolvida por **Matheus Ricci dos Santos**.

## O que o aplicativo oferece

- Acesso à plataforma Emerald Vault diretamente no Android;
- navegação interna com suporte ao botão Voltar;
- upload e seleção de arquivos pelo dispositivo;
- abertura de links externos no aplicativo adequado;
- suporte a JavaScript, armazenamento local e reprodução de mídia;
- ajuste de viewport, texto e zoom para uso em telas menores.

## Tecnologias

- Kotlin
- Jetpack Compose
- Android WebView
- Firebase Hosting
- Gradle

## Arquitetura resumida

A atividade principal monta a interface com Compose e incorpora uma `WebView`. A aplicação mantém a navegação da plataforma hospedada no Firebase dentro do app e redireciona links externos para o navegador ou aplicativo correspondente.

## Como executar

1. Clone este repositório.
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Execute em um emulador ou dispositivo com Android 7.0 (API 24) ou superior.

```bash
git clone https://github.com/m0rpheuss7/emeraldapp.git
cd emeraldapp
./gradlew assembleDebug
```

O APK de desenvolvimento será gerado em `app/build/outputs/apk/debug/`.

## Projeto relacionado

A plataforma web, com autenticação, painel do aluno, módulos de curso, ranking, área administrativa e suporte offline como PWA, está disponível no repositório da equipe:

- [Emerald Vault — plataforma web](https://github.com/vittrC/tcc-senai)

## Minha atuação

Além de desenvolver esta versão Android, atuei como **Scrum Master** no projeto Emerald Vault: acompanhei uma equipe de 8 integrantes durante 4 ciclos de entrega, conduzi dailies, apoiei a organização do Product Backlog, removi impedimentos e colaborei na publicação da plataforma no Firebase dentro do prazo acadêmico.

## Autor

**Matheus Ricci dos Santos**  
[GitHub](https://github.com/m0rpheuss7) · [LinkedIn](https://www.linkedin.com/in/matheus-ricci-santos)
