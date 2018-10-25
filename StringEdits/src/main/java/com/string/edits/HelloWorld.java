package com.string.edits;

import com.string.edits.persistence.algorithm.StringDistanceAlgorithm;

public class HelloWorld {

    public static int getDistanceBetween(String source, String target) {
        return StringDistanceAlgorithm.computeDistance(source, target);
    }
}
