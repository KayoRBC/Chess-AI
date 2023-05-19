package com.chess_AI;

import com.chess_AI.util.PieceColor;
import com.chess_AI.view.BoardPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // iniciando tela
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Chass game");

        // definindo cor do usuario
        final PieceColor USER_COLOR = PieceColor.BLACK;

        // definindo quem vai comecar
        boolean isUserStart = true;

        // definindo direcao do tabuleiro
        PieceColor.setIsWhiteUp(true);

        // criando tela do tabuleiro
        BoardPanel boardPanel = new BoardPanel(USER_COLOR, isUserStart);

        // inserindo tela do tabuleiro na tela principal
        window.add(boardPanel);

        window.pack();

        // window vai aparecer no centro da tela
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // iniciando thread do tabuleiro para inserir os botoes das pecas
        //boardPanel.startGameThread();

    }
}
