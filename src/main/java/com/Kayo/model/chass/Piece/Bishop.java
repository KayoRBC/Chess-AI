package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.movements.BishopMovementRules;
import com.Kayo.util.PieceColor;

public class Bishop extends Piece{
    public Bishop(PieceColor color) {
        super(color, new BishopMovementRules());
    }
}
