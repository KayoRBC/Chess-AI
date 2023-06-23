package com.chess_AI.view.initialScreen;

import com.chess_AI.util.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta classe representa o menu inicial do jogo, onde o jogador seleciona a cor da peca
 */
public class InitialScreen extends JComponent {

    /** Painel para selecionar cor de peca*/
    private final SelectColor SELECT_COLOR;

    /** Texto de erro caso nenhuma cor for selecionada*/
    private JLabel errorText;

    /** Se pode comecar o jogo*/
    private boolean start = false;

    /**
     * Cria objeto de InitialScreen com altura e largura definidos
     *
     * @param width Largura do painel
     * @param height Altura do painel
     */
    public InitialScreen(int width, int height){
        // redimenciona painel
        this.setPreferredSize(new Dimension(width, height));

        this.setFocusable(true);

        // seleciona cor de fundo do painel
        setBackground(Color.DARK_GRAY);

        // alinha componentes verticalmente
        setLayout(new FlowLayout(FlowLayout.CENTER, width, Math.round(height * 0.10f)));

        // adiciona painel para selecionar a cor de peca
        SELECT_COLOR = new SelectColor(width, Math.round(height * 0.20f), false);
        add(SELECT_COLOR);

        // adicionando espaco para o texto de erro
        addErrorText(Math.round(width * 0.19f), Math.round(height * 0.05f));

        // adiciona botao para comecar o jogo
        addStartButton(width/2, Math.round(height * 0.10f));

    }

    /**
     * Adiciona o texto de erro na tela inicial
     *
     * @param width Largura do espaco para o texto
     * @param height Altura do espaco para o texto
     */
    private void addErrorText(int width, int height){
        // adiciona texto inicial
        errorText = new JLabel("");

        // redimenciona espaco para o texto
        errorText.setPreferredSize(new Dimension(width, height));

        // muda cor e fonte do texto
        errorText.setForeground(Color.RED);
        Font font = new Font(errorText.getFont().getName(), Font.BOLD, Math.round(height * 0.35f));
        errorText.setFont(font);

        // adiciona painel de erro na tela inicial
        add(errorText);
    }

    /**
     * Adiciona botao para comecar o jogo na tela inicial
     *
     * @param width Largura do botao
     * @param height Altura do botao
     */
    private void addStartButton(int width, int height){
        // cria objeto botao
        JButton button = new JButton("JOGAR");

        // muda cor e fonte do texto dentro do botao
        button.setForeground(Color.WHITE);
        Font font = new Font(button.getFont().getName(), Font.BOLD, Math.round(height * 0.60f));
        button.setFont(font);

        // redimenciona botao
        button.setPreferredSize(new Dimension(width, height));

        // muda cor de fundo do botao
        button.setBackground(Color.GREEN);

        // adiciona acao de comecar o jogo ao botao
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // se alguma cor foi selecionada
                if(SELECT_COLOR.getSelectedColor() != null) {
                    // comecar jogo
                    System.out.println(SELECT_COLOR.getSelectedColor());
                    start = true;
                }
                // se nenhuma cor selecionada
                else{
                    errorText.setText("SELECIONE UMA COR!");
                }
            }
        });

        // adiciona botao na tela inicial
        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha um retângulo preenchido com a cor de fundo
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }


    /**
     * Retorna se pode comecar o jogo
     *
     * @return Se pode comecar o jogo
     */
    public boolean canStart() {
        return start;
    }

    /**
     * Retorna a cor da peca selecionada
     *
     * @return cor da peca selecionada
     */
    public PieceColor getColor(){
        return SELECT_COLOR.getSelectedColor();
    }
}
