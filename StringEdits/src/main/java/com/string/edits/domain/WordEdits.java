package com.string.edits.domain;

import java.util.Objects;

public class WordEdits {

    private EditType editType;
    private int editIndex;

    private char foundCharacter;
    // applicable only for substitution or transposition
    private char potentialSolution;

    public WordEdits(EditType editType, int editIndex, char foundCharacter, char potentialSolution) {
        this.editType = editType;
        this.editIndex = editIndex;
        this.foundCharacter = foundCharacter;
        this.potentialSolution = potentialSolution;
    }

    public WordEdits(EditType editType, int editIndex, char foundCharacter) {
        this.editType = editType;
        this.editIndex = editIndex;
        this.foundCharacter = foundCharacter;
    }

    public EditType getEditType() {
        return editType;
    }

    public void setEditType(EditType editType) {
        this.editType = editType;
    }

    public int getEditIndex() {
        return editIndex;
    }

    public void setEditIndex(int editIndex) {
        this.editIndex = editIndex;
    }

    public char getPotentialSolution() {
        return potentialSolution;
    }

    public void setPotentialSolution(char potentialSolution) {
        this.potentialSolution = potentialSolution;
    }

    public char getFoundCharacter() {
        return foundCharacter;
    }

    public void setFoundCharacter(char foundCharacter) {
        this.foundCharacter = foundCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WordEdits wordEdits = (WordEdits) o;
        return editIndex == wordEdits.editIndex &&
            foundCharacter == wordEdits.foundCharacter&&
            potentialSolution == wordEdits.potentialSolution &&
            editType == wordEdits.editType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(editType, editIndex, foundCharacter, potentialSolution);
    }

    @Override
    public String toString() {
        return "WordEdits{" +
            "editType=" + editType +
            ", foundCharacter=" + foundCharacter +
            ", potentialSolution=" + potentialSolution +
            ", at editIndex=" + editIndex +
            '}';
    }
}
