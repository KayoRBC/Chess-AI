package com.Kayo.view;

import com.Kayo.controller.ImageController;
import com.Kayo.util.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class PieceButton extends JComponent{
    private int line;
    private int column;

    private PieceType type;
    private PieceColor color;

    private int size;

    private BoardPanel boardPanel;

    BufferedImage image;

    public PieceButton(int line, int column, int size, BoardPanel boardPanel){
        this.line = line;
        this.column = column;
        this.size = size;
        this.boardPanel = boardPanel;

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
                boardPanel.selectPosition(line, column);
            }
        });
        add(button);
    }

    public void insertButton(){
        setBounds(column * size, line * size, size, size);
        boardPanel.add(this);
        System.out.println("CRIADO bobao"+line+column);
    }

}
