package com.Kayo.model.chass.movements;

import com.Kayo.model.chass.Board;

public abstract class MovementRules {

    public abstract boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn);
}
