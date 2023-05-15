package com.Kayo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieceButton extends JComponent{
    private final int LINE;
    private final int COLUMN;

    private final int SIZE;

    private final BoardPanel BOARD_PANEL;

    public PieceButton(int line, int column, int size, BoardPanel boardPanel){
        this.LINE = line;
        this.COLUMN = column;
        this.SIZE = size;
        this.BOARD_PANEL = boardPanel;

        setPreferredSize(new Dimension(size, size));
        this.setFocusable(true);

        createButton(size);
    }

    private void createButton(int size){
        // criando botao
        JButton button = new JButton();
        // informando que botao vai ser invisivel
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        // posicionando e redirecionando botao no container
        button.setBounds(0, 0, size, size);
        // inserindo funcao de execucao quando apertar o botao
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // inserindo posicoes do botao no board panel
                BOARD_PANEL.selectPosition(LINE, COLUMN);
                // mudando cor do botao selecionado
                button.setBackground(new Color(255, 0, 0, 166));
                button.setContentAreaFilled(true);
            }
        });
        // inserindo o botao no container
        add(button);
    }

    public void insertButton(){
        // posicionando o botao na tela
        setBounds(COLUMN * SIZE, LINE * SIZE, SIZE, SIZE);
        // inserindo na tela
        BOARD_PANEL.add(this);
    }

}
