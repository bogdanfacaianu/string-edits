package com.string.edits.domain;

public enum EditType {
    INSERTION, // horizontal move
    DELETETION, // vertical move
    SUBSTITUTION, // diagonal move (if equal then no change)
    TRANSPOSITION,// sort of double substitution here on check
    MOVE // the case for insert and delete on same character
}
