package com.chess_AI.view.gameScreen;

import com.chess_AI.controller.AIController;
import com.chess_AI.controller.BoardController;
import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * Esta classe representa o tabuleiro de xadrez visualmente
 *
 */
public class BoardPanel extends JComponent{
    /** Tamanho das imagens (IMAGE_SIZE X IMAGE_SIZE)*/
    private final int IMAGE_SIZE;

    /** Cor da peca do jogador*/
    private final PieceColor USER_COLOR;

    /** Controlador do tabuleiro que vai ser mostrado*/
    private final BoardController BOARD_CONTROLLER;

    /** Controlador da inteligencia artificial que o jogador vai jogar contra*/
    private final AIController AI_CONTROLLER;


    /** Posicao da linha selecionada de origem*/
    private int fromLineButton = -1;

    /** Posicao da coluna selecionada de origem*/
    private int fromColumnButton = -1;

    /** Posicao da linha selecionada de destino*/
    private int toLineButton = -1;

    /** Posicao da coluna selecionada de destino*/
    private int toColumnButton = -1;


    /**
     * Cria objeto de BoardPanel com tamanho de tela e cor da peca do jogador definidos.
     *
     * @param screenSize Tamanho do tabuleiro (screenSize x screenSize)
     * @param userColor Cor da peca do usuario
     */
    public BoardPanel(int screenSize, PieceColor userColor) {
        // define tamanho das imagens
        IMAGE_SIZE = screenSize/8;

        // redimenciona tela
        this.setPreferredSize(new Dimension(screenSize, screenSize));
        //this.setFocusable(true);

        // define cor de fundo da tela
        this.setBackground(Color.BLACK);

        // define quem vai comecar o jogo
        this.USER_COLOR = userColor;
        boolean isUserStart = false;
        if(USER_COLOR == PieceColor.WHITE) isUserStart = true;

        // cria objeto de BoardController
        BOARD_CONTROLLER = new BoardController(isUserStart, userColor);

        // cria objeto de AIController
        AI_CONTROLLER = new AIController(PieceColor.getOpponentOf(USER_COLOR), BOARD_CONTROLLER);
    }


    /**
     * Habilita o jogador selecionar as pecas para movimentar e faz IA jogar caso ela que comece o jogo.
     */
    public void start(){
        // adiciona os botoes para selecionar as pecas
        addPiecesButtons();

        // se o jogador nao comecar
        if(!BOARD_CONTROLLER.isUserTurn()){
            // IA faz jogada
            AI_CONTROLLER.play();
        }

        // atualiza tela
        repaint();
    }


    /**
     * Atualiza a primeira e a segunda posicao selecionada pelo usuario, sendo a primeira chamada para
     * a primeira posicao, segunda chamada para a segunda posicao. Apos isso reseta as posicoes selecionadas
     * e a IA faz jogada.
     *
     * @param line Posicao da linha
     * @param column Posicao da coluna
     * @return True caso de certo a selecao, caso contrario false
     */
    public boolean selectPosition(int line, int column){
        if(BOARD_CONTROLLER.isUserTurn()) {
            // se nenhum botao selecionado ainda
            if (fromLineButton < 0 || fromColumnButton < 0) {
                // seleciona primeira posicao
                fromLineButton = line;
                fromColumnButton = column;
            }
            // se primeira posicao ja foi selecionada
            else {
                // seleciona segunda posicao
                toLineButton = line;
                toColumnButton = column;

                // faz jogada da jogador e da IA
                play();

                // reseta posicoes selecionadas
                fromLineButton = -1;
                fromColumnButton = -1;
                toLineButton = -1;
                toColumnButton = -1;
            }
            return true;
        }
        return false;
    }


    /**
     * Faz jogado do jogador de acordo com as posicoes selecionadas. Apos isso realiza
     * a jogada a IA. Conforme as jogadas sao feitas vai atualizando a tela.
     */
    private void play(){
        System.out.println("Usuario tentanto mover:");
        System.out.println("From line: " + fromLineButton + " Column: " + fromColumnButton + " | To line: " + toLineButton + " Column: " + toColumnButton);
        // jogador tenta fazer jogada
        userPlay();

        // atualiza botoes
        addPiecesButtons();

        // atualiza tela imediatamente
        paintImmediately(getBounds());

        // AI tenta fazer jogada
        AIPlay();

        // atualiza tela
        repaint();

        // verifica vitoria
        verifyWin();
    }


    /**
     * Faz jogada do jogador de acordo com as posicoes selecionadas
     *
     * @return Se deu certo a jogada
     */
    private boolean userPlay(){
        // faz jogada do usuario e caso de certo
        if(BOARD_CONTROLLER.move(true, fromLineButton, fromColumnButton, toLineButton, toColumnButton)){

            System.out.println("Usuaruio conseguiu mover");

            // se o peao do jogador chegou no final do tabuleiro
            if(BOARD_CONTROLLER.hasPawnOnFinal() && BOARD_CONTROLLER.isUserTurn()){

                // atualiza tela imediatamente
                paintImmediately(getBounds());

                // seleciona tipo para troca do peao por nova peca
                PieceType type = selectPieceType();

                // troca peao por nova peca
                BOARD_CONTROLLER.changePawnType(true, type);
            }

            // verifica check
            verifyCheck();

            return true;
        }
        // nao deu certo movimentar
        else{
            System.out.println("Usuario nao conseguiu mover");
            return false;
        }
    }


