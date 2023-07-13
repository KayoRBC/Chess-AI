# ♟️Chess-AI♟️

## 📋Tópicos
- [📚Sobre o projeto](#📚sobre-o-projeto)
- [🔮Planejamentos futuros](#🔮planejamentos-futuros)
- [🔧Tecnologias](#🔧tecnologias)
- [💡Como utilizar](#💡como-utilizar)
- [▶️Como executar o projeto](#▶️como-executar-o-projeto)
- [📁Diretórios](#📁diretórios)
- [🔗Links](#🔗links)


## 📚Sobre o projeto
Neste projeto, o objetivo era criar um jogo em que o jogador joga xadrez contra uma IA. 

O que me levou a fazer esse projeto foi a disciplina de Inteligência Artificial do curso de Ciência da Computação da [PUCPR](https://www.pucpr.br). Um dos trabalhos era aplicar o algoritimo [**Alpha-Beta**](https://en.wikipedia.org/wiki/Alpha–beta_pruning) para predizer o melhor movimento esperado dado um estado do tabuleiro de xadrez. Após a apresentação do trabalho, decidi melhorar o código previamente desenvolvido e disponibilizá-lo.

## 🔮Planejamentos futuros
* Alterar a documentação e os comentários do código para inglês.
* Adicionar uma imagem de fundo à tela inicial.
* Implementar o movimento [en passant](https://pt.wikipedia.org/wiki/En_passant).

## 🔧Tecnologias
* Java
* Maven
* Java Swing 

## 💡Como utilizar
Com o código compilado e o Java JRE instalado, execute o programa.
### Tela inicial
Na tela inicial, selecione a cor das suas peças de xadrez. A IA escolherá
automaticamente a cor oposta escolhida. Em seguida clique
no botão "Jogar" para iniciar a partida.

(gif da tela inicial)

### Tela do tabuleiro
Nesta tela, o jogador pode selecionar duas posições para fazer um movimento.

(gif mostrando as peças sendo movimentadas)

Caso algum peão do jogador chegue ao final do tabuleiro, 
serão exibidas opções no centro do tabuleiro para mudar o tipo do peão.

(gif selecionando um tipo de peça)

Durante a partida, o histórico de jogadas será exibido no terminal.

(gif mostrando o terminal)

Além disso, se o jogador ou a IA vencererem, uma mensagem será exibida no centro
do tabuleiro informando quem venceu, juntamente com um botão para retornal à tela
inicial.

(gif mostrando alguem vencendo e retornando à tela inicial)

## ▶️Como executar o projeto
Clone o projeto em uma cópia local usando o Git em um terminal com o Git instalado.
```bash
git clone link-do-projeto
```
Abra e rode o arquivo Main. IDE recomendada: IntelliJ

## 📁Diretórios
* ```./src/main/resources/images``` Imagens utilizadas

* ```./src/main/java/com/chess_AI/controller/ImageController``` Responsável por carregar as imagens

* ```./src/main/java/com/chess_AI/controller/AIController``` Responsável por utilizar o algoritmo Alpha-Beta

* ```./src/main/java/com/chess_AI/controller/BoardController``` Responsável por realizar as jogadas e as regras do jogo

* ```./src/main/java/com/chess_AI//model``` Possui modelos da AI e de Board

* ```./src/main/java/com/chess_AI/view``` Front-end do jogo feito com a biblioteca Java Swing

* ```./src/main/java/com/chess_AI/util``` Classes e funções utilizadas em todas as camadas

## 🔗Links
* Link do author do código
* [Imagens do tabuleiro e das peças](https://opengameart.org/content/chess-pieces-and-board-squares)