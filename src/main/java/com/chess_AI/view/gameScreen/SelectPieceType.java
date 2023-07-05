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
 * Esta classe representa o painel para selecao de peca quando um peao chega no final do tabuleiro.
 */
public class SelectPieceType extends JPanel{

    /** Peca selecionada pelo usuario*/
    private PieceType piece = null;


    /**
     * Cria e retorna classe de SelectPieceType.
     * Nao define a posicao para colocar esse componente na tela.
     *
     * @param buttonSize Tamanho dos botoes (buttonSize x buttonSize)
     * @param userColor Cor da peca do usuario
     * @param types Tipos de pecas para selecionar
     */
    public SelectPieceType(int buttonSize, PieceColor userColor, PieceType[] types, BoardPanel boardPanel){
        setSize(buttonSize * types.length, buttonSize);

        for (int i = 0; i < types.length; i++) {
            addButton(buttonSize, types[i], userColor, i, boardPanel);
        }
    }


    /**
     * Retorna o tipo da peca selecionada pelo usuario.
     *
     * @return Tipo da peca selecionada, caso nenhum entao retorna null.
     */
    public PieceType getPiece(){
        return piece;
    }

    /**
     * Adiciona um botao em uma determinada posicao do painel para selecao de peca.
     *
     * @param buttonSize Tamanho do botoes (buttonSize X buttonSize)
     * @param pieceType Tipo de peca respectiva ao botao
     * @param pieceColor Cor da peca
     * @param position Posicao que vai ocupar no painel
     * @param boardPanel boardPanel que vai ser adicionada as pecas
     */
    private void addButton(int buttonSize, PieceType pieceType, PieceColor pieceColor,
                           int position, BoardPanel boardPanel) {

        JButton button = new JButton();

        // define configuracoes do botao
        button.setPreferredSize(new Dimension(buttonSize, buttonSize)); // dimensoes do botao
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setRolloverEnabled(true); // efeito de quando coloca o mouse em cima do botao
        button.setPressedIcon(null); // icone exibido quando o botao eh selecionado
        button.setDisabledIcon(null); // icone exibido quando o botao esta desativado
        button.setFocusPainted(true); // destaque visual quando o botao esta em foco

        button.setBackground(Color.GRAY);

        // adiciona funcao para retornar o tipo da peca do botao
        button.addActionListener(new ActionListener() {
            private final PieceType type = pieceType;

            @Override
            public void actionPerformed(ActionEvent e) {
                boardPanel.getBoardController().changePawnType(true, type);
                boardPanel.verifyCheck();
                boardPanel.AIPlay();
            }
        });

        // carrega e coloca imagem da cor de peca no botao
        BufferedImage image = ImageController.loadPiece(pieceType, pieceColor);
        Image scaledImage = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        button.setIcon(icon);

        // posiciona botao no componente
        button.setBounds(position * buttonSize, 0, buttonSize, buttonSize);

        // adiciona botao no componente
        add(button);
    }
}
