package com.chess_AI.view.gameScreen;

import com.chess_AI.controller.AIController;
import com.chess_AI.controller.BoardController;
import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

/**
 * Esta classe representa o tabuleiro de xadrez visualmente, possui um PromoteSelector e um PositionSelector.
 * Caso alguem venca a partida mostra WonLabel.
 */
public class BoardPanel extends JPanel{

    /** Cor da peca do jogador*/
    private final PieceColor USER_COLOR;

    /** Controlador do tabuleiro para fazer as movimentacoes*/
    private final BoardController BOARD_CONTROLLER;

    /** Responsavel pela selecao de posicao no tabuleiro*/
    private final PositionSelector POSITION_SELECTOR;

    /** Responsavel pela selecao de peca quando o peao do usuario chega no final do tabuleiro*/
    private final PromoteSelector PROMOTE_SELECTOR;

    /** Janela em que esse objeto esta sendo mostrada, deve possuir o layout CardLayout*/
    private final JPanel WINDOW;

    /**
     * Cria e retorna objeto de BoardPanel. Quando criado, cria um tabuleiro com estado inicial para
     * o jogador e a IA jogarem.
     *
     * @param screenSize Tamanho do tabuleiro (screenSize x screenSize)
     * @param userColor Cor da peca do usuario
     * @param window Janela em que esse objeto vai ser mostrado, deve possuir o layout CardLayout.
     */
    public BoardPanel(int screenSize, PieceColor userColor, JPanel window) {
        this.USER_COLOR = userColor;
        this.WINDOW = window;

        // define a direcao do tabuleiro para ser mostrado
        if(USER_COLOR == PieceColor.WHITE) PieceColor.setIsWhiteUp(false);

        // cria boardController
        boolean isUserStarts = USER_COLOR == PieceColor.WHITE;
        BOARD_CONTROLLER = new BoardController(USER_COLOR, isUserStarts);

        // define o tamanho da tela
        Dimension size = new Dimension(screenSize, screenSize);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // insere position selector visivel para o jogador selecionar as posicoes
        POSITION_SELECTOR = new PositionSelector(screenSize, this);
        POSITION_SELECTOR.setVisible(true);
        add(POSITION_SELECTOR);

        // cria piece selector invisivel
        PROMOTE_SELECTOR = new PromoteSelector(screenSize, userColor, this);
        PROMOTE_SELECTOR.setVisible(false);
        add(PROMOTE_SELECTOR);

        // define funcao para iniciar o jogo quando esse painel ser visivel na tela
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if(e.getComponent() instanceof BoardPanel board){
                    History.printStart();
                    History.printColor(true, USER_COLOR);
                    History.printColor(false, PieceColor.getOpponentOf(USER_COLOR));

                    // se IA que comeca o jogo entao faz jogada da IA
                    if(!isUserStarts) board.makeAIPlay();
                }
            }
        });
    }

    /**
     * Realiza a jogada do usuario de acordo com um movimento.
     * Se um peao do usuario chegar no final do tabuleiro entao desabilita a selecao de posicao
     * e habilita a selecao de peca.
     * Se alguem vencer entao mostra wonLabel.
     *
     * @param move Movimento para aplicar
     * @return Se o jogador conseguir realizar a jogada, um peao nao chegou no final do tabuleiro
     * e alguem nao venceu entao retorna true.
     */
    public boolean makeUserPlay(Move move){
        History.printMove(move, true, BOARD_CONTROLLER.getTypeOf(move.FROM));

        if(BOARD_CONTROLLER.makeMove(true, move)){
            History.printIsCompletedMove(true, true);

            repaint();

            // se alguem venceu entao mostra wonLabel
            boolean isAnyoneWon = BOARD_CONTROLLER.isUserWon() || BOARD_CONTROLLER.isAIWon();
            if(isAnyoneWon){
                showWonLabel();
                return false;
            }

            // aparece tela para selecao de peca caso algum peao chegou no final
            // do tabuleiro
            if(BOARD_CONTROLLER.verifyPawnPromote()){
                showPromoteSelector();
                return false;
            }

            verifyCheck();

            return true;
        }
        else {
            History.printIsCompletedMove(true, false);
            return false;
        }
    }

    /**
     * Mostra o seletor de peca no painel para selecionar uma peca quando um peao do usuario
     * chegou no final do tabuleiro.
     * Desabilita position selector e habilita promote selector.
     */
    private void showPromoteSelector(){
        POSITION_SELECTOR.setVisible(false);
        PROMOTE_SELECTOR.setVisible(true);
        repaint();
    }

    /**
     * Realiza a jogada da IA.
     * Enquanto a IA esta predizendo a jogando, o seletor de posicao fica desabilitado.
     * Se alguem vencer entao mostra WonLabel.
     */
    public void makeAIPlay(){
        POSITION_SELECTOR.setVisible(false);
        paintImmediately(getBounds());

        // pega predicao da melhor jogada de acordo com a IA
        PieceColor AI_COLOR = PieceColor.getOpponentOf(USER_COLOR);
        long start = System.currentTimeMillis() / 1000; // tempo inicial
        Move move = AIController.predict(AI_COLOR, BOARD_CONTROLLER, 3);
        long end = System.currentTimeMillis() / 1000; // tempo final

        POSITION_SELECTOR.setVisible(true);

        // faz movimento
        if(move != null) {

            History.printMove(move, false, BOARD_CONTROLLER.getTypeOf(move.FROM));

            if (BOARD_CONTROLLER.makeMove(false, move)) {
                History.printIsCompletedMove(false, true);
                History.printTime(false, start, end);

                if(BOARD_CONTROLLER.verifyPawnPromote()){
                    BOARD_CONTROLLER.makePawnPromote(false, PieceType.QUEEN);
                    History.printPawnPromote(false, PieceType.QUEEN);
                }

                verifyCheck();

                boolean isAnyoneWon = BOARD_CONTROLLER.isUserWon() || BOARD_CONTROLLER.isAIWon();
                if (isAnyoneWon) showWonLabel();
            } else {
                History.printIsCompletedMove(false, false);
            }
        }
        else History.printAIError();

        repaint();
    }

    /**
     * Realiza a troca do peao.
     * Usar quando um peao chegar no final do tabuleiro.
     * Caso de certo, desabilita panel de peca e habilita a selecao de posicao.
     *
     * @param newType Novo tipo de peca no lugar do peao.
     */
    public void makePawnChange(PieceType newType){
        if(BOARD_CONTROLLER.makePawnPromote(true, newType)){
            History.printPawnPromote(true, newType);

            verifyCheck();

            PROMOTE_SELECTOR.setVisible(false);
            POSITION_SELECTOR.setVisible(true);

            repaint();
        }
    }

    /**
     * Mostra wonLabel. Usar quado alguem vencer.
     * Desabilita position selector.
     */
    private void showWonLabel(){
        POSITION_SELECTOR.setVisible(false);

        History.printWon(BOARD_CONTROLLER.isUserWon());

        WonLabel winLabel = new WonLabel(BOARD_CONTROLLER.isUserWon(), USER_COLOR, WINDOW, this);
        winLabel.setVisible(true);
        add(winLabel);

        repaint();
    }

    /**
     * Verifica e mostra no terminal se algum rei esta em check.
     */
    private void verifyCheck(){
        if(BOARD_CONTROLLER.verifyCheckOnKing(USER_COLOR)) History.printCheck(false);

        if(BOARD_CONTROLLER.verifyCheckOnKing(PieceColor.getOpponentOf(USER_COLOR))){
            History.printCheck(true);
        }
    }

    /**
     * Desenha o fundo do tabuleiro.
     *
     * @param g2 Contexto para desenhar o fundo
     */
    private void drawBoard(Graphics2D g2){
        // define tamanho das imagens
        int width = getWidth() / 8;
        int height = getHeight() / 8;

        BufferedImage[][] background = ImageController.loadBackground();
        for(int i = 0; i < background.length; i++){
            for(int j = 0; j < background.length; j++){
                g2.drawImage(background[i][j], j * width, i * height, width, height, null);
            }
        }
    }

    /**
     * Desenha as pecas no tabuleiro de acordo com BOARD_CONTROLLER.
     *
     * @param g2 Contexto para desenhar
     */
    private void drawPieces(Graphics2D g2){
        // define tamanho das pecas
        int width = getWidth() / 8;
        int height = getHeight() / 8;

        // percorre posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Position position = new Position(i, j);
                PieceType type = BOARD_CONTROLLER.getTypeOf(position);
                PieceColor color = BOARD_CONTROLLER.getColorOf(position);

                BufferedImage image = ImageController.loadPiece(type, color);
                if(image != null) {
                    g2.drawImage(image, j * width, i * height, width, height, null);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // desenha fundo
        drawBoard(g2);
        // desenha pecas
        drawPieces(g2);
    }
}
