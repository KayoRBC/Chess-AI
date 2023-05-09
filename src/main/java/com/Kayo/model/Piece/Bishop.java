package com.Kayo.model.Piece;

import com.Kayo.model.movements.BishopMovementRules;
import com.Kayo.model.movements.MovementRules;
import com.Kayo.util.PieceColor;

public class Bishop extends Piece{
    public Bishop(PieceColor color) {
        super(color, new BishopMovementRules());
    }
}
