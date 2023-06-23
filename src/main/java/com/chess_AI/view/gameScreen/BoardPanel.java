package com.chess_AI.view.gameScreen;

import com.chess_AI.controller.AIController;
import com.chess_AI.controller.BoardController;
import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class BoardPanel extends JComponent{

    // tamanho das imagens das pecas
    private final int IMAGE_SIZE;

    // cor do jogador
    private final PieceColor USER_COLOR;

    // controladores
    private final BoardController BOARD_CONTROLLER;
    private final AIController AI_CONTROLLER;

    // posicao dos botoes selecionados
    private int fromLineButton = 0;
    private int fromColumnButton = 0;
    private int toLineButton = 0;
    private int toColumnButton = 0;
    private int select = 0;

    public BoardPanel(int screenWidth, int screenHeight, PieceColor userColor, int imageSize) {
        IMAGE_SIZE = imageSize;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.USER_COLOR = userColor;

        boolean isUserStart = false;
        if(USER_COLOR == PieceColor.WHITE) isUserStart = true;

        BOARD_CONTROLLER = new BoardController(isUserStart, userColor);
        AI_CONTROLLER = new AIController(PieceColor.getOpponentOf(USER_COLOR), BOARD_CONTROLLER);
    }

    public void start(){
        addPiecesButtons();
        // se o jogador nao comecar
        if(!BOARD_CONTROLLER.isUserTurn()){
            AI_CONTROLLER.play();
        }
        repaint();
    }

    // chamado pelo metodo repaint()
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // desenhando fundo
        drawBackGround(g2);
        // desenhando pecas
        drawPieces(g2);

        g2.dispose();
    }

    // desenha o fundo do tabuleiro
    private void drawBackGround(Graphics2D g2){
        // carregando imagens de fundo
        BufferedImage[][] background = ImageController.loadBackground();
        // percorrendo imagens
        for(int i = 0; i < background.length; i++){
            for(int j = 0; j < background.length; j++){
                // desenhando imagem na respectiva posicao
                g2.drawImage(background[i][j], j * IMAGE_SIZE, i * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, null);
            }
        }
    }

    // desenha as pecas na tela
    private void drawPieces(Graphics2D g2){
        // percorrendo posicoes de pecas
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                PieceType type = BOARD_CONTROLLER.getTypeOf(i, j);
                PieceColor color = BOARD_CONTROLLER.getColorOf(i, j);
                // carregando imagem da respectiva peca e cor
                BufferedImage image = ImageController.loadPiece(type, color);
                // se existir imagem
                if(image != null) {
                    // desenhando imagem da peca
                    g2.drawImage(image, j * IMAGE_SIZE, i * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, null);
                }
            }
        }
    }

    // insere os botoes das pecas na tela
    private void addPiecesButtons(){
        // removendo todos os componentes
        removeAll();
        // percorrendo posicoes do tabuleiro
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // criando botao da peca
                JButton button = new PieceButton(i, j, this);
                // inserindo botao na tela
                button.setBounds(j * IMAGE_SIZE, i * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
                add(button);
            }
        }
    }

    // funcao que os botoes das pecas chamam para inserir os valores das posicoes from e to
    // return true caso tenha sido selecionado
    // return false cao nao tenha sido selecionado
    public boolean selectPosition(int line, int column){
        if(BOARD_CONTROLLER.isUserTurn()) {
            // se botoes selecionados for 0
            if (select == 0) {
                // inserindo posicao do primeiro botao
                fromLineButton = line;
                fromColumnButton = column;
            }
            // se botoes selecionas for 1
            else if (select == 1) {
                // inserindo posicao do segundo botao
                toLineButton = line;
                toColumnButton = column;
            }
            // atualizando contagem de botoes selecionados
            select++;

            // se botoes selecionados for 2
            if (select == 2) {
                System.out.println("Usuario tentanto mover:");
                System.out.println("From line: " + fromLineButton + " Column: " + fromColumnButton + " | To line: " + toLineButton + " Column: " + toColumnButton);
                // iniciando jogadas do jogador e do oponente
                play();
                select = 0;
            }
            return true;
        }
        return false;
    }

    // inicia jogadas do usuario e do oponente
    private void play(){
        // jogador tenta fazer jogada
        userPlay();

        // adicionando componentes dos botoes
        addPiecesButtons();

        // atualizando tela imediatamente
        paintImmediately(getBounds());

        // oponente tenta fazer jogada
        opponentPlay();

        // verificando vitoria
        verifyWin();

        // atualizando tela
        repaint();
    }

    // pede ao usuario selecionar um tipo de peca e retorna ela
    private PieceType selectPawnTypeChange(){
        PieceType[] types = {PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};
        for(int i = 0; i < types.length; i++){
            System.out.println((i+1)+" - "+types[i]);
        }
        Scanner sc = new Scanner(System.in);
        int option = -1;
        while(option < 1 || option > types.length){
            System.out.println("Escolha uma opcao");
            option = sc.nextInt();
            if(option < 1 || option > types.length){
                System.out.println("Opcao invalida! Digite novamente");
            }
        }
        return types[option-1];

    }

    // faz jogada do usario
    // return true caso de certo
    // return false caos nao de certo
    private boolean userPlay(){
        // tentanto movimento do usuario
        if(BOARD_CONTROLLER.move(true, fromLineButton, fromColumnButton, toLineButton, toColumnButton)){
            System.out.println("Usuaruio conseguiu mover");
            // se o peao do jogador chegar no final do tabuleiro
            if(BOARD_CONTROLLER.isPawnChange() && BOARD_CONTROLLER.isUserTurn()){
                // atualizando tela com a movimentacao
                paintImmediately(getBounds());
                // selecionando tipo para troca do peao por nova peca
                PieceType type = selectPawnTypeChange();
                // trocando peao por nova peca
                BOARD_CONTROLLER.changePawnType(true, type);
            }
            verifyCheck();
            return true;
        }
        else{
            System.out.println("Usuario nao conseguiu mover");
            return false;
        }
    }

    // iniciando jogada o oponente
    // return true caso deu certo
    // return false caso nao de certo
    private boolean opponentPlay(){
        // tentanto movimento com AI
        if (AI_CONTROLLER.play()) {
            System.out.println("Oponente conseguiu mover");
            verifyCheck();
            return true;
        } else {
            System.out.println("Oponente nao conseguiu mover");
            return false;
        }
    }

    // verifica se algum rei esta em check
    private void verifyCheck(){
        // verificando se foi xeque no rei do usuario
        if(BOARD_CONTROLLER.checkOnKing(USER_COLOR)){
            System.out.println("Rei do usuario tomou xeque");
        }
        // verificando xeque no rei do oponente
        if (BOARD_CONTROLLER.checkOnKing(PieceColor.getOpponentOf(USER_COLOR))){
            System.out.println("Rei do oponente tomou xeque");
        }
    }

    // verifica se alguem ja venceu
    private void verifyWin(){
        // se o oponente venceu
        if(BOARD_CONTROLLER.isOpponentWon()){
            System.out.println("Oponente venceu");
        }
        // se o usuario venceu
        else if(BOARD_CONTROLLER.isUserWon()){
            System.out.println("Jogador venceu");
        }
    }
}
