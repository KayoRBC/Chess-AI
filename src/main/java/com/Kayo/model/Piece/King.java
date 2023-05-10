package com.Kayo.model.Piece;

import com.Kayo.model.movements.KingMovementRules;
import com.Kayo.model.movements.MovementRules;
import com.Kayo.util.PieceColor;

public class King extends Piece{
    public King(PieceColor color) {
        super(color, new KingMovementRules());
    }
}
