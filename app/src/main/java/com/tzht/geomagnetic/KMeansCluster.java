package com.tzht.geomagnetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        Signal first = origin.get(0);
        Signal last = origin.get(origin.size() - 1);
        clusters.get(0).setCentroid(first.signal);
        clusters.get(1).setCentroid(last.signal);

//        Random r = new Random();
//        int roll = 0;
//        while (centroid.size() < K) {
//            roll = r.nextInt(origin.size());
//            centroid.add(origin.get(roll));
//        }
    }

    public void cluster(List<Signal> data, float base, float threshold) {
        this.origin = data;
        normalize();
        chooseCentroid();

        do {
            clusters.get(0).clear();
            clusters.get(1).clear();
            for (Signal signal : origin) {
                float firstDis = Math.abs(signal.signal - clusters.get(0).getCentroid());
                float secondDis = Math.abs(signal.signal - clusters.get(1).getCentroid());
                if (firstDis < secondDis) {
                    clusters.get(0).add(signal);
                } else {
                    clusters.get(1).add(signal);
                }
            }

            recalculateCentroid();
        } while (isCentroidChanged());

        classify(base, threshold);
    }

    private void classify(float base, float threshold) {
        for (Cluster cluster : clusters) {
            if (Math.abs(cluster.getCentroid() - base) < threshold) {
                cluster.setType(0);
            } else {
                cluster.setType(1);
            }
        }
    }

    private void recalculateCentroid() {
        for (int i = 0; i < clusters.size(); i++) {
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
        for (int i = 0; i < clusters.size(); i++) {
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