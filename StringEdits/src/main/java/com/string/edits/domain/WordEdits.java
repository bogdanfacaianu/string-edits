package com.string.edits.domain;

import java.util.Objects;

public class WordEdits {

    private EditType editType;
    private int editIndex;
    private char foundCharacter;
    private char potentialSolution;
    private int transpositionIndex;

    public WordEdits(EditType editType, int editIndex, char foundCharacter, char potentialSolution) {
        this.editType = editType;
        this.editIndex = editIndex;
        this.foundCharacter = foundCharacter;
        this.potentialSolution = potentialSolution;
    }

    public WordEdits(EditType editType, int editIndex, char potentialSolution) {
        this.editType = editType;
        this.editIndex = editIndex;
        this.potentialSolution = potentialSolution;
    }

    public WordEdits(EditType editType, int editIndex, char foundCharacter, char potentialSolution, int transpositionIndex) {
        this.editType = editType;
        this.editIndex = editIndex;
        this.foundCharacter = foundCharacter;
        this.potentialSolution = potentialSolution;
        this.transpositionIndex = transpositionIndex;
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

    public int getTranspositionIndex() {
        return transpositionIndex;
    }

    public void setTranspositionIndex(int transpositionIndex) {
        this.transpositionIndex = transpositionIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordEdits)) {
            return false;
        }
        WordEdits wordEdits = (WordEdits) o;
        return editIndex == wordEdits.editIndex &&
            foundCharacter == wordEdits.foundCharacter &&
            potentialSolution == wordEdits.potentialSolution &&
            transpositionIndex == wordEdits.transpositionIndex &&
            editType == wordEdits.editType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(editType, editIndex, foundCharacter, potentialSolution, transpositionIndex);
    }

    @Override
    public String toString() {
        return "WordEdits{" +
            "editType=" + editType +
            ", editIndex=" + editIndex +
            ", foundCharacter=" + foundCharacter +
            ", potentialSolution=" + potentialSolution +
            ", transpositionIndex=" + transpositionIndex +
            '}';
    }
}
