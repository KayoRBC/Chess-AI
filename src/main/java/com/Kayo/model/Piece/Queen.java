package com.Kayo.model.Piece;

import com.Kayo.model.movements.MovementRules;
import com.Kayo.model.movements.QueenMovementRules;
import com.Kayo.util.PieceColor;

public class Queen extends Piece{
    public Queen(PieceColor color) {
        super(color, new QueenMovementRules());
    }
}
