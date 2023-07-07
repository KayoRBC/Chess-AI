package com.chess_AI.view.initialScreen;

import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.view.gameScreen.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Esta classe representa a tela inicial, onde o jogador seleciona a cor da peca e inicia o jogo.
 */
public class InitialScreen extends JPanel {

    /** Largura da janela*/
    private final int SCREEN_WIDTH;

    /** Altura da janela*/
    private final int SCREEN_HEIGHT;

    /** Painel que armazena essa classe*/
    private final JPanel FATHER_SCREEN;

    /** Layout do componente pai*/
    private final CardLayout FATHER_LAYOUT;

    /** Cor selecionada pelo usuario*/
    private PieceColor selectedColor = null;

    /**
     * Cria e retorna objeto de InitialScreen.
     *
     * @param screenWidth Largura da tela
     * @param screenHeight Altura da tela
     * @param fatherScreen Container pai
     * @param fatherLayout Layout do pai
     */
    public InitialScreen(int screenWidth, int screenHeight, JPanel fatherScreen, CardLayout fatherLayout){
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        FATHER_SCREEN = fatherScreen;
        FATHER_LAYOUT = fatherLayout;

        // define configuracoes do painel
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.gray);

        // adiciona componentes no painel
        addTitle();
        add(Box.createRigidArea(new Dimension(0, SCREEN_HEIGHT / 16)));
        addColorSelector();
        add(Box.createRigidArea(new Dimension(0, SCREEN_HEIGHT / 8)));
        JLabel errorText = createErrorText();
        add(errorText);
        add(Box.createRigidArea(new Dimension(0, SCREEN_HEIGHT / 32)));
        addStartButton(errorText);
        add(Box.createVerticalGlue());
        addAuthor();
    }

    /**
     * Adiciona o titulo do jogo no painel.
     */
    private void addTitle(){
        JLabel gameTitle = new JLabel("Chess-AI");
        Font font = new Font(gameTitle.getFont().getName(), Font.BOLD, Math.round(SCREEN_HEIGHT / 8.0f * 0.90f));
        gameTitle.setFont(font);

        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(gameTitle);
    }

    /**
     * Adiciona o seletor de cor de peca no painel.
     */
    private void addColorSelector(){
        // adiciona um titulo em cima de color selector
        JLabel text = new JLabel("CHOOSE YOUR PIECE COLOR");
        Font font = new Font(text.getFont().getName(), Font.BOLD, Math.round(SCREEN_HEIGHT / 8.0f * 0.30f));
        text.setFont(font);
        text.setAlignmentX(CENTER_ALIGNMENT);
        add(text);

        JPanel colorSelector = new JPanel();
        colorSelector.setLayout(new BoxLayout(colorSelector, BoxLayout.X_AXIS));
        colorSelector.setBackground(getBackground());

        // define tamanho do color selector
        int panelWidth = SCREEN_WIDTH / 2;
        int panelHeight = Math.round(SCREEN_HEIGHT / 8.0f * 1.5f);
        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        colorSelector.setPreferredSize(panelSize);
        colorSelector.setMaximumSize(panelSize);
        colorSelector.setMaximumSize(panelSize);

        // cria botoes para inserir em colorSelector
        Dimension buttonSize = new Dimension(panelHeight, panelHeight);
        PieceType type = PieceType.getRand();
        while(type == PieceType.NULL) type = PieceType.getRand();
        JButton white = createColorButton(buttonSize, type, PieceColor.WHITE);
        JButton black = createColorButton(buttonSize, type, PieceColor.BLACK);

        // adiciona funcao para mudar o fundo dos botoes
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                white.setContentAreaFilled(true);
                black.setContentAreaFilled(false);
                selectedColor = PieceColor.WHITE;
            }
        });
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                black.setContentAreaFilled(true);
                white.setContentAreaFilled(false);
                selectedColor = PieceColor.BLACK;
            }
        });

        colorSelector.add(white);
        colorSelector.add(Box.createHorizontalGlue());
        colorSelector.add(black);

        colorSelector.setAlignmentX(CENTER_ALIGNMENT);
        add(colorSelector);
    }

    /**
     * Cria e retorna um botao com a imagem de uma peca e sem nenhum action listener.
     * O fundo nao esta desenhado e esta definido para ser vermelho.
     *
     * @param size Tamanho do botao
     * @param type Tipo da peca
     * @param color Cor da peca
     * @return O botao.
     */
    private JButton createColorButton(Dimension size, PieceType type, PieceColor color){
        JButton button = new JButton();

        // define tamanho de botao
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMaximumSize(size);

        // define cor do botao
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setBackground(new Color(255, 0, 0, 255));

        // carrega e coloca imagem da cor de peca no botao
        BufferedImage image = ImageController.loadPiece(type, color);
        Image scaledImage = image.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        button.setIcon(icon);

        return button;
    }

    /**
     * Cria e retorna label para o texto de erro. Apenas com cor e fonte para o texto definidos.
     *
     * @return Label para o texto de erro.
     */
    private JLabel createErrorText(){
        JLabel error = new JLabel(" ");

        // define fonte para o texto de erro
        Font font = new Font(error.getFont().getName(), Font.BOLD, Math.round(SCREEN_HEIGHT / 8.0f * 0.20f));
        error.setFont(font);

        error.setForeground(Color.red);

        error.setAlignmentX(CENTER_ALIGNMENT);

        return error;
    }

    /**
     * Adiciona botao para comecar o jogo no painel.
     *
     * @param errorText Label para aparecer o texto de erro caso nenhuma cor tenha selecionada ainda.
     */
    private void addStartButton(JLabel errorText){
        JButton start = new JButton("START");

        // define o tamanho do botao
        Dimension size = new Dimension(SCREEN_WIDTH / 8 * 3 , SCREEN_WIDTH / 8);
        start.setPreferredSize(size);
        start.setMaximumSize(size);
        start.setMaximumSize(size);

        // define a fonte do botao
        Font font = new Font(start.getFont().getName(), Font.BOLD, Math.round(size.height * 0.40f));
        start.setFont(font);

        // define as cores do botao
        start.setBorder(BorderFactory.createLineBorder(Color.WHITE, size.width/64));
        start.setForeground(Color.WHITE);
        start.setBackground(Color.GREEN);

        // adiciona funcao para mudar as cores do botao dependendo da acao do mouse do usuario
        start.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                start.setBorder(BorderFactory.createLineBorder(Color.GREEN, size.width/64));
                start.setForeground(Color.GREEN);
                start.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                start.setBorder(BorderFactory.createLineBorder(Color.WHITE, size.width/64));
                start.setForeground(Color.WHITE);
                start.setBackground(Color.GREEN);
            }
        });

        // adiciona funcao para mudar para a pagina do tabuleiro de xadrez
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedColor != null) {
                    errorText.setText(" ");
                    FATHER_SCREEN.add(new BoardPanel(SCREEN_WIDTH, selectedColor), "Chess");
                    FATHER_LAYOUT.show(FATHER_SCREEN, "Chess");
                }
                else{
                    errorText.setText("SELECIONE UMA COR ANTES DE COMEÇAR!");
                }
            }
        });

        start.setAlignmentX(CENTER_ALIGNMENT);
        add(start);
    }

    /**
     * Adiciona o nome do autor do codigo no painel.
     */
    private void addAuthor(){
        JLabel text = new JLabel("Author: KayoRBC");

        // define a fonte do texto
        Font font = new Font(text.getFont().getName(), Font.BOLD, Math.round(SCREEN_HEIGHT / 8.0f * 0.19f));
        text.setFont(font);

        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(text);
    }
}
