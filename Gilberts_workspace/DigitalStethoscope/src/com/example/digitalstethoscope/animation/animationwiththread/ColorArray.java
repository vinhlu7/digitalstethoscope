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
        this.maximum = -120; //Color mapping from 0 to -120
        this.hueDegree = 240; //Java Hue degree color from red to darkest blue
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

    public void insert(int[] columnArray) {
        columnArray = java.util.Arrays.copyOf(columnArray, HEIGHT);
        int row = 0;
        for (int i = 0; i < colorMapping.length; i++) {
            if (i % WIDTH == currentColumn) {
                colorMapping[i] = setHsv(columnArray[row++]);
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
    
    public float normalization(float logValue){
    	return (logValue/maximum);
    }
    
    public int setHsv(float logValue){
    	float hue = 0;
    	float[] hsv = {0f,1f,1f}; //hsv[0] initialized to 0. S and V are always 1f.
    	float normalized = normalization(logValue);
    	if(normalized <= .5){
    		hue = normalized*hueDegree*.25f;
    		hue += ((normalized*10)-1)*13;
    	} else{ 
    		hue = normalized*hueDegree*.7f+76;
    	}
    	hsv[0]= hue;
    	return Color.HSVToColor(1,hsv);
    }
}
