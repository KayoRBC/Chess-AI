package com.Kayo.model.Piece;

import com.Kayo.model.movements.MovementRules;
import com.Kayo.model.movements.PawnMovementRules;
import com.Kayo.util.PieceColor;

public class Pawn extends Piece{
    public Pawn(PieceColor color) {
        super(color, new PawnMovementRules());
    }
}
