package com.tzht.geomagnetic;

import android.support.annotation.NonNull;

/**
 * Created by zhupeng on 2018/3/12.
 */

public class Signal implements Comparable<Signal> {
    public int id;
    public float signal;
    public float uniform;
    public int digit;

    public Signal(int id, float signal) {
        this.id = id;
        this.signal = signal;
    }

    @Override
    public int compareTo(@NonNull Signal o) {
        return Integer.signum(Float.compare(Math.abs(this.signal), Math.abs(o.signal)));
    }
}
