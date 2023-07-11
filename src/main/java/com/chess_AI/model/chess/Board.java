package com.chess_AI.model.chess;

import com.chess_AI.model.chess.Piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Functions;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

/**
 * Esta classe armazena o tabuleiro de xadrez, possui a matriz de pecas do tabuleiro
 * e funcoes para manipulacao da mesma sem regras.
 */
public class Board implements Cloneable{

    /** Representa a posicao das pecas no tabuleiro*/
    private Piece[][] pieces;

    /**
     * Cria e retorna um tabuleiro de xadrez com o estado inicial do jogo.
     */
    public Board(){
        pieces = createInitialState();
    }

    /**
     * Cria e retorna uma matriz de pecas com as posicoes iniciais delas no tabuleiro.
     * Se as pecas dao colocadas em cima ou em baixo do tabuleiro eh determinado por
     * PieceColor no metodo isWhiteUp().
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
        Piece[] nullLine = new Piece[8];

        // insere NullPieces em toda linha
        for(int i = 0; i < nullLine.length; i++){
            nullLine[i] = new NullPiece();
        }

        return nullLine;
    }

    /**
     * Cria e retorna uma linha do tabuleiro com peoes de uma determinada cor.
     *
     * @param color Cor do peao
     * @return Array de Pawn
     */
    private Piece[] createFrontLine(PieceColor color){
        Piece[] frontLine = new Piece[8];

        // insere peoes em toda linha
        for(int i = 0; i < frontLine.length; i++){
            frontLine[i] = new Pawn(color);
        }

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

        return backLine;
    }

    /**
     * Troca as posicoes de duas pecas no tabuleiro.
     *
     * @param first Primeira posicao
     * @param second Segunda posicao
     */
    public void switchPieces(Position first, Position second){
        Piece firstPiece = getPiece(first);

        Piece secondPiece = getPiece(second);

        // troca posicoes
        if(firstPiece != null && secondPiece != null) {
            pieces[first.LINE][first.COLUMN] = secondPiece;
            pieces[second.LINE][second.COLUMN] = firstPiece;
        }
    }

    /**
     * Remove uma peca de uma determinada posicao do tabuleiro e substitui por uma NullPiece.
     *
     * @param position Posicao da peca para remover
     */
    public void removePiece(Position position){
        try {
            pieces[position.LINE][position.COLUMN] = new NullPiece();
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Retorna a peca de uma determinada posicao do tabuleiro.
     *
     * @param position Posicao no tabuleiro
     * @return A peca que esta na posicao escolhida (se nao existir retorna null)
     */
    public Piece getPiece(Position position){
        try{
            return pieces[position.LINE][position.COLUMN];
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Insere uma nova peca de um determinado tipo e cor em uma posicao do tabuleiro.
     *
     * @param type Tipo da peca
     * @param color Cor da peca
     * @param hasMoved Se foi movimentada alguma vez
     * @param position Posicao no tabuleiro
     * @return Se conseguiu inserir a peca
     */
    public boolean setPiece(PieceType type, PieceColor color, boolean hasMoved, Position position){
        // cria peca
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
        piece.setHasMoved(hasMoved);

        // insere peca no tabuleiro
        try{
            pieces[position.LINE][position.COLUMN] = piece;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se uma determinada posicao do tabuleiro eh perigosa para uma determinada cor
     *
     * @param allyColor Cor da peca para verificar se eh perigosa a posicao
     * @param position Posicao para verificar se eh segura
     * @return Se a posicao eh perigosa
     */
    public boolean isDungerousPosition(PieceColor allyColor, Position position){
        // percorre posicoes do tabuleiro
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                Position from = new Position(fromLine, fromColumn);
                Piece piece = getPiece(from);

                if(piece != null) {
                    boolean isEnemy = piece.getColor() != allyColor;
                    if (isEnemy) {
                        // verifica se peca inimiga pode ir na posicao aliada
                        Move move = new Move(from, position);
                        if (piece.isValidMove(this, move)) return true; // posicao perigosa
                    }
                }
            }
        }
        return false; // posicao segura
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

        // verifica se as colunas da linha sao seguras
        int[] columns = Functions.createIntermediateValues(startColumn, finalColumn);
        if(columns != null){
            for(int column: columns){
                Position position = new Position(line, column);
                if(isDungerousPosition(allyColor, position)) return false;
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