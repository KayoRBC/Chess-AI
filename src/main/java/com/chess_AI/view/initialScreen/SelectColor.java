package com.chess_AI.view.initialScreen;

import com.chess_AI.controller.ImageController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Esta classe representa um painel de selecao de uma cor de peca
 */
public class SelectColor extends JComponent {

    /** Cor selecionada, se null entao nenhuma cor selecionada*/
    private PieceColor selectedColor = null;

    /** Ver o fundo do painel*/
    private final boolean VIEW_BACKGROUND;

    /** Armazena os botoes de selecao de cor de peca*/
    private ArrayList<JButton> buttons = new ArrayList<JButton>();


    /**
     * Cria novo objeto de SelectColor com largura, altura e vizualizacao de fundo definidos
     *
     * @param width Largura do painel
     * @param height Altura do painel
     * @param viewBackground Vizualizar fundo do painel
     */
    public SelectColor(int width, int height, boolean viewBackground){
        this.VIEW_BACKGROUND = viewBackground;

        setPreferredSize(new Dimension(width, height));

        // distancia horizontal de gap
        int horizontalGap = Math.round(width*0.20f);

        // alinha elementos verticalmente
        setLayout(new FlowLayout(FlowLayout.CENTER, horizontalGap, 0));

        // texto
        JLabel description = new JLabel("Selecione a sua cor de peça");
        // muda cor e fonte do texto
        description.setForeground(Color.WHITE);
        Font font = new Font(description.getFont().getName(), Font.PLAIN, Math.round(height * 0.20f));
        description.setFont(font);
        // adiciona texto no painel
        add(description);

        // adiciona botoes
        int buttonSize = Math.round(height * 0.80f);
        addButton(buttonSize, buttonSize, PieceColor.WHITE);
        addButton(buttonSize, buttonSize, PieceColor.BLACK);
    }


    /**
     * Adiciona um botao de uma determinada cor de peca no painel
     *
     * @param width Largura do botao
     * @param height Altura do botao
     * @param pieceColor Cor da peca
     * */
    private void addButton(int width, int height, PieceColor pieceColor) {
        JButton button = new JButton();

        // define configuracoes do botao
        button.setPreferredSize(new Dimension(width, height)); // dimensoes do botao
        button.setBorderPainted(false); // borda transparente
        button.setContentAreaFilled(false); // fundo transparente
        button.setRolloverEnabled(false); // efeito de quando coloca o mouse em cima do botao sendo desabilitado
        button.setPressedIcon(null); // remove o icone exibido quando o botao eh selecionado
        button.setDisabledIcon(null); // remove o icone exibido quando o botao esta desativado
        button.setFocusPainted(false); // desabilita o destaque visual quando o botao esta em foco

        // adiciona acao de atualizacao de cor selecionada do painel
        button.addActionListener(new ActionListener() {
            private final PieceColor color = pieceColor; // cor da peca
            @Override
            public void actionPerformed(ActionEvent e) {
                // reseta cores de todos os botoes
                for(JButton otherButton : buttons){
                    // muda cor de fundo do boao selecionado para transparente
                    otherButton.setContentAreaFilled(false);
                    otherButton.setBackground(new Color(255, 0, 0, 0));
                }

                // atualiza cor de peca selecionada
                selectedColor = color;

                // muda cor de fundo do botao para opaco
                button.setContentAreaFilled(true);
                button.setBackground(new Color(255, 0, 0, 255));
            }
        });

        // carrega e coloca imagem da cor de peca no botao
        BufferedImage image = ImageController.loadPiece(PieceType.PAWN, pieceColor);
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        button.setIcon(icon);

        // adiciona botao no painel
        add(button);

        // adiciona botao na lista de botoes
        buttons.add(button);
    }


    @Override
    protected void paintComponent(Graphics g) {
        if(VIEW_BACKGROUND) {
            // desenha fundo do painel
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Retorna a cor de peca selecionada, se null entao nenhuma peca selecionada
     *
     * @return Cor de peca selecionada
     */
    public PieceColor getSelectedColor() {
        return selectedColor;
    }
}
