package com.tzht.geomagnetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by zhupeng on 2018/3/12.
 */

public class KMeansCluster {
    private static final int K = 2;//将要分成的类别个数

    private List<Signal> origin;//待分类的原始值
    private List<Cluster> clusters;//聚类的结果

    public KMeansCluster() {
        clusters = new ArrayList<>(K);
        for (int i = 0; i < K; i++) {
            clusters.add(new Cluster());
        }
    }

    public int type(Signal signal) {
        for (Cluster cluster : clusters) {
            if (cluster.contains(signal)) {
                return cluster.getType();
            }
        }

        return 0;
    }

    public Cluster get(int index) {
        return clusters.get(index % K);
    }

    private void chooseCentroid() {
        final int size = origin.size();
        final int seg = size / K;
        Random r = new Random();
        int roll = 0;
        for (int i = 0; i < K; i++) {
            roll = r.nextInt(seg) + i * seg;
            clusters.get(i).setCentroid(origin.get(roll).signal);
        }
    }

    public void cluster(List<Signal> data, float base, float threshold) {
        this.origin = data;
        normalize();
        chooseCentroid();

        do {
            for (Cluster cluster : clusters) {
                cluster.clear();
            }

            for (Signal signal : origin) {
                Cluster min = clusters.get(0);
                Cluster temp;
                for (int i = 1; i < K; i++) {
                    temp = clusters.get(i);
                    if (Math.abs(signal.signal - temp.getCentroid()) < Math.abs(signal.signal - min.getCentroid())) {
                        min = temp;
                    }
                }
                min.add(signal);
            }

            recalculateCentroid();
        } while (isCentroidChanged());

        classify(base, threshold);
    }

    private void classify(float base, float threshold) {
        List<Signal> signals;
        for (Cluster cluster : clusters) {
            signals = cluster.get();
            final int size = signals.size();
            if (0 == size) continue;

            float min = Math.abs(signals.get(0).signal - base);
            float max = min;
            Signal temp;
            for (int i = 1; i < size; i++) {
                temp = signals.get(i);
                float dis = Math.abs(temp.signal - base);
                min = Math.min(min, dis);
                max = Math.max(max, dis);
            }

            if (max < threshold) {
                cluster.setType(0);
            }

            if (min > threshold) {
                cluster.setType(1);
            }
        }
    }

    private void recalculateCentroid() {
        for (int i = 0; i < K; i++) {
            List<Signal> signals = clusters.get(i).get();
            float sum = 0;
            int size = signals.size();
            for (int j = 0; j < size; j++) {
                sum += signals.get(j).signal;
            }
            clusters.get(i).setCentroid(sum / size);
        }
    }

    private boolean isCentroidChanged() {
        for (int i = 0; i < K; i++) {
            List<Signal> signals = clusters.get(i).get();
            float sum = 0;
            int size = signals.size();
            for (int j = 0; j < size; j++) {
                sum += signals.get(j).signal;
            }

            if (Float.compare(Math.abs(clusters.get(i).getCentroid() - sum / size), 0.01f) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 规格化
     */
    private void normalize() {
        Signal min = Collections.min(origin);
        Signal max = Collections.max(origin);
        for (Signal s : origin) {
            s.uniform = (s.signal - min.signal) / (max.signal - min.signal);
        }
    }
}