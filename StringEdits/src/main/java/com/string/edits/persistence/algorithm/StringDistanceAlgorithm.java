package com.string.edits.persistence.algorithm;

import static com.string.edits.domain.EditType.DELETETION;
import static com.string.edits.domain.EditType.INSERTION;
import static com.string.edits.domain.EditType.MOVE;
import static com.string.edits.domain.EditType.SUBSTITUTION;
import static com.string.edits.domain.EditType.TRANSPOSITION;

import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.WordEdits;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringDistanceAlgorithm {

    public static DistanceToWord computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];
        DistanceToWord dtw = new DistanceToWord(rhs.toString());

        for (int i = 0; i <= lhs.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= rhs.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= lhs.length(); i++) {
            for (int j = 1; j <= rhs.length(); j++) {
                distance[i][j] = minimum(
                    distance[i - 1][j] + 1,
                    distance[i][j - 1] + 1,
                    distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
            }
        }
        printMatrix(distance, lhs.length(), rhs.length(), lhs.toString(), rhs.toString());

        dtw.setDistance(distance[lhs.length()][rhs.length()]);
        List<WordEdits> wordEdits = addEdits(distance, lhs.length(), rhs.length(), lhs.toString(), rhs.toString());
        dtw.setEdits(wordEdits);
        System.out.println(dtw);
        return dtw;
    }

    private static int minimum(int deletionCost, int insertionCost, int substitutionCost) {
        return Math.min(Math.min(deletionCost, insertionCost), substitutionCost);
    }

    private static void printMatrix(int matrix[][], int sourceLength, int targetLength, String source, String target) {
        System.out.print(" ");
        for (int j = 0; j < targetLength; j++) {
            System.out.print(" " + target.charAt(j));
        }
        System.out.println();
        for (int i = 0; i < sourceLength; i++) {
            System.out.print("" + source.charAt(i) + " ");
            for (int j = 0; j < targetLength; j++) {
                System.out.print("" + matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static List<WordEdits> addEdits(int[][] distance, int sourceLength, int targetLength, String source, String target) {
        List<WordEdits> edits = new ArrayList<>();

        int i = sourceLength;
        int j = targetLength;

        while (i != 0 || j != 0) {
            char sourceCharacterAtIndex = (i > 0) ? source.charAt(i - 1) : Character.MIN_VALUE;
            char targetCharacterAtIndex = (j > 0) ? target.charAt(j - 1) : Character.MIN_VALUE;

            if (j <= 0) {
                while (i > 0) {
                    edits.add(new WordEdits(DELETETION, j, sourceCharacterAtIndex));
                    i--;
                    sourceCharacterAtIndex = (i > 0) ? source.charAt(i - 1) : Character.MIN_VALUE;
                }
                break;
            }

            if (i <= 0) {
                while (j > 0) {
                    edits.add(new WordEdits(INSERTION, i, targetCharacterAtIndex));
                    j--;
                    targetCharacterAtIndex = (j > 0) ? target.charAt(j - 1) : Character.MIN_VALUE;
                }
                break;
            }

            int currentCell = distance[i][j];
            int diagonalCell = distance[i - 1][j - 1];
            int leftCell = distance[i][j - 1];
            int aboveCell = distance[i - 1][j];

            int smallestOfAll3 = minimum(diagonalCell, leftCell, aboveCell);

            if (diagonalCell == smallestOfAll3 && (diagonalCell == currentCell - 1 || diagonalCell == currentCell)) {
                if (diagonalCell == currentCell - 1) {
                    edits.add(new WordEdits(SUBSTITUTION, i, sourceCharacterAtIndex, targetCharacterAtIndex));
                }

                i--;
                j--;
            } else if (leftCell <= diagonalCell && leftCell == currentCell - 1) {
                edits.add(new WordEdits(INSERTION, i, targetCharacterAtIndex));
                j--;
            } else {
                edits.add(new WordEdits(DELETETION, i, sourceCharacterAtIndex));
                i--;
            }
        }

        Collections.reverse(edits);
        mergeOperations(edits);
        return edits;
    }

    private static void mergeOperations(List<WordEdits> edits) {
        List<WordEdits> copyOfEdits = new ArrayList<>(edits);

        for (int i = 0; i < edits.size(); i++) {
            WordEdits edit = edits.get(i);
            for (int j = 0; j < copyOfEdits.size(); j++) {
                WordEdits we = copyOfEdits.get(j);
                if (we.getEditType() == SUBSTITUTION && edit.getEditType() == SUBSTITUTION) {
                    if (we.getFoundCharacter() == edit.getPotentialSolution() && we.getPotentialSolution() == edit.getFoundCharacter()) {
                        edits.add(new WordEdits(TRANSPOSITION, edit.getEditIndex(), edit.getFoundCharacter(), we.getFoundCharacter(), we.getEditIndex()));
                        edits.remove(i);
                        edits.remove(i<j ? j-1 : j);
                    }
                }

                if ((we.getEditType() == INSERTION && edit.getEditType() == DELETETION)
                    || (we.getEditType() == DELETETION && edit.getEditType() == INSERTION)) {
                    if (we.getPotentialSolution() == edit.getPotentialSolution()) {
                        edits.add(new WordEdits(MOVE, edit.getPotentialSolution(), edit.getEditIndex(), we.getEditIndex()));
                        edits.remove(i);
                        edits.remove(i<j ? j-1 : j);
                    }
                }
            }
        }
    }
}
