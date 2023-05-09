package com.Kayo.model.Piece;

import com.Kayo.model.Board;
import com.Kayo.model.movements.MovementRules;
import com.Kayo.util.PieceColor;

public abstract class Piece {
    protected PieceColor color;
    protected MovementRules movementRules;
    protected boolean hasMoved = false;

    public Piece(PieceColor color, MovementRules movementRules){
        this.color = color;
        this.movementRules = movementRules;
    }

    public PieceColor getColor() {
        return color;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public MovementRules getMovementRules(){
        return movementRules;
    }

    public void hasMoved(){
        hasMoved = true;
    }
}
