package com.Kayo.view;

import com.Kayo.controller.AIController;
import com.Kayo.controller.BoardController;
import com.Kayo.controller.ImageController;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class BoardPanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    private final int ORIGINAL_IMAGE_SIZE = 128; // 16x16 px
    private final float SCALE = 1/1.5f; // aumenta o tamanho da imagem n vezes

    private final int IMAGE_SIZE = Math.round(ORIGINAL_IMAGE_SIZE * SCALE); // tamanho da imagem que vai ser desenhada na tela
    private final int MAX_SCREEN_COL = 8;
    private final int MAX_SCREEN_ROW = 8;
    private final int SCREEN_WIDTH = IMAGE_SIZE * MAX_SCREEN_COL; // largura da tela
    private final int SCREEN_HEIGHT = IMAGE_SIZE * MAX_SCREEN_ROW; // altura da tela

    Thread GAME_THREAD;

    private final PieceColor USER_COLOR;
    private final PieceColor OPPONENT_COLOR;

    // controladores
    private final BoardController BOARD_CONTROLLER;
    private final AIController AI_CONTROLLER;

    // posicao dos botoes selecionados
    private int fromLineButton = 0;
    private int fromColumnButton = 0;
    private int toLineButton = 0;
    private int toColumnButton = 0;
    private int select = 0;

    public BoardPanel(PieceColor userColor) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.USER_COLOR = userColor;
        // seleciona cor da peca oponente de acordo com a peca do usuario
        if(userColor == PieceColor.WHITE){
            OPPONENT_COLOR = PieceColor.BLACK;
        }
        else{
            OPPONENT_COLOR = PieceColor.WHITE;
        }

        BOARD_CONTROLLER = new BoardController(true, userColor);
        AI_CONTROLLER = new AIController(OPPONENT_COLOR, BOARD_CONTROLLER);
    }

    public void startGameThread() {
        GAME_THREAD = new Thread(this);
        GAME_THREAD.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addPiecesButtons();
    }

    // chamado pelo metodo repaint()
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // desenhando fundo
        drawBackGround(g2);
        // desenhando pecas
        drawPieces(g2);

        //g2.dispose();
    }

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

    private void addPiecesButtons(){
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

    public void selectPosition(int line, int column){
        // se botoes selecionados for 0
        if(select == 0){
            // inserindo posicao do primeiro botao
            fromLineButton = line;
            fromColumnButton = column;
        }
        // se botoes selecionas for 1
        else if (select == 1){
            // inserindo posicao do segundo botao
            toLineButton = line;
            toColumnButton = column;
        }
        // atualizando contagem de botoes selecionados
        select++;

        // se botoes selecionados for 2
        if(select == 2){
            System.out.println("Usuario tentanto mover:");
            System.out.println("From line: "+ fromLineButton +" Column: "+ fromColumnButton +" | To line: "+ toLineButton +" Column: "+ toColumnButton);
            play();
            select = 0;
        }
    }

    private void play(){
        // jogador tenta fazer jogada
        userPlay();

        // removendo todos os componentes
        removeAll();
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

    private void verifyCheck(){
        // verificando se foi xeque no rei do usuario
        if(BOARD_CONTROLLER.check(USER_COLOR)){
            System.out.println("Rei do usuario tomou xeque");
        }
        // verificando xeque no rei do oponente
        if (BOARD_CONTROLLER.check(OPPONENT_COLOR)){
            System.out.println("Rei do oponente tomou xeque");
        }
    }

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
