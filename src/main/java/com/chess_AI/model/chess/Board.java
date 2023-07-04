package com.chess_AI.model.chess;

import com.chess_AI.model.chess.Piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Util;

/**
 * Esta classe armazena o tabuleiro de xadrez, possui a matriz de pecas do tabuleiro
 * e funcoes para manipulacao da mesma.
 */
public class Board implements Cloneable{

    /** Representa a posicao das pecas no tabuleiro*/
    private Piece[][] pieces;

    /**
     * Cria e retorna um tabuleiro de xadrez com o estado inicial do jogo.
     */
    public Board() {
        pieces = createInitialState();
    }

    /**
     * Cria e retorna uma matriz de pecas com as posicoes iniciais delas no tabuleiro.
     *
     * @return Uma matriz de pecas
     */
    private Piece[][] createInitialState(){
        // matriz que vai armazenar as pecas
        Piece[][] pieces = new Piece[8][8];

        // pegando qual vai ser a cor que vai ser desenhada em cima e em baixo do tabuleiro
        PieceColor colorUp;
        PieceColor colorDown;
        if(PieceColor.isWhiteUp()){
            colorUp = PieceColor.WHITE;
            colorDown = PieceColor.BLACK;
        }
        else{
            colorUp = PieceColor.BLACK;
            colorDown = PieceColor.WHITE;
        }

        // insere pecas iniciais na parte de cima do tabuleiro
        pieces[0] = createBackLine(colorUp);
        pieces[1] = createFrontLine(colorUp);

        // insere regiao intermediaria entre as duas cores
        for(int i = 2; i < 6; i++){
            pieces[i] = createNullLine();
        }

        // insere pecas iniciais na parte de baixo do tabuleiro
        pieces[6] = createFrontLine(colorDown);
        pieces[7] = createBackLine(colorDown);

        // retorna matriz de pecas
        return pieces;
    }

    /**
     * Cria e retorna uma linha do tabuleiro com pecas vazias.
     *
     * @return Array de NullPiece
     */
    private Piece[] createNullLine(){
        // cria uma linha com pecas vazias
        Piece[] nullLine = new Piece[8];
        for(int i = 0; i < nullLine.length; i++){
            nullLine[i] = new NullPiece();
        }
        // retorna a linha
        return nullLine;
    }

    /**
     * Cria e retorna uma linha do tabuleiro com peoes de uma determinada cor.
     *
     * @param color Cor do peao
     * @return Array de Pawn
     */
    private Piece[] createFrontLine(PieceColor color){
        // cria uma linha de peoes
        Piece[] frontLine = new Piece[8];
        for(int i = 0; i < frontLine.length; i++){
            frontLine[i] = new Pawn(color);
        }
        // retorna a linha
        return frontLine;
    }

    /**
     * Cria e retorna as pecas que estao atras dos peoes (front line) de uma determinada cor.
     *
     * @param color Cor das pecas
     * @return Array com as pecas que ficam atras dos peoes
     */
    private Piece[] createBackLine(PieceColor color){
        // cria linha de tras
        Piece[] backLine = new Piece[8];
        backLine[0] = new Rook(color);
        backLine[1] = new Knight(color);
        backLine[2] = new Bishop(color);
        backLine[3] = new Queen(color);
        backLine[4] = new King(color);
        backLine[5] = new Bishop(color);
        backLine[6] = new Knight(color);
        backLine[7] = new Rook(color);

        // retorna linha de tras
        return backLine;
    }

    /**
     * Troca a posicao de duas pecas do tabuleiro.
     *
     * @param fromLine Posicao da linha da primeira peca
     * @param fromColumn Posicao da coluna da primeira peca
     * @param toLine Posicao da linha da segunda peca
     * @param toColumn Posicao da coluna da segunda peca
     */
    public void switchPieces(int fromLine, int fromColumn, int toLine, int toColumn){
        // pega primeira peca
        Piece fromPiece = getPiece(fromLine, fromColumn);

        // pega segunda peca
        Piece toPiece = getPiece(toLine, toColumn);

        // se pecas existirem
        if(fromPiece != null && toPiece != null) {

            // troca posicoes
            pieces[toLine][toColumn] = fromPiece;
            pieces[fromLine][fromColumn] = toPiece;
        }
    }

