package com.xyleme.bravais.utils;

public class RandomizationUtil {

    /**
     * Gets random number between specified boundaries.
     *
     * @param min - number which introduces lower boundary
     * @param max - number which introduces higher boundary
     * @return {@code int}
     */
    public static int getRandomNumber(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }
}