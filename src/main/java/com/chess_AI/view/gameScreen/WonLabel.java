package com.chess_AI.view.gameScreen;

import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.view.initialScreen.InitialScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Representa a tela de vitoria do jogador ou da IA. No centro dessa tela estao os componentes.
 */
public class WonLabel extends JLabel{

    /** Panel onde BoardPanel esta incluso*/
    private final JPanel window;

    /** BoardPanel onde esse objeto vai ser incluso*/
    private final BoardPanel BOARD_PANEL;

    /**
     * Cria objeto de WinLabel.
     *
     * @param isUserWon Se o jogador venceu
     * @param userColor Cor do jogador
     * @param window Janela em que boardPamel esta, deve ter layout CardLayout
     * @param boardPanel Janela em que WinLabel vai ser incluso
     */
    public WonLabel(boolean isUserWon, PieceColor userColor, JPanel window, BoardPanel boardPanel){
        //SCREEN_SIZE = screenSize;
        this.window = window;
        BOARD_PANEL = boardPanel;

        // define tamanho de panel
        Dimension size = new Dimension(boardPanel.getWidth(), boardPanel.getHeight());
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        // adiciona os componentes no centro da tela
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        JLabel winLabel = createLabel(size.width/8 * 5, size.height/8 * 4, isUserWon, userColor);
        winLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(winLabel);
        add(Box.createVerticalGlue());
    }

    /**
     * Cria e retorna Label com a imagem de uma peca do rei, texto de vitoria e o botao para retornar a tela inicial.
     *
     * @param width Tamanho do Label
     * @param height Altura do Label
     * @param isUserWon Se o jogador venceu
     * @param userColor Cor do jogador
     * @return Label com os componentes inclusos.
     */
    private JLabel createLabel(int width, int height, boolean isUserWon, PieceColor userColor){
        JLabel win = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // adiciona cor de fundo
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // define tamanho de win
        Dimension size = new Dimension(width, height);
        win.setPreferredSize(size);
        win.setMinimumSize(size);
        win.setMaximumSize(size);


        // adiciona os componentes em win
        win.setLayout(new BoxLayout(win, BoxLayout.Y_AXIS));

        win.add(Box.createVerticalGlue());

        JLabel image = createKingLabel(Math.round(width * 0.30f), isUserWon, userColor);
        image.setAlignmentX(CENTER_ALIGNMENT);
        win.add(image);

        win.add(Box.createVerticalGlue());

        JLabel text = createWonText(isUserWon, Math.round(height * 0.10f));
        text.setAlignmentX(CENTER_ALIGNMENT);
        win.add(text);

        win.add(Box.createVerticalGlue());

        JButton returnButton = createReturnButton(Math.round(width * 0.60f), Math.round(height * 0.30f));
        returnButton.setAlignmentX(CENTER_ALIGNMENT);
        win.add(returnButton);

        win.add(Box.createVerticalGlue());

        return win;
    }

    /**
     * Cria e retorna uma label com a imagem do rei dentro.
     * A cor do rei depende de quem venceu a partida.
     *
     * @param imageSize Tamanho da imagem/label
     * @param isUserWon Se o jogador venceu
     * @param userColor Cor do usuario
     * @return Label com a imagem da peca do rei
     */
    private JLabel createKingLabel(int imageSize, boolean isUserWon, PieceColor userColor){
        JLabel imageLabel = new JLabel(); // label que vai ser inserida a imagem

        // define tamanho de label
        Dimension size = new Dimension(imageSize, imageSize);
        imageLabel.setPreferredSize(size);
        imageLabel.setMinimumSize(size);
        imageLabel.setMaximumSize(size);

        // carrega imagem
        BufferedImage image;
        if(isUserWon) image = ImageController.loadPiece(PieceType.KING, userColor);
        else image = ImageController.loadPiece(PieceType.KING, PieceColor.getOpponentOf(userColor));

        // insere imagem
        Image scaledImage = image.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        imageLabel.setIcon(icon);

        return imageLabel;
    }

    /**
     * Cria e retorna o texto de vitoria dependendo de quem venceu.
     *
     * @param isUserWon Se o jogador venceu
     * @param fontSize Tamanho da fonte do texto
     * @return Label com o texto de vitoria.
     */
    private JLabel createWonText(boolean isUserWon, int fontSize){
        JLabel text = new JLabel();

        // define texto de vitoria
        if(isUserWon) text.setText("USER WON");
        else text.setText("AI WON");

        // define a fonte do texto
        Font font = new Font(text.getFont().getName(), Font.BOLD, fontSize);
        text.setFont(font);

        return text;
    }

    /**
     * Cria e retorna o botao com funcao para retornar a tela inicial.
     *
     * @param width Largura do botao
     * @param height Altura do botao
     * @return O botao
     */
    private JButton createReturnButton(int width, int height){
        JButton returnButton = new JButton("RETURN");

        // define o tamanho do botao
        Dimension size = new Dimension(width , height);
        returnButton.setPreferredSize(size);
        returnButton.setMaximumSize(size);
        returnButton.setMaximumSize(size);

        // define a fonte do botao
        Font font = new Font(returnButton.getFont().getName(), Font.BOLD, Math.round(size.height * 0.40f));
        returnButton.setFont(font);

        // define as cores do botao
        returnButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, size.width/64));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(Color.GREEN);

        returnButton.setFocusPainted(false); // para nao aparecer o texto dentro do botao selecionado

        // adiciona funcao para mudar as cores do botao dependendo da acao do mouse do usuario
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                returnButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, size.width/64));
                returnButton.setForeground(Color.GREEN);
                returnButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, size.width/64));
                returnButton.setForeground(Color.WHITE);
                returnButton.setBackground(Color.GREEN);
            }
        });

        // adiciona funcao para retornar a tela inicial
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) window.getLayout();
                layout.removeLayoutComponent(BOARD_PANEL);
                window.remove(BOARD_PANEL);

                InitialScreen initialScreen = new InitialScreen(BOARD_PANEL.getWidth(), window);
                window.add(initialScreen, "Initial screen");

                layout.show(window, "Initial screen");
            }
        });

        return returnButton;
    }
}
