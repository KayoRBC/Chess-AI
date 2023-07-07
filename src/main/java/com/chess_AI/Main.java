package com.chess_AI;

import com.chess_AI.view.initialScreen.InitialScreen;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // SCREEN SETTINGS
        final int ORIGINAL_IMAGE_SIZE = 128; // 16x16 px
        final float SCALE = 1/1.5f; // aumenta o tamanho da imagem n vezes

        final int IMAGE_SIZE = Math.round(ORIGINAL_IMAGE_SIZE * SCALE); // tamanho da imagem que vai ser desenhada na tela
        final int MAX_SCREEN_COL = 8;
        final int MAX_SCREEN_ROW = 8;
        final int SCREEN_WIDTH = IMAGE_SIZE * MAX_SCREEN_COL; // largura da tela
        final int SCREEN_HEIGHT = IMAGE_SIZE * MAX_SCREEN_ROW; // altura da tela

        // inicia tela
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Chess-AI");
        window.setResizable(false);

        CardLayout layout = new CardLayout();
        JPanel father = new JPanel();
        father.setLayout(layout);

        InitialScreen initialScreen = new InitialScreen(680, 680, father, layout);

        father.add(initialScreen, "Initial screen");

        window.add(father);
        window.pack();
        window.setLocationRelativeTo(null); // window vai aparecer no centro da tela
        window.setVisible(true);
    }
}
