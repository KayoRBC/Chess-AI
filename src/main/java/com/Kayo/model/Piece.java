package com.Kayo.model;

import com.Kayo.model.movements.MovementRules;
import com.Kayo.util.PieceColor;

public abstract class Piece {

    protected PieceColor color;
    protected MovementRules movementRules;

    public Piece(PieceColor color, MovementRules movementRules){
        this.color = color;
        this.movementRules = movementRules;
    }

    public PieceColor getColor() {
        return color;
    }
}
