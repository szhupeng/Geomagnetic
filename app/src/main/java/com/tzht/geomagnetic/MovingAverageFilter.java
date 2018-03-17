package com.tzht.geomagnetic;

/**
 * 对信号进行滑动均值滤波
 * Created by zhupeng on 2018/3/13.
 */

public class MovingAverageFilter implements Filter {

    private static final int WINDOW = 10;

    private int windowSize;
    private final float[] buffer;
    private boolean full = false;
    private int index = 0;

    public MovingAverageFilter(int size) {
        this.windowSize = size;
        if (size <= 0) {
            this.windowSize = WINDOW;
        }

        this.buffer = new float[windowSize];
    }

    @Override
    public float filter(float value) {
        float sum = 0;
        buffer[index++] = value;
        for (int i = 0; i < windowSize; i++) {
            sum += buffer[i];
        }

        if (!full && index < windowSize) {
            return sum / index;
        } else {
            if (windowSize == index) {
                full = true;
                index = 0;
            }
            return sum / windowSize;
        }
    }
}
