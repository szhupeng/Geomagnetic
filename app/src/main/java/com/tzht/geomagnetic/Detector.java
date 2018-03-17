package com.tzht.geomagnetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupeng on 2018/3/6.
 */

public class Detector {
    private Config config;

    private Filter filter;
    private final List<Signal> signals = new ArrayList<>();
    private float baseLine;

    private int counter = 0;

    private final KMeansCluster cluster;

    private Detector(Config config) {
        if (config != null) {
            this.config = config;
        } else {
            this.config = new Config();
        }

        filter = new MovingAverageFilter(this.config.getWindowSize());
        cluster = new KMeansCluster();

        StateMachine.switchTo(StateMachine.State.S1);
    }

    public static Detector prepare(Config config) {
        return new Detector(config);
    }

    public void handle(float axis) {
        if (StateMachine.isState(StateMachine.State.S1)) {
            if (signals.size() < 50) {
                signals.add(new Signal(signals.size(), filter.filter(axis)));
                return;
            }

            initBaseLine();
        } else {
            checkSignal(filter.filter(axis));
        }
    }

    /**
     * 初始化x轴，y轴，z轴基线
     */
    private void initBaseLine() {
        final int size = signals.size();
        float sum = 0;
        for (int i = 0; i < size; i++) {
            sum += signals.get(i).signal;
        }

        baseLine = sum / size;

        StateMachine.switchTo(StateMachine.State.S2);
    }

    /**
     * 调整基线
     *
     * @param axis
     * @return
     */
    private void adaptBaseLine(float axis) {
        final float beta = config.getBeta();
        baseLine = (1 - beta) * baseLine + beta * axis;
    }

    private void checkSignal(float axis) {
        Signal signal = new Signal(signals.size(), axis);
        signals.remove(0);
        signals.add(signal);
        cluster.cluster(signals, baseLine, config.getThresholdOfZAxisIncoming());
        if (1 == cluster.type(signal)) {
            if (StateMachine.isState(StateMachine.State.S2)) {
                counter = 0;
                StateMachine.switchTo(StateMachine.State.S3);
            } else if (StateMachine.isState(StateMachine.State.S3)) {
                if (counter > 20) {
                    counter = 0;
                    StateMachine.switchTo(StateMachine.State.S5);
                } else {
                    counter++;
                }
            } else if (StateMachine.isState(StateMachine.State.S4)) {
                counter = 0;
                StateMachine.switchTo(StateMachine.State.S3);
            }
        } else {
            if (StateMachine.isState(StateMachine.State.S2)) {
                adaptBaseLine(axis);
            } else if (StateMachine.isState(StateMachine.State.S3)) {
                counter = 0;
                StateMachine.switchTo(StateMachine.State.S4);
            } else if (StateMachine.isState(StateMachine.State.S4)) {
                if (counter > 16) {
                    StateMachine.switchTo(StateMachine.State.S2);
                } else {
                    counter++;
                }
            } else if (StateMachine.isState(StateMachine.State.S5)) {
                if (counter > 16) {
                    StateMachine.switchTo(StateMachine.State.S2);
                } else {
                    counter++;
                }
            }
        }
    }

    public boolean isVehicleExists() {
        return StateMachine.isState(StateMachine.State.S5);
    }
}
