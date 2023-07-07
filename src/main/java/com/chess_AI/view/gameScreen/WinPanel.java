package com.chess_AI.view.gameScreen;

import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Esta classe representa um painel para dizer quem venceu o jogo.
 */
public class WinPanel extends JComponent {

    private final int IMAGE_SIZE;

    private final boolean IS_USER_WON;

    private final PieceColor USER_COLOR;


    public WinPanel(boolean isUserWon, PieceColor userColor, int imageSize){
        IS_USER_WON = isUserWon;
        USER_COLOR = userColor;
        IMAGE_SIZE = imageSize;

        setPreferredSize(new Dimension(2 * IMAGE_SIZE, IMAGE_SIZE));

        setLayout(new GridLayout());

        for (int i = 0; i < 2; i++) {
            JButton button = new JButton("Button " + i);
            add(button);
        }


        /*
        addWinText();

        addKingImage();
         */
        setBackground(Color.gray);
    }

    private void addWinText(){
        JLabel label = new JLabel();
        label.setSize(getWidth(), IMAGE_SIZE);

        if(IS_USER_WON) {
            label.setText("Você venceu!");
            label.setForeground(Color.GREEN);
        }
        else{
            label.setText("IA venceu!");
            label.setForeground(Color.RED);
        }

        //label.setHorizontalAlignment(SwingConstants.CENTER);
        //label.setVerticalAlignment(SwingConstants.CENTER);

        Font font = new Font(label.getFont().getName(), Font.BOLD, Math.round(IMAGE_SIZE * 0.20f));
        label.setFont(font);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0; // coluna 0
        constraints.gridy = 1; // linha 1
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        add(label);
    }

    /**
     * Desenha a imagem da peca do rei na primeira linha | terceira coluna do painel.
     *
     * @param g2 Contexto para desenhar a imagem
     */
    private void addKingImage(){
        BufferedImage piece;
        if(IS_USER_WON) piece = ImageController.loadPiece(PieceType.KING, USER_COLOR);
        else piece = ImageController.loadPiece(PieceType.KING, PieceColor.getOpponentOf(USER_COLOR));

        JLabel label = new JLabel();
        label.setSize(IMAGE_SIZE, IMAGE_SIZE);

        ImageIcon imageIcon = new ImageIcon(piece);
        label.setIcon(imageIcon);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0; // coluna 0
        constraints.gridy = 0; // linha 1
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        add(label);

        /*
        // posiciona imagem na primeira linha | terceira coluna
        final int X = 2 * IMAGE_SIZE;
        final int Y = 0;

        g2.drawImage(piece, X, Y, IMAGE_SIZE, IMAGE_SIZE, null);

         */
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
