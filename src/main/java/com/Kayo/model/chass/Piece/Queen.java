package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.movements.QueenMovementRules;
import com.Kayo.util.PieceColor;

public class Queen extends Piece{
    public Queen(PieceColor color) {
        super(color, new QueenMovementRules());
    }
}
