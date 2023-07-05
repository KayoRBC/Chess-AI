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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

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

        setVisible(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("Comecando jogo!");
                if(e.getComponent() instanceof BoardPanel board){
                    board.start();
                }
            }
        });
        setVisible(true);
    }


    /**
     * Retorna o boardController utilizado.
     *
     * @return O boardController utilizado
     */
    public BoardController getBoardController() {
        return BOARD_CONTROLLER;
    }


    /**
     * Verifica check e mostra no terminal qual rei esta em check.
     */
    public void verifyCheck(){
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
     * Atualiza a primeira e a segunda posicao selecionada pelo usuario, sendo a primeira chamada para
     * a primeira posicao, segunda chamada para a segunda posicao. Apos isso reseta as posicoes selecionadas
     * e chama o metodo userPlay().
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
                userPlay();

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
     * Faz jogada da IA e chama o metodo addPiecesButtons().
     * Alem disso, atualiza a tela, remove todos os componentes anteriores
     * e mostra check/vitoria no terminal.
     */
    public void AIPlay(){
        if (AI_CONTROLLER.play()) {
            System.out.println("IA conseguiu mover");
        }
        else System.out.println("IA nao conseguiu mover");

        boolean isAnyoneWon = BOARD_CONTROLLER.isUserWon() || BOARD_CONTROLLER.isAIWon();

        removeAll();

        if(isAnyoneWon) verifyWin();
        else{
            verifyCheck();
            addPiecesButtons();
        }

        paintImmediately(getBounds());
    }


    /**
     * Faz jogada do jogador de acordo com as posicoes selecionadas e logo em seguida chama o metodo AIPlay().
     * Se algum peao chegou no final apos a movimentacao do jogador entao remove todos os componentes anteriores,
     * cria objeto SelectPieceType e nao chama o metodo AIPlay().
     * Alem disso, atualiza a tela e mostra check/vitoria no terminal.
     */
    private void userPlay(){
        // faz jogada do usuario
        if(BOARD_CONTROLLER.move(true, fromLineButton, fromColumnButton, toLineButton, toColumnButton)){
            System.out.println("Usuaruio conseguiu mover");

            boolean isAnyoneWon = BOARD_CONTROLLER.isUserWon() || BOARD_CONTROLLER.isAIWon();

            if(BOARD_CONTROLLER.hasPawnOnFinal() && !isAnyoneWon){
                removeAll(); // remove todos os componentes da tela (botoes)

                // opcoes de pecas
                PieceType[] types = {PieceType.KNIGHT, PieceType.BISHOP, PieceType.ROOK, PieceType.QUEEN};

                SelectPieceType selectPieceType = new SelectPieceType(IMAGE_SIZE, USER_COLOR, types, this);
                final int X = (getWidth() - selectPieceType.getWidth()) / 2;
                final int Y = (getHeight() - selectPieceType.getHeight()) / 2;
                selectPieceType.setBounds(X, Y, selectPieceType.getWidth(), selectPieceType.getHeight());

                add(selectPieceType);

                paintImmediately(getBounds());
            }
            else{
                paintImmediately(getBounds());
                verifyCheck();
                AIPlay();
            }
        }
        else {
            System.out.println("Usuaruio nao conseguiu mover");
            AIPlay();
        }

    }

    /**
     * Funcao para comecar o jogo.
     * Habilita o jogador selecionar as pecas para movimentar e faz IA jogar caso ela que comece o jogo.
     */
    private void start(){
        paintImmediately(getBounds());

        if(BOARD_CONTROLLER.isUserTurn()){
            // adiciona os botoes para selecionar as pecas
            removeAll();
            addPiecesButtons();
        }
        else AIPlay();
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
     * Adiciona os botoes para selecao de peca na tela. Cuidado para antes de adicionar remover os anteriores.
     */
    private void addPiecesButtons(){
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

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // desenha fundo
        drawBackGround(g2);
        // desenha pecas
        drawPieces(g2);

    }
}
