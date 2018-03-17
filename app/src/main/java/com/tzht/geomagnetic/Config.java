package com.tzht.geomagnetic;

/**
 * Created by zhupeng on 2018/3/5.
 */

public class Config {
    private static final int TH_ENV = 10;  //环境影响阈值
    private static final int TH_NER = 80;  //附件车辆影响阈值
    private static final int TH_CAR = 200; //停车影响阈值

    private float beta = 0.05f;
    private int windowSize = 16;
    private long samplingInterval = 200;
    private long detectionInterval = 200;
    private float thresholdOfZ = 20;
    private float thresholdOfY = 5;
    private int comingCheckCount = 15;

    public Config() {
    }

    public float getBeta() {
        return beta;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public long getSamplingInterval() {
        return samplingInterval;
    }

    public void setSamplingInterval(long samplingInterval) {
        this.samplingInterval = samplingInterval;
    }

    public long getDetectionInterval() {
        return detectionInterval;
    }

    public void setDetectionInterval(long detectionInterval) {
        this.detectionInterval = detectionInterval;
    }

    public float getThresholdOfZ() {
        return thresholdOfZ;
    }

    public void setThresholdOfZ(float thresholdOfZ) {
        this.thresholdOfZ = thresholdOfZ;
    }

    public float getThresholdOfY() {
        return thresholdOfY;
    }

    public void setThresholdOfY(float thresholdOfY) {
        this.thresholdOfY = thresholdOfY;
    }

    public int getComingCheckCount() {
        return comingCheckCount;
    }

    public void setComingCheckCount(int comingCheckCount) {
        this.comingCheckCount = comingCheckCount;
    }
}
