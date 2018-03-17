package com.tzht.geomagnetic;

/**
 * Created by zhupeng on 2018/3/5.
 */

class StateMachine {

    private static final StateMachine INSTANCE = new StateMachine();

    private State mCurrentState;

    public enum State {
        S1("初始化基线"),
        S2("调整基线并检测"),
        S3("出现进车信号"),
        S4("出现离车信号"),
        S5("进车");

        private final String desc;

        State(String desc) {
            this.desc = desc;
        }
    }

    private StateMachine() {
    }

    public static void switchTo(State state) {
        INSTANCE.mCurrentState = state;
    }

    public static boolean isState(State state) {
        return INSTANCE.mCurrentState == state;
    }
}
