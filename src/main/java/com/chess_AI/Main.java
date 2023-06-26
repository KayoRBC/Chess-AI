package com.chess_AI;

import com.chess_AI.util.PieceColor;
import com.chess_AI.view.gameScreen.BoardPanel;
import com.chess_AI.view.initialScreen.InitialScreen;

import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
        window.setResizable(false);
        window.setTitle("Chass game");

        // cria tela inicial
        InitialScreen initialScreen = new InitialScreen(SCREEN_WIDTH, SCREEN_HEIGHT);

        // adiciona tela inicial na tela principal
        window.add(initialScreen);
        window.pack();
        window.repaint();

        window.setLocationRelativeTo(null); // window vai aparecer no centro da tela
        window.setVisible(true);

        // espera ate poder comecar o jogo
        while(!initialScreen.canStart()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        window.remove(initialScreen);

        // cor do usuario
        final PieceColor USER_COLOR = initialScreen.getColor();

        // define direcao do tabuleiro
        // por default peca branca é desenhada no topo do tabuleiro
        if(USER_COLOR == PieceColor.WHITE) {
            PieceColor.setIsWhiteUp(false);
        }

        // cria tela do tabuleiro
        BoardPanel boardPanel = new BoardPanel(SCREEN_WIDTH, USER_COLOR);

        // adiciona tela do tabuleiro na tela principal
        window.add(boardPanel);
        window.pack();

        // inicia jogo
        boardPanel.start();
    }
}
