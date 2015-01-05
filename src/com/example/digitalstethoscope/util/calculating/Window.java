package com.example.digitalstethoscope.util.calculating;

public class Window {

    public enum WindowType {
        BLACKMAN, HANN, HAMMING, KAISER
    }

    private int length = 0;
    private double[] win;
    private double beta = 0.5;
    final private long[] fact = { 1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L,
            40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L,
            87178291200L, 1307674368000L, 20922789888000L, 355687428096000L,
            6402373705728000L, 121645100408832000L, 2432902008176640000L };

    public Window(int length) {
        this.length = length;
        createBlackMan();
    }

    public Window(int length, WindowType wintype) {
        this.length = length;
        switch (wintype) {
        case BLACKMAN:
            createBlackMan();
            break;
        case HANN:
            createHann();
            break;
        case HAMMING:
            createHamming();
            break;
        case KAISER:
            createKaiser();
            break;
        default:
            createBlackMan();
            break;
        }
    }

    public Window(int length, WindowType wintype, double beta) {
        this.length = length;
        this.beta = beta;
        createKaiser();
    }

    public int getLength() {
        return this.length;
    }

    public double getPoint(int index) {
        return win[index];
    }

    private void createBlackMan() {
        win = new double[length];
        for (int i = 0; i < win.length; i++) {
            win[i] = 0.42 - 0.5 * Math.cos(2 * Math.PI * i / (length - 1))
                    + 0.08 * Math.cos(4 * Math.PI * i / (length - 1));
        }
    }

    private void createHann() {
        win = new double[length];
        for (int i = 0; i < win.length; i++) {
            win[i] = 0.5 * (1 - Math.cos(2 * Math.PI * i / (length - 1)));
        }
    }

    private void createHamming() {
        win = new double[length];
        for (int i = 0; i < length; i++) {
            win[i] = 0.54 - 0.46 * (Math.cos(2 * Math.PI * i / (length - 1)));
        }
    }

    // Using this implementation
    // http://www.labbookpages.co.uk/audio/firWindowing.html#kaiser
    // Hard code kaiser order, M to 30
    private void createKaiser() {
        final int M = length;
        win = new double[length];
        for (int i = 0; i < length; i++) {
            // win[i] = besseli(beta * Math.sqrt(1 - Math.pow((((2.0 * i) / M) -
            // 1), 2.0))) / (besseli(beta));
            win[i] = besseli((2 * beta / M) * Math.sqrt(i * (M - i)))
                    / (besseli(beta));
        }
    }

    // Sum only up to i=20
    private double besseli(double x) {
        double accum = 0.0;
        for (int i = 0; i <= 20; i++) {
            accum += (Math.pow(x / 2, 2.0 * i)) / ((fact[i]) * (fact[i]));
        }
        return accum;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (double elem : win) {
            str.append(String.format("  %1.4f\n", elem));
        }
        return str.toString();
    }
}
