package com.learn.tankgame03;

import java.util.Vector;

/**
 * @author Arc
 * @version v1.0
 */
public class Tank {
    Vector<Shot> shots = new Vector<>();
    private int x;//坦克坐标
    private int y;
    boolean isLive = true;
    private int speed = 1;//移动速度倍速

    public void shotEnemyTank(){
        Shot shot = null;
        switch (getDirect()){
            case 0:
                shot = new Shot(getX() + 20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX() + 60,getY() + 20,1);
                break;
            case 2:
                shot = new Shot(getX() + 20,getY() + 60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY() + 20,3);
                break;
        }
        //将子弹对象存入集合中
        shots.add(shot);
        //启动子弹线程
        new Thread(shot).start();
    }
    public boolean checkRange(){
        if((direct == 0 && y > 0 )|| (direct == 1 && x + 80  < 1000) || (direct == 2 && y + 100  < 750) || (direct == 3 && x >0)){
            return true;
        }
        return false;
    }

    public void moveUp(){//用于封装坦克朝某个方向走时的移动
        if(checkRange()){
            y -= speed;
        }
    }

    public void moveDown(){
        if(checkRange()){
            y += speed;
        }
    }

    public void moveRight(){
        if (checkRange()){
            x += speed;
        }
    }

    public void moveLeft(){
        if (checkRange()) {
            x -= speed;
        }
    }

    private int direct;//方向
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getDirect() {
        return direct;
    }//方向

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
