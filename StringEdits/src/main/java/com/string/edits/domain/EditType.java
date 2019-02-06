package com.string.edits.domain;

public enum EditType {
    INSERT, // horizontal move
    DELETE, // vertical move
    REPLACE, // diagonal move (if equal then no change)
    TRANSPOSE // ?????
}
