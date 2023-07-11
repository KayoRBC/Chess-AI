package com.chess_AI.view.gameScreen;

import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Esta classe representa a selecao de peca quando um peao do jogador chega no final do tabuleiro.
 * Ela eh responsavel por chamar a troca do peao e fazer a jogada da IA logo em seguida.
 */
public class PromoteSelector extends JLabel {

    /** Panel onde esta desenhado o tabuleiro e possui a funcao para troca do peao*/
    private final BoardPanel BOARD_PANEL;

    /**
     * Cria e retorna objeto de PromoteSelector.
     * No meio desse panel vai aparecer o seletor.
     *
     * @param screenSize Tamanho da tela (screenSize X screenSize)
     * @param pieceColor Cor das pecas que vao aparecer no seletor.
     * @param boardPanel Panel onde esta desenhado o tabuleiro
     */
    public PromoteSelector(int screenSize, PieceColor pieceColor, BoardPanel boardPanel){
        BOARD_PANEL = boardPanel;

        // define tamanho de panel
        Dimension size = new Dimension(screenSize, screenSize);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // posiciona o seletor no meio de panel
        add(Box.createVerticalGlue());
        JPanel selector = createSelector(screenSize/7, pieceColor);
        selector.setAlignmentX(CENTER_ALIGNMENT);
        add(selector);
        add(Box.createVerticalGlue());

    }

    /**
     * Cria e retorna um panel com os botoes para o usuario selecionar a peca.
     *
     * @param buttonSize Tamanho dos botoes (buttonSize X buttonSize)
     * @param color Cor das pecas dos botoes
     * @return JPanel com os botoes.
     */
    private JPanel createSelector(int buttonSize, PieceColor color){
        // opcoes no seletor
        PieceType[] types = {PieceType.BISHOP, PieceType.KNIGHT, PieceType.QUEEN, PieceType.ROOK};

        JPanel selector = new JPanel();

        // define tamanho do seletor
        Dimension size = new Dimension(buttonSize * types.length, buttonSize);
        selector.setPreferredSize(size);
        selector.setMinimumSize(size);
        selector.setMaximumSize(size);

        // adiciona os botoes no seletor
        selector.setLayout(new GridLayout(1, 0));
        for(PieceType type: types){
            JButton piece = createPiece(type, color, buttonSize);
            selector.add(piece);
        }

        return selector;
    }

    /**
     * Cria e retorna um botao com a imagem de uma peca dentro.
     * Alem disso, com funcao para chamar a atualizacao do peao no tabuleiro.
     *
     * @param type Tipo da peca
     * @param color Cor da peca
     * @param imageSize Tamanho da imagem da peca (imageSize X imageSize)
     * @return O botao com a imagem de uma peca dentro.
     */
    private JButton createPiece(PieceType type, PieceColor color, int imageSize){
        JButton piece = new JButton();

        // adiciona funcao para chamar a atualizacao do peao no tabuleiro e chamar a jogada a IA
        piece.addActionListener(new ActionListener() {
            private final PieceType TYPE = type;

            @Override
            public void actionPerformed(ActionEvent e) {
                BOARD_PANEL.makePawnChange(TYPE);
                BOARD_PANEL.makeAIPlay();
            }
        });

        // carrega e coloca imagem da cor de peca no botao
        BufferedImage image = ImageController.loadPiece(type, color);
        Image scaledImage = image.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        piece.setIcon(icon);

        return piece;
    }
}
