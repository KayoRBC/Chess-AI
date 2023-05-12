package com.Kayo;

import com.Kayo.view.BoardPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Chass game");

        BoardPanel boardPanel = new BoardPanel();
        window.add(boardPanel);

        window.pack(); // usar os parametros da classe GamePanel (subclasse de JFrame)

        // window vai aparecer no centro da tela
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        boardPanel.startGameThread();

    }
}
