package com.Kayo.model;

import com.Kayo.util.PieceColor;

public abstract class Piece {

    protected PieceColor color;

    public Piece(PieceColor color){
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    }
}
