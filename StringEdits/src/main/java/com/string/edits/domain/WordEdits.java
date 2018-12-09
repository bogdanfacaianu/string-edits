package com.string.edits.domain;

import java.util.Objects;

public class WordEdits {

    private EditType editType;
    private int editIndex;
    // applicable for all but deletion
    private char potentialSolution;


    public WordEdits(EditType editType, int editIndex) {
        this.editType = editType;
        this.editIndex = editIndex;
    }

    public WordEdits(EditType editType, int editIndex, char potentialSolution) {
        this.editType = editType;
        this.editIndex = editIndex;
        this.potentialSolution = potentialSolution;
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
            potentialSolution == wordEdits.potentialSolution &&
            editType == wordEdits.editType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(editType, editIndex, potentialSolution);
    }

    @Override
    public String toString() {
        return "WordEdits{" +
            "editType=" + editType +
            ", editIndex=" + editIndex +
            ", potentialSolution=" + potentialSolution +
            '}';
    }
}
