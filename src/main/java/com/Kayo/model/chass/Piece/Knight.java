package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.movements.KnightMovementRules;
import com.Kayo.util.PieceColor;

public class Knight extends Piece{
    public Knight(PieceColor color) {
        super(color, new KnightMovementRules());
    }
}