    /**
     * Remove uma peca de uma determinada posicao do tabuleiro.
     *
     * @param line Posicao da linha da peca
     * @param column Posicao da coluna da peca
     */
    public void removePiece(int line, int column){
        // pega peca da posicao que quer retirar
        Piece piece = getPiece(line, column);

        // se peca existe
        if(piece != null){
            // substitui por uma peca vazia
            pieces[line][column] = new NullPiece();
        }
    }

    /**
     * Retorna a peca de uma determinada posicao do tabuleiro.
     *
     * @param line Posicao da linha da peca
     * @param column Posicao da coluna da peca
     * @return A peca que esta na posicao escolhida (se nao existir retorna null)
     */
    public Piece getPiece(int line, int column){
        try{
            // se peca existe retorna ela
            return pieces[line][column];
        } catch (Exception e) {
            // se peca nao existe retorna null
            return null;
        }
    }

    /**
     * Insere uma nova peca de um determinado tipo e cor em uma posicao do tabuleiro.
     *
     * @param type Tipo da peca
     * @param color Cor da peca
     * @param line Posicao da linha para inserir
     * @param column Posicao da coluna para inserir
     * @param hasMoved Se foi movimentada alguma vez
     * @return Se conseguiu inserir a peca
     */
    public boolean setPiece(PieceType type, PieceColor color, int line, int column, boolean hasMoved){
        // se linha e coluna estiverem dentro do tabuleiro
        if(-1 < line && line < 8 && -1 < column && column < 8){
            Piece piece;
            switch (type) {
                case PAWN -> piece = new Pawn(color);
                case KNIGHT -> piece = new Knight(color);
                case BISHOP -> piece = new Bishop(color);
                case ROOK -> piece = new Rook(color);
                case QUEEN -> piece = new Queen(color);
                case KING -> piece = new King(color);
                default -> {
                    return false;
                }
            }
            // atualiza estado da peca
            piece.setHasMoved(hasMoved);

            // insere peca no tabuleiro
            pieces[line][column] = piece;

            // conseguiu inserir
            return true;
        }
        // nao conseguiu inserir
        return false;
    }

    /**
     * Verifica se uma determinada posicao do tabuleiro eh perigosa para uma determinada cor
     *
     * @param allyColor Cor da peca para verificar se eh perigosa a posicao
     * @param checkLine Posicao da linha para verificar
     * @param checkColumn Posicao da coluna para verificar
     * @return Se a posicao eh perigosa
     */
    public boolean isDungerousPosition(PieceColor allyColor, int checkLine, int checkColumn){
        // percorre posicoes do tabuleiro
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){

                // pega peca de uma determinada posicao
                Piece currentPiece = getPiece(fromLine, fromColumn);

                // se peca for inimiga
                if(currentPiece.getColor() != allyColor){
                    // se peca inimiga pode ir para a posicao de verificacao
                    if(currentPiece.isValidMove(this, fromLine, fromColumn, checkLine, checkColumn)){
                        // posicao perigosa
                        return true;
                    }
                }
            }
        }
        // posicao nao perigosa
        return false;
    }

    /**
     * Verifica se as posicoes de uma coluna a outra de uma mesma linha sao seguras para uma determinada cor.
     * Posicoes que nao existem vao ser consideradas seguras.
     *
     * @param line Linha para verificar
     * @param startColumn Coluna inicial da linha
     * @param finalColumn Coluna final da linha
     * @param allyColor Cor para verificar se eh seguro
     * @return Se as posicoes sao seguras
     */
    public boolean isSafeLine(int line, int startColumn, int finalColumn, PieceColor allyColor){

        // aumenta o intervalo de valores superior e inferior em +1,
        // pois a funcao para pegar as colunas apenas retorna valores intermediarios
        if(startColumn < finalColumn){
            startColumn--;
            finalColumn++;
        }
        else{
            startColumn++;
            finalColumn--;
        }

        int[] columns = Util.createIntermediateValues(startColumn, finalColumn);

        if(columns != null){
            for(int column: columns){

                Piece piece = getPiece(line, column);

                if(piece != null && isDungerousPosition(allyColor, line, column)) return false;
            }
        }
        return true;
    }


    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();

            Piece[][] piecesClone = new Piece[8][8];
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    piecesClone[i][j] = pieces[i][j].clone();
                }
            }
            clone.pieces = piecesClone;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}