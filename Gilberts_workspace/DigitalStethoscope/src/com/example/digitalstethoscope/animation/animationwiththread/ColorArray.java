package com.example.digitalstethoscope.animation.animationwiththread;

import android.graphics.Color;

public class ColorArray {
    private int WIDTH;
    private int HEIGHT;
    private int maximum;
    private int hueDegree;
    private int currentColumn;
    private int[] colorMapping;

    public ColorArray(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        currentColumn = 0;
        this.colorMapping = new int[WIDTH * HEIGHT];
        this.initColor(Color.BLACK);
        this.maximum = -120; // Color mapping from 0 to -120
        this.hueDegree = 240; // Java Hue degree color from red to darkest blue
    }

    public int[] getColor() {
        return colorMapping;
    }

    public int[] castInt() {
        int[] cast = new int[colorMapping.length];
        for (int i = 0; i < colorMapping.length; i++) {
            cast[i] = (int) colorMapping[i];
            // System.out.println("cast is: " + cast[i]);
        }
        return cast;
    }

    public void insert(double[] columnArray) {
        columnArray = java.util.Arrays.copyOf(columnArray, HEIGHT);
        int row = 0;
        for (int i = 0; i < colorMapping.length; i++) {
            if (i % WIDTH == currentColumn) {
                if (row < columnArray.length) {
                    colorMapping[i] = setHsv(columnArray[columnArray.length - 1
                            - row]);
                    row++;
                }
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

    public double normalization(double logValue) {
        return (logValue / maximum);
    }

    public int setHsv(double logValue) {
        // System.out.println("logvalue is: " + logValue);
        // float [] test = {40f,1f,1f};
        double hue = 0;
        float[] hsv = { 0f, 1f, 1f }; // hsv[0] initialized to 0. S and V are
                                      // always 1f.
        if (logValue == 0) {
            hsv[0] = 120f;
            hsv[1] = 0f;
            hsv[2] = 0f;
        } else {
            double normalized = normalization(logValue); // normalized = .333
                                                         // for
                                                         // 40)
            if (normalized < .5) {
                hue = normalized * hueDegree * .25f; // 20
                hue += ((normalized * 12) - 1) * 13; // check hue value if color
                                                     // mapping is wrong. hue =
                                                     // 20+(.33333*12)-1
                // System.out.println("Hue is: " + hue); //should be 59
            } else {
                hue = normalized * hueDegree * .7f + 76;
            }
            hsv[0] = (float) hue;
            // System.out.println("Hue is: " + hue);
            // System.out.println("hsv to color is: " + Color.HSVToColor(1,hsv)
            // );
        }
        return Color.HSVToColor(1, hsv);
    }
}
