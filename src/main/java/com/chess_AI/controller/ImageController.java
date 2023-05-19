package com.chess_AI.controller;

import com.chess_AI.util.PieceColor;
import com.chess_AI.view.BoardPanel;
import com.chess_AI.util.PieceType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public abstract class ImageController {

    // carrega e retorna uma matriz com as imagens de fundo
    public static BufferedImage[][] loadBackground(){
        // matriz que vai armazenar as imagens de fundo
        BufferedImage[][] board = new BufferedImage[8][8];

        // variavel que representa se a posicao do fundo eh escura
        boolean isDark = false;
        // pecorrendo posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                try {
                    // pegando caminho de uma imagem de uma certa posicao do fundo
                    URL path;
                    // se escuro
                    if(isDark){
                        path = BoardPanel.class.getResource("/images/background/square_dark_brown.png");
                    }
                    // se claro
                    else{
                        path = BoardPanel.class.getResource("/images/background/square_light_brown.png");
                    }
                    // carregando imagem e inserindo na matriz
                    board[i][j] = ImageIO.read(path);

                    // trocando tonalidade
                    isDark = !isDark;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // trocando tonalidade
            isDark = !isDark;
        }
        // retornando matriz com as imagens carregadas
        return board;
    }

    // carrega a imagem de uma peca selecionada por tipo e cor
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

    // retorna a imagem carregada do nome da peca com a cor
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
