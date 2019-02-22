package com.string.edits.persistence.algorithm;

import com.string.edits.domain.DistanceToWord;
import com.string.edits.domain.EditType;
import com.string.edits.domain.WordEdits;
import java.util.ArrayList;
import java.util.List;

/*****************************************************************************************************************
*   This algorithm's source can be found at:                                                                     *
*   https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm *
*****************************************************************************************************************/

public class StringDistanceAlgorithm {

    public static DistanceToWord computeDistance(String source, String target) {
        int matrix[][]; // matrix
        int sourceLength; // length of source
        int targetLength; // length of target
        int sourceIndex; // iterates through source
        int targetIndex; // iterates through target
        char sourceCharacterAtIndex; // ith character of s
        char targetCharacterAtIndex; // jth character of t
        int cost; // cost

        DistanceToWord dtw = new DistanceToWord(target);

        // Step 1
        sourceLength = source.length();
        targetLength = target.length();
        if (sourceLength == 0) {
            dtw.setDistance(targetLength);
            return dtw;
        }
        if (targetLength == 0) {
            dtw.setDistance(sourceLength);
            return dtw;
        }
        matrix = new int[sourceLength+1][targetLength+1];

        // Step 2
        for (sourceIndex = 0; sourceIndex <= sourceLength; sourceIndex++) {
            matrix[sourceIndex][0] = sourceIndex;
        }
        for (targetIndex = 0; targetIndex <= targetLength; targetIndex++) {
            matrix[0][targetIndex] = targetIndex;
        }

        // Step 3
        for (sourceIndex = 1; sourceIndex <= sourceLength; sourceIndex++) {
            sourceCharacterAtIndex = source.charAt(sourceIndex - 1);

            // Step 4
            for (targetIndex = 1; targetIndex <= targetLength; targetIndex++) {
                targetCharacterAtIndex = target.charAt(targetIndex - 1);

                // Step 5
                if (sourceCharacterAtIndex == targetCharacterAtIndex) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }

                // Step 6
                matrix[sourceIndex][targetIndex] = minimum(
                        matrix[sourceIndex-1][targetIndex] + 1,
                        matrix[sourceIndex][targetIndex-1] + 1,
                        matrix[sourceIndex-1][targetIndex-1] + cost);
            }

        }
//        printMatrix(matrix, sourceLength, targetLength);

        dtw.setDistance(matrix[sourceLength][targetLength]);
        List<WordEdits> wordEdits = addEdits(matrix, sourceLength, targetLength, source, target);
        dtw.setEdits(wordEdits);

        // Step 7
        return dtw;
    }

    private static int minimum(int deletionCost, int insertionCost, int substitutionCost) {
        return Math.min(Math.min(deletionCost,insertionCost), substitutionCost);
    }

    private static void printMatrix(int matrix[][], int sourceLength, int targetLength) {
        for (int i = 0; i < targetLength; i++) {
            for (int j = 0; j < sourceLength; j++) {
                System.out.print("" + matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    private static List<WordEdits> addEdits(int[][] matrix, int sourceLength, int targetLength, String source, String target) {
        List<WordEdits> edits = new ArrayList<>();
        for (int i = targetLength - 1; i > 2; i--) {
            char targetCharacterAtIndex = target.charAt(i - 1);
            for (int j = sourceLength - 1; j > 2; j--) {
                char sourceCharacterAtIndex = source.charAt(j- 1);

                if (matrix[i - 1][j - 1] <= matrix[i-1][j] && matrix[i - 1][j - 1] <= matrix[i][j-1]) {
                    if (matrix[i - 1][j - 1] - matrix[i][j] <= 0) {
                        if (matrix[i - 1][j - 1] - matrix[i][j] == -1) {
                            edits.add(new WordEdits(EditType.SUBSTITUTION, i, sourceCharacterAtIndex, targetCharacterAtIndex));
                        }
                    }
                }
                if (matrix[i][j-1] <= matrix[i-1][j]) {
                    if ((matrix[i][j - 1] == matrix[i][j]) || (matrix[i][j] - matrix[i][j - 1] <= 0)) {
                        edits.add(new WordEdits(EditType.INSERTION, j - 1, sourceCharacterAtIndex, targetCharacterAtIndex));
                        j--;
                    } else {
                        edits.add(new WordEdits(EditType.DELETETION, j - 1, sourceCharacterAtIndex));
                        i--;
                    }
                }
            }
        }

        return edits;
    }

    public static void main(String[] args) {
        System.out.println("\n" + computeDistance("staficla", "stafida"));
    }
}
