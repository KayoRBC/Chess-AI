package com.chess_AI.view.gameScreen;

import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta classe representa os botoes para selecao de posicao no tabuleiro. Tambem tem a responsabilidade de
 * chamar as funcoes para realizar as jogadas do Jogador e da IA.
 */
public class PositionSelector extends JLabel {

    /** Primeira posicao selecionada.*/
    private Position first;

    /** Segunda posicao selecionada.*/
    private Position second;

    /** Panel onde esta desenhado o tabuleiro e possui as funcoes para jogar.*/
    private final BoardPanel BOARD_PANEL;

    /**
     * Cria e retorna objeto de PositionSelector.
     *
     * @param boardPanel Panel onde esta desenhado o tabuleiro.
     */
    public PositionSelector(int screenSize, BoardPanel boardPanel){
        BOARD_PANEL = boardPanel;

        Dimension size = new Dimension(screenSize, screenSize);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(new GridLayout(8, 8));

        for(int line = 0; line < 8; line++){
            for(int column = 0; column < 8; column++){
                Position position = new Position(line, column);
                JButton button = createButton(position);
                add(button);
            }
        }

        resetSelectedPositions();
    }

    /**
     * Reseta as posicoes selecionadas e deixa o fundo de todos os botoes transparente.
     */
    private void resetSelectedPositions(){
        first = null;
        second = null;

        // define fundo transparente em todos os botoes
        for(Component c : getComponents()){
            if(c instanceof JButton b) b.setContentAreaFilled(false);
        }
        repaint();
    }

    /**
     * Cria e retorna um botao com posicao registrado internamente.
     *
     * @param position Posicao do botao
     */
    private JButton createButton(Position position){
        JButton button = new JButton();

        // configura visualizacao do botao
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(false); // tem que definir para false. Se nao o botao vai ficar opaco
        button.setBackground(new Color(255, 0, 0, 144));

        // define funcao para definir uma posicao do tabuleiro quando selecionar o botao e chamar a jogada da IA
        button.addActionListener(new ActionListener() {
            final Position POSITION = position;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectPosition(POSITION)) button.setContentAreaFilled(true);

                // realiza jogada se as posicoes ja foram selecionadas
                if(first != null && second != null){
                    Move move = new Move(first, second);
                    boolean hasBeenMoved = BOARD_PANEL.makeUserPlay(move);
                    resetSelectedPositions();
                    if(hasBeenMoved)BOARD_PANEL.makeAIPlay();
                }

                repaint();
            }
        });
        return button;
    }

    /**
     * Atualiza a primeira e a segunda posicao selecionada pelo usuario, sendo a primeira chamada para
     * a primeira posicao, segunda chamada para a segunda posicao. Alem disso, apenas eh atualizada
     * se alguma posicao estiver disponivel.
     *
     * @param position Posicao para registrar.
     * @return Se foi possivel selecionar a posicao.
     */
    private boolean selectPosition(Position position){
        // insere primeira posicao
        if (first == null) {
            first = position;
            return true;
        }
        // insere segunda posicao
        else if (second == null){
            second = position;
            return true;
        }
        else return false;
    }
}
