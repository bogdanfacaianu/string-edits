package com.string.edits;

import com.string.edits.persistence.algorithm.StringDistanceAlgorithm;

public class HelloWorld {

    public static String getHello(String name) {
        return "Hello " + name;
    }

    public static int getDistanceBetween(String source, String target) {
        return StringDistanceAlgorithm.computeDistance(source, target);
    }
}
