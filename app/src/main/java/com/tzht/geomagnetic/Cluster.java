package com.tzht.geomagnetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhupeng on 2018/3/16.
 */

public class Cluster {
    private int type;
    private float centroid;
    private final List<Signal> signals;

    public Cluster() {
        signals = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCentroid() {
        return centroid;
    }

    public void setCentroid(float centroid) {
        this.centroid = centroid;
    }

    public void add(Signal signal) {
        signals.add(signal);
    }

    public boolean contains(Signal signal) {
        return signals.contains(signal);
    }

    public int size() {
        return signals.size();
    }

    public void clear() {
        signals.clear();
    }

    public List<Signal> get() {
        return signals;
    }
}
