package com.string.edits.persistence.algorithm;

/*****************************************************************************************************************
*   This algorithm's source can be found at:                                                                     *
*   https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm *
*****************************************************************************************************************/

public class StringDistanceAlgorithm {

    public static int computeDistance(String source, String target) {
        int matrix[][]; // matrix
        int sourceLength; // length of source
        int targetLength; // length of target
        int sourceIndex; // iterates through source
        int targetIndex; // iterates through target
        char sourceCharacterAtIndex; // ith character of s
        char targetCharacterAtIndex; // jth character of t
        int cost; // cost

        // Step 1
        sourceLength = source.length();
        targetLength = target.length();
        if (sourceLength == 0) {
            return targetLength;
        }
        if (targetLength == 0) {
            return sourceLength;
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

        // Step 7
        return matrix[sourceLength][targetLength];
    }

    private static int minimum(int deletionCost, int insertionCost, int substitutionCost) {
        return Math.min(Math.min(deletionCost,insertionCost), substitutionCost);
    }
}
