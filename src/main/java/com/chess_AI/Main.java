package com.chess_AI;

import com.chess_AI.view.initialScreen.InitialScreen;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // inicia tela
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Chess-AI");
        window.setResizable(false);

        CardLayout layout = new CardLayout(); // para trocar as telas
        JPanel principal = new JPanel();
        principal.setLayout(layout);

        InitialScreen initialScreen = new InitialScreen(680, principal);

        principal.add(initialScreen, "Initial screen");

        window.add(principal);
        window.pack();
        window.setLocationRelativeTo(null); // window vai aparecer no centro da tela
        window.setVisible(true);
    }
}
