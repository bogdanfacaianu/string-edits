package com.string.edits.persistence.algorithm;

/****************************************************************************************************************
*   This algorithm's source can be found at:                                                                    *
*   https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm*
****************************************************************************************************************/

public class StringDistanceAlgorithm {

    public static int computeDistance(String source, String target) {
        int d[][]; // matrix
        int sourceLength; // length of s
        int targetLength; // length of t
        int sourceInterator; // iterates through s
        int targetIterator; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
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
        d = new int[sourceLength+1][targetLength+1];

        // Step 2
        for (sourceInterator = 0; sourceInterator <= sourceLength; sourceInterator++) {
            d[sourceInterator][0] = sourceInterator;
        }
        for (targetIterator = 0; targetIterator <= targetLength; targetIterator++) {
            d[0][targetIterator] = targetIterator;
        }

        // Step 3
        for (sourceInterator = 1; sourceInterator <= sourceLength; sourceInterator++) {
            s_i = source.charAt (sourceInterator - 1);

            // Step 4
            for (targetIterator = 1; targetIterator <= targetLength; targetIterator++) {
                t_j = target.charAt (targetIterator - 1);

                // Step 5
                if (s_i == t_j) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }

                // Step 6
                d[sourceInterator][targetIterator] = minimum(d[sourceInterator-1][targetIterator]+1, d[sourceInterator][targetIterator-1]+1, d[sourceInterator-1][targetIterator-1] + cost);
            }

        }

        // Step 7
        return d[sourceLength][targetLength];
    }

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a,b), c);
    }
}
