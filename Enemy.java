package com.learn.tankgame03;

import java.util.Vector;

/**
 * @author Arc
 * @version v1.0
 */
public class Enemy extends Tank implements Runnable{
    private final int TYPE = 1;
    Vector<Enemy> enemyTanks = new Vector<>();
    @Override
    public void run() {
        while (true){
            if(isLive && shots.size() == 0){
                shotEnemyTank();
            }
            switch (getDirect()){
                case 0://上
                    for (int i = 0;i < 30;i++) {
                        if (!isTouchEnemyTank()) {
                            moveUp();
                        }
                        try {//休眠
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1://右
                    for (int i = 0;i < 30;i++) {
                        if (!isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {//休眠
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2://下
                    for (int i = 0;i < 30;i++) {
                        if (!isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {//休眠
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3://左
                    for (int i = 0;i < 30;i++) {
                        if (!isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {//休眠
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            if(!isLive)break;//退出线程
            try {//休眠
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //随机改变坦克方向0-3
            setDirect((int)(Math.random() * 4));
        }
    }

    public int getTYPE() {
        return TYPE;
    }

    public Enemy(int x, int y) {
        super(x, y);

    }

    //获取所有坦克的引用
    public void setEnemyTanks(Vector<Enemy> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //判断是否重叠
    public boolean isTouchEnemyTank(){
        switch (this.getDirect()){
            case 0://上
                //让当前敌人坦克和其它所有坦克比较
                for(int i = 0;i < enemyTanks.size();i++){
                    //取出每一架坦克
                    Enemy enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if(enemyTank != this){
                        //如果敌人坦克是上/下
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            //判断右上和左上两个点是否重叠
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                        }else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){//如果敌人坦克是左/右
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
            case 1://右
                for(int i = 0;i < enemyTanks.size();i++){
                    //取出每一架坦克
                    Enemy enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if(enemyTank != this){
                        //如果敌人坦克是上/下
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            //判断右上和右下两个点是否重叠
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60){
                                return true;
                            }
                        }else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){//如果敌人坦克是左/右
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }

                break;
            case 2://下
                for(int i = 0;i < enemyTanks.size();i++){
                    //取出每一架坦克
                    Enemy enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if(enemyTank != this){
                        //如果敌人坦克是上/下
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60<= enemyTank.getY() + 60){
                                return true;
                            }
                            //判断右上和左上两个点是否重叠
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60>= enemyTank.getY()
                                    && this.getY() + 60<= enemyTank.getY() + 60){
                                return true;
                            }
                        }else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){//如果敌人坦克是左/右
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60>= enemyTank.getY()
                                    && this.getY() + 60<= enemyTank.getY() + 40){
                                return true;
                            }
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60>= enemyTank.getY()
                                    && this.getY() + 60<= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
            case 3://左
                for(int i = 0;i < enemyTanks.size();i++){
                    //取出每一架坦克
                    Enemy enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if(enemyTank != this){
                        //如果敌人坦克是上/下
                        if(enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            //判断右上和左上两个点是否重叠
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 40
                                    && this.getY() + 40>= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 60){
                                return true;
                            }
                        }else if(enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){//如果敌人坦克是左/右
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
        }
        return false;
    }


}
