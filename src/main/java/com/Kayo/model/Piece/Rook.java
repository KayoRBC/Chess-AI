package com.Kayo.model.Piece;

import com.Kayo.model.movements.MovementRules;
import com.Kayo.model.movements.RookMovementRules;
import com.Kayo.util.PieceColor;

public class Rook extends Piece{
    public Rook(PieceColor color) {
        super(color, new RookMovementRules());
    }
}
