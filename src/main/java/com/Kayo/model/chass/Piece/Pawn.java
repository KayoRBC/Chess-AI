package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.movements.PawnMovementRules;
import com.Kayo.util.PieceColor;

public class Pawn extends Piece{
    public Pawn(PieceColor color) {
        super(color, new PawnMovementRules());
    }
}
