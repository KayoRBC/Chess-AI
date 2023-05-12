package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.movements.RookMovementRules;
import com.Kayo.util.PieceColor;

public class Rook extends Piece{
    public Rook(PieceColor color) {
        super(color, new RookMovementRules());
    }
}
