package com.example.digitalstethoscope.animation.animationwiththread;

import android.graphics.Color;

public class ColorArray {
    private int WIDTH;
    private int HEIGHT;
    private int currentColumn;
    private int[] colorMapping;

    public ColorArray(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        currentColumn = 0;
        this.colorMapping = new int[WIDTH * HEIGHT];
        this.initColor(Color.BLACK);
    }

    public int[] getColor() {
        return colorMapping;
    }

    public int[] castInt() {
        int[] cast = new int[colorMapping.length];
        for (int i = 0; i < colorMapping.length; i++) {
            cast[i] = (int) colorMapping[i];
        }
        return cast;
    }

    /*
     * public void add(float[] newColor){ for (int i=0;i<newColor.length;i++){
     * 
     * } }
     */
    public void insert(int[] columnArray) {
        columnArray = java.util.Arrays.copyOf(columnArray, HEIGHT);
        int row = 0;
        for (int i = 0; i < colorMapping.length; i++) {
            if (i % WIDTH == currentColumn) {
                colorMapping[i] = columnArray[row++];
            }
        }
        currentColumn++;
        if (currentColumn == WIDTH) {
            currentColumn = 0;
        }
    }

    public void initColor(int color) {
        for (int i = 0; i < colorMapping.length; i++) {
            colorMapping[i] = color;
        }
    }

    public int setRGB(int logValue) {
        int red = 0;
        if (inRange(20, 0, logValue)) {
            red = (20 - logValue) * 5;
            return red;
        }
        return 0;
    }

    public boolean inRange(int high, int low, int logValue) {
        // int [] range = {-20,0,20,40,60,80,100};
        if (logValue >= low && logValue <= high) {
            return true;
        }
        return false;
    }
}
