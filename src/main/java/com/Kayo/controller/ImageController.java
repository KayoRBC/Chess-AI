package com.Kayo.controller;

import com.Kayo.util.PieceColor;
import com.Kayo.view.BoardPanel;
import com.Kayo.util.PieceType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class ImageController {

    public static BufferedImage[][] loadBackground(){
        BufferedImage[][] board = new BufferedImage[8][8];
        boolean isDark = false;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                try {
                    URL path;
                    if(isDark){
                        path = BoardPanel.class.getResource("/images/background/square_dark_brown.png");
                    }
                    else{
                        path = BoardPanel.class.getResource("/images/background/square_light_brown.png");
                    }
                    board[i][j] = ImageIO.read(path);
                    isDark = !isDark;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isDark = !isDark;
        }
        return board;
    }

    public static BufferedImage loadPiece(PieceType type, PieceColor pieceColor){
        // convertendo a cor da peca em uma string
        String color;
        if(pieceColor == PieceColor.BLACK){
            color = "black";
        }
        else{
            color = "white";
        }

        // selecionando imagem de acordo com o tipo da peca e a cor
        BufferedImage image = switch (type) {
            case ROOK -> loadPiece(color + "_rook");
            case KNIGHT -> loadPiece(color + "_knight");
            case BISHOP -> loadPiece(color + "_bishop");
            case QUEEN -> loadPiece(color + "_queen");
            case KING -> loadPiece(color + "_king");
            case PAWN -> loadPiece(color + "_pawn");
            default -> null;
        };

        // retornando imagem
        return image;
    }

    private static BufferedImage loadPiece(String pieceNamePng){
        // pegando caminho da imagem
        URL path;
        path = BoardPanel.class.getResource("/images/pieces/"+pieceNamePng+".png");

        // carregando imagem
        BufferedImage image = null;
        try {
            image = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // retornando imagem
        return image;
    }
}
