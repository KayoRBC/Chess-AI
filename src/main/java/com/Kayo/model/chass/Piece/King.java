package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.movements.KingMovementRules;
import com.Kayo.util.PieceColor;

public class King extends Piece{
    public King(PieceColor color) {
        super(color, new KingMovementRules());
    }
}
