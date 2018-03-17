package com.tzht.geomagnetic;

/**
 * 中值滤波
 * Created by zhupeng on 2018/3/14.
 */

public class MedianFilter implements Filter {

    private final float[] buffer;
    private final int length;

    public MedianFilter(int length) {
        if (length <= 0) {
            length = 16;
        }
        this.length = length;
        this.buffer = new float[length];
    }

    @Override
    public float filter(float value) {
        float temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (buffer[j] > buffer[j + 1]) {
                    temp = buffer[j];
                    buffer[j] = buffer[j + 1];
                    buffer[j + 1] = temp;
                }
            }
        }

        if ((length & 1) > 0) {
            temp = buffer[(length + 1) / 2];
        } else {
            temp = (buffer[length / 2] + buffer[length / 2 + 1]) / 2;
        }

        return temp;
    }
}
