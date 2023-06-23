package com.chess_AI.view.gameScreen;

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

    // adiciona configs do botao
    private void addConfigs(){
        // configurando para o botao ser invisivel
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setRolloverEnabled(false); // efeito de quando coloca o mouse em cima do botao sendo desabilitado
        setPressedIcon(null); // remove o icone exibido quando o botao eh selecionado
        setDisabledIcon(null); // remove o icone exibido quando o botao esta desativado
        setFocusPainted(false); // desabilita o destaque visual quando o botao esta em foco

        // inserindo funcao de execucao quando apertar o botao
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se der certo de selecionar a posicao no tabuleiro
                if(BOARD_PANEL.selectPosition(LINE, COLUMN)) {
                    // mudando cor do botao selecionado
                    setContentAreaFilled(true);
                    setBackground(new Color(255, 0, 0, 166));
                }
            }
        });
    }
}
