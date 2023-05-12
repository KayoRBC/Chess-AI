package com.Kayo.view;

import com.Kayo.controller.BoardController;
import com.Kayo.controller.ImageController;
import com.Kayo.util.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class BoardPanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalImageSize = 128; // 16x16 px
    final float scale = 1/1.5f; // aumenta o tamanho da imagem n vezes

    final int imageSize = Math.round(originalImageSize * scale); // tamanho da imagem que vai ser desenhada na tela
    final int maxScreenCol = 8;
    final int maxScreenRow = 8;
    final int screenWidth = imageSize * maxScreenCol; // largura da tela
    final int screenHeight = imageSize * maxScreenRow; // altura da tela

    // define o fps do jogo
    final int fps  = 60;

    Thread gameThread;

    private final ImageController imageController = new ImageController();
    private final BoardController boardController = new BoardController();

    //
    private int fromLine = 0;
    private int fromColumn = 0;
    private int toLine = 0;
    private int toColumn = 0;
    private int select = 0;

    public BoardPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        //this.setDoubleBuffered(true);
        //this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        insertPiecesButtons();
    }

    // chamado pelo metodo repaint()
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawBackGround(g2);
        drawPieces(g2);

        g2.dispose();
    }

    private void drawBackGround(Graphics2D g2){
        BufferedImage[][] background = imageController.loadBackground();
        for(int i = 0; i < background.length; i++){
            for(int j = 0; j < background.length; j++){
                g2.drawImage(background[i][j], j * imageSize, i * imageSize, imageSize, imageSize, null);
            }
        }
    }

    private void drawPieces(Graphics2D g2){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                PieceType type = boardController.getTypeOf(i, j);
                PieceColor color = boardController.getColorOf(i, j);
                BufferedImage image = imageController.loadPiece(type, color);
                if(image != null) {
                    g2.drawImage(image, j * imageSize, i * imageSize, imageSize, imageSize, null);
                }
            }
        }
    }

    private void insertPiecesButtons(){
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceButton button = new PieceButton(i, j, imageSize, this);
                button.insertButton();
            }
        }
    }

    public void selectPosition(int line, int column){
        if(select == 0){
            fromLine = line;
            fromColumn = column;
        }
        else{
            toLine = line;
            toColumn = column;
        }
        select++;
        if(select == 2){
            System.out.println("From line: "+fromLine+" Column: "+fromColumn+" | To line: "+toLine+" Column: "+toColumn);

            if(boardController.move(fromLine, fromColumn, toLine, toColumn)){
                System.out.println("Deu boa");
            }
            else{
                System.out.println("Deu errado");
            }
            select = 0;
            repaint();
        }
    }
}
