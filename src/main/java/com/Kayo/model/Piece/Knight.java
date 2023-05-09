package com.Kayo.model.Piece;

import com.Kayo.model.movements.KnightMovementRules;
import com.Kayo.model.movements.MovementRules;
import com.Kayo.util.PieceColor;

public class Knight extends Piece{
    public Knight(PieceColor color) {
        super(color, new KnightMovementRules());
    }
}