    /**
     * Pede ao usuario selecionar um tipo de peca e retorna ela.
     *
     * @return Tipo da peca selecionada
     */
    private PieceType selectPieceType(){
        // opcoes de pecas
        PieceType[] types = {PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};

        // mostra no terminal as opcoes
        for(int i = 0; i < types.length; i++){
            System.out.println("["+(i)+"] - "+types[i]);
        }

        // escolhe opcao de peca
        Scanner sc = new Scanner(System.in);
        int option = -1;
        while(option < 0 || option >= types.length){
            System.out.println("Escolha uma opcao");
            option = sc.nextInt();
            if(option < 0 || option >= types.length){
                System.out.println("Opcao invalida! Digite novamente");
            }
        }

        // retorna peca selecionada
        return types[option];

    }


    /**
     * Faz jogada da IA.
     *
     * @return Se deu certo a jogada
     */
    private boolean AIPlay(){
        // tentanto movimento com AI
        if (AI_CONTROLLER.play()) {
            System.out.println("IA conseguiu mover");
            verifyCheck();
            return true;
        } else {
            System.out.println("IA nao conseguiu mover");
            return false;
        }
    }


    /**
     * Verifica check e mostra no terminal qual rei esta em check.
     */
    private void verifyCheck(){
        // verificando se foi xeque no rei do usuario
        if(BOARD_CONTROLLER.verifyCheckOnKing(USER_COLOR)){
            System.out.println("Rei do usuario tomou xeque");
        }
        // verificando xeque no rei da IA
        if (BOARD_CONTROLLER.verifyCheckOnKing(PieceColor.getOpponentOf(USER_COLOR))){
            System.out.println("Rei da IA tomou xeque");
        }
    }


    /**
     * Verifica vitoria e mostra no terminal quem venceu
     */
    private void verifyWin(){
        // se a IA venceu
        if(BOARD_CONTROLLER.isAIWon()){
            System.out.println("IA venceu");
        }
        // se o usuario venceu
        else if(BOARD_CONTROLLER.isUserWon()){
            System.out.println("Jogador venceu");
        }
    }


    /**
     * Desenha o fundo do tabuleiro.
     *
     * @param g2 Objeto de pintura
     */
    private void drawBackGround(Graphics2D g2){
        // carrega imagens de fundo
        BufferedImage[][] background = ImageController.loadBackground();

        // percorre imagens de fundo
        for(int i = 0; i < background.length; i++){
            for(int j = 0; j < background.length; j++){
                // desenha imagem na respectiva posicao
                g2.drawImage(background[i][j], j * IMAGE_SIZE, i * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, null);
            }
        }
    }


    /**
     * Desenha as pecas no tabuleiro de acordo com BOARD_CONTROLLER.
     *
     * @param g2 Objeto de desenho
     */
    private void drawPieces(Graphics2D g2){
        // percorre posicoes de pecas
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                // pega tipo da peca na respectiva posicao
                PieceType type = BOARD_CONTROLLER.getTypeOf(i, j);

                // pega cor da peca na respectiva posicao
                PieceColor color = BOARD_CONTROLLER.getColorOf(i, j);

                // carrega imagem da respectiva peca e cor
                BufferedImage image = ImageController.loadPiece(type, color);

                // se existir imagem
                if(image != null) {
                    // desenha imagem da peca na respectiva posicao na tela
                    g2.drawImage(image, j * IMAGE_SIZE, i * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, null);
                }
            }
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // desenha fundo
        drawBackGround(g2);
        // desenha pecas
        drawPieces(g2);

        g2.dispose();
    }


    /**
     * Remove todos os componentes do tabuleiro e adiciona os botoes para selecao de peca na tela.
     */
    private void addPiecesButtons(){
        // remove todos os componentes
        removeAll();

        // percorre posicoes do tabuleiro
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();

                // configura para o botao ser invisivel
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setRolloverEnabled(false); // efeito de quando coloca o mouse em cima do botao sendo desabilitado
                button.setPressedIcon(null); // remove o icone exibido quando o botao eh selecionado
                button.setDisabledIcon(null); // remove o icone exibido quando o botao esta desativado
                button.setFocusPainted(false); // desabilita o destaque visual quando o botao esta em foco

                // insere funcao de selecionar posicao ao botao
                int line = i;
                int column = j;
                button.addActionListener(new ActionListener() {
                    final int LINE = line;
                    final int COLUMN = column;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // se conseguir atualizar a posicao selecionada
                        if(selectPosition(LINE, COLUMN)) {
                            // muda cor do botao
                            button.setContentAreaFilled(true);
                            button.setBackground(new Color(255, 0, 0, 166));
                        }
                    }
                });

                // redimensiona e insere botao na tela
                button.setBounds(column * IMAGE_SIZE, line * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);

                // adiciona botao na tela
                add(button);
            }
        }
    }
}
