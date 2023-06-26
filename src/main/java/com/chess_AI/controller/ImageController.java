package com.chess_AI.controller;

import com.chess_AI.util.PieceColor;
import com.chess_AI.view.gameScreen.BoardPanel;
import com.chess_AI.util.PieceType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Esta classe representa um controller para carregar as imagens de src, possui funcoes para carregamento dessas imagens
 */
public abstract class ImageController {

    /**
     * Carrega e retorna uma matriz de imagens que representa o fundo do tabuleiro de xadrez
     *
     * @return Uma matriz de imagens
     */
    public static BufferedImage[][] loadBackground(){
        // matriz que vai armazenar as imagens de fundo
        BufferedImage[][] board = new BufferedImage[8][8];

        // variavel que representa se a posicao do fundo eh escura
        boolean isDark = false;

        // pecorre posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                try {
                    // pega caminho de uma imagem de uma certa posicao do fundo
                    URL path;
                    // se escuro
                    if(isDark){
                        path = BoardPanel.class.getResource("/images/background/square_dark_brown.png");
                    }
                    // se claro
                    else{
                        path = BoardPanel.class.getResource("/images/background/square_light_brown.png");
                    }

                    // carrega imagem e insere na matriz
                    board[i][j] = ImageIO.read(path);

                    // troca tonalidade
                    isDark = !isDark;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // troca tonalidade
            isDark = !isDark;
        }

        // retorna matriz com as imagens carregadas
        return board;
    }


    /**
     * Carrega e retorna a imagem de uma repectiva peca e cor
     *
     * @param type Tipo da peca
     * @param pieceColor Cor da peca
     * @return Imagem da peca (se for NullPiece entao retorna null)
     */
    public static BufferedImage loadPiece(PieceType type, PieceColor pieceColor){
        // converte a cor da peca em uma string
        String color;
        if(pieceColor == PieceColor.BLACK){
            color = "black";
        }
        else{
            color = "white";
        }

        // carrega imagem de acordo com o tipo da peca e a cor
        BufferedImage image = switch (type) {
            case ROOK -> loadPiece(color + "_rook");
            case KNIGHT -> loadPiece(color + "_knight");
            case BISHOP -> loadPiece(color + "_bishop");
            case QUEEN -> loadPiece(color + "_queen");
            case KING -> loadPiece(color + "_king");
            case PAWN -> loadPiece(color + "_pawn");
            default -> null;
        };

        // retorna imagem
        return image;
    }

    /**
     * Carrega a imagem de uma peca de acordo com o nome do arquivo
     *
     * @param pieceNamePng Nome do arquivo que contem a imagem
     * @return Imagem carregada (se nao existir entao retorna null)
     */
    private static BufferedImage loadPiece(String pieceNamePng){
        // pega caminho da imagem
        URL path;
        path = BoardPanel.class.getResource("/images/pieces/"+pieceNamePng+".png");

        // carrega imagem
        BufferedImage image = null;
        try {
            image = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // retorna imagem
        return image;
    }
}
