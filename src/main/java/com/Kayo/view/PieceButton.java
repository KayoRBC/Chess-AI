package com.Kayo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieceButton extends JButton{
    private final int LINE;
    private final int COLUMN;
    private final BoardPanel BOARD_PANEL;

    public PieceButton(int line, int column, BoardPanel boardPanel){
        this.LINE = line;
        this.COLUMN = column;
        this.BOARD_PANEL = boardPanel;
        addConfigs();
    }

    private void addConfigs(){
        // configurando para o botao ser invisivel
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        // inserindo funcao de execucao quando apertar o botao
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // inserindo posicoes do botao no board panel
                BOARD_PANEL.selectPosition(LINE, COLUMN);
                // mudando cor do botao selecionado
                setBackground(new Color(255, 0, 0, 166));
                setContentAreaFilled(true);
            }
        });
    }
}
