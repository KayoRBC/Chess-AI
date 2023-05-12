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
        JButton button = new JButton();
        button.setOpaque(false);
        button.setBounds(0, 0, size, size);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BOARD_PANEL.selectPosition(LINE, COLUMN);
            }
        });
        add(button);
    }

    public void insertButton(){
        setBounds(COLUMN * SIZE, LINE * SIZE, SIZE, SIZE);
        BOARD_PANEL.add(this);
        System.out.println("CRIADO bobao"+ LINE + COLUMN);
    }

}
