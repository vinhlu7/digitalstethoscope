package com.example.digitalstethoscope.animation.animationwiththread;

import android.graphics.Color;

public class ColorArray {
    private int WIDTH;
    private int HEIGHT;
    private int maximum;
    private int hsvDegree;
    private int currentColumn;
    private int[] colorMapping;

    public ColorArray(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        currentColumn = 0;
        this.colorMapping = new int[WIDTH * HEIGHT];
        this.initColor(Color.BLACK);
        this.maximum = 100;
        this.hsvDegree = 240;
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
    
    public float normalization(float logValue){
    	return (logValue/maximum)*-1;
    }
    
    public float[] setHsv(float logValue){
    	float hue = 0;
    	float normalized = normalization(logValue);
    	if(normalized <= .5){
    		hue = normalized*hsvDegree*.25f;
    		hue += ((normalized*10)-1)*12;
    	} else{ 
    		hue = normalized*hsvDegree*.7f+80;
    	}
    	float[] hsv = {hue,1f,1f};
    	return hsv;
    }
}
