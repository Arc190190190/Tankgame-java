package com.learn.tankgame03;

import java.util.Vector;

/**
 * @author Arc
 * @version v1.0
 */
public class MyTank extends Tank {
    private final int TYPE = 0;//坦克类型



    public int getTYPE() {
        return TYPE;
    }

    public MyTank(int x, int y) {
        super(x, y);
    }
}
