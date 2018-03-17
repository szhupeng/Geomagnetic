package com.tzht.geomagnetic;

/**
 * Created by zhupeng on 2018/3/5.
 */

public class Config {
    private float beta = 0.05f;
    private int windowSize = 16;
    private long samplingInterval = 200;
    private long detectionInterval = 200;
    private float thresholdOfZAxisIncoming = 20;
    private float thresholdOfYAxisOutgoing = 5;
    private float thresholdOfZAxisOutgoing = 20;
    private int comingCheckCount = 15;

    private static final int TH_ENV = 10;  //环境影响阈值
    private static final int TH_NER = 80;  //附件车辆影响阈值
    private static final int TH_CAR = 200; //停车影响阈值

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

    public float getThresholdOfZAxisIncoming() {
        return thresholdOfZAxisIncoming;
    }

    public void setThresholdOfZAxisIncoming(float thresholdOfZAxisIncoming) {
        this.thresholdOfZAxisIncoming = thresholdOfZAxisIncoming;
    }

    public float getThresholdOfYAxisOutgoing() {
        return thresholdOfYAxisOutgoing;
    }

    public void setThresholdOfYAxisOutgoing(float thresholdOfYAxisOutgoing) {
        this.thresholdOfYAxisOutgoing = thresholdOfYAxisOutgoing;
    }

    public float getThresholdOfZAxisOutgoing() {
        return thresholdOfZAxisOutgoing;
    }

    public void setThresholdOfZAxisOutgoing(float thresholdOfZAxisOutgoing) {
        this.thresholdOfZAxisOutgoing = thresholdOfZAxisOutgoing;
    }

    public int getComingCheckCount() {
        return comingCheckCount;
    }

    public void setComingCheckCount(int comingCheckCount) {
        this.comingCheckCount = comingCheckCount;
    }
}
