package com.learn.tankgame03;

import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Vector;

/**
 * @author Arc
 * @version v1.0
 */
@SuppressWarnings({"All"})
public class MyPanel extends JPanel implements KeyListener,Runnable{
    MyTank hero = null;
    Vector<Enemy> enemy = new Vector<>();//用线程安全的容器存放敌方坦克对象
    //定义一个容器存放炸弹
    Vector<Bomb> bombs = new Vector<>();
    Vector<Node> nodes = new Vector<>();
    int enemyTankSize = 3;
    //定义三张图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public MyPanel(String Key) {
        //判断文件是否存在，不存在就只能开新游戏
        File file1 = new File(Recorder.getRecordFile());
       if(file1.exists()){
           nodes = Recorder.getNodesAndallEnemyTankNum();
       }else{
           System.out.println("文件不存在，只能开启新游戏");
           Key = "1";
       }
        //定义一个存放Node对象的集合，恢复坦克的坐标和方向

        Recorder.setEnemyTanks(enemy);
        if (Key.equals("1")) {
            for (int i = 0; i < 3; i++) {
                //创建一个敌人坦克
                Enemy enemy1 = new Enemy(100 * (i + 1), 0);
                //把集合赋给它
                enemy1.setEnemyTanks(enemy);
                //设置方向
                enemy1.setDirect(2);
                //启动线程
                new Thread(enemy1).start();
                //给该坦克加一颗子弹
                Shot shot = new Shot(enemy1.getX() + 20, enemy1.getY() + 60,enemy1.getDirect());
                //加入该坦克的子弹容器中
                enemy1.shots.add(shot);
                //启动线程
                new Thread(shot).start();
                //把坦克 加入容器中
                enemy.add(enemy1);
            }
        }else if(Key.equals("2")){
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                //创建一个敌人坦克
                Enemy enemy1 = new Enemy(node.getX(),node.getY());
                //把集合赋给它
                enemy1.setEnemyTanks(enemy);
                //设置方向
                enemy1.setDirect(node.getDirect());
                //启动线程
                new Thread(enemy1).start();
                //给该坦克加一颗子弹
                Shot shot = new Shot(enemy1.getX() + 20, enemy1.getY() + 60,enemy1.getDirect());
                //加入该坦克的子弹容器中
                enemy1.shots.add(shot);
                //启动线程
                new Thread(shot).start();
                //把坦克 加入容器中
                enemy.add(enemy1);
            }
        }else {
            System.out.println("你的输入有误");
        }
        hero = new MyTank(100,500);
       hero.setSpeed(5);
       //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/bomb_3.gif"));
        //播放指定的音乐
        new AePlayWave("src\\111.wav").start();
    }
    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g){
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("您累积击毁敌方坦克",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //填充矩形
        g.fillRect(0,0,1000,750);
        showInfo(g);
       //判断我方坦克是否存活
        if(hero != null && hero.isLive) {
            //画出坦克-封装方法
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), hero.getTYPE());
        }
        //遍历子弹集合，画出玩家射击的子弹

        for (int i = 0;i < hero.shots.size();i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive != false){
               g.fill3DRect(shot.getX(),shot.getY(),5,5,false);
            }else {
                hero.shots.remove(shot);
            }
        }
        //如果bombs集合中有对象就画出
        for(int i = 0;i < bombs.size();i++){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据炸弹的生命值画出对应的图片，为了显示出停滞感
            if(bomb.life > 6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if(bomb.life > 3){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            }else {
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            //让这个炸弹生命值减少
            bomb.lifeDown();
            //如果生命值为0，删除
            bombs.remove(bomb);
        }


        for (int i = 0; i < enemy.size(); i++) {
            Enemy enemyTank = enemy.get(i);
            //当敌人坦克为死亡状态不画
            if(enemyTank.isLive == false ){
                enemy.remove(enemyTank);
                continue;
            }
            //画出敌人坦克
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), enemyTank.getTYPE());
            for (int j = 0;j < enemyTank.shots.size();j++){
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //绘制
                if(shot.isLive){
                    g.fill3DRect(shot.getX(), shot.getY(), 5,5,false);
                } else {
                    enemyTank.shots.remove(shot);
                }

            }
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //判断是否击中了敌人坦克
            for (int i = 0;i < hero.shots.size();i++) {
                Shot shot = hero.shots.get(i);
                if(shot != null && shot.isLive) {//子弹还存活
                    //遍历敌人的坦克
                    for(int j = 0;j < enemy.size();j++){
                        Enemy enemyTank = enemy.get(j);
                        hitTank(shot,enemyTank);
                    }
                }
            }
            //判断敌人坦克是否击中我方坦克
            for (int i = 0;i < enemy.size();i++) {
                Enemy enemyTank = enemy.get(i);
                for (int j = 0;j < enemyTank.shots.size();j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if(shot != null && shot.isLive && hero.isLive) {//子弹还存活
                        //判断我方的坦克
                        hitTank(shot,hero);
                    }
                }
            }

            this.repaint();
        }

    }

    /**
     *
     * @param x//坦克的左上角x坐标
     * @param y//左上角y坐标
     * @param g//画笔
     * @param direct 坦克方向
     * @param type 坦克类型
     */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        //根据不同类型坦克，设置不同颜色
        switch (type){
            case 0://玩家坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克方向，来绘制坦克
        switch (direct){
            case 0:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x + 30,y,10,60,false);
                g.fill3DRect(x + 10,y + 10,20,40,false);
                g.fillOval(x + 10 ,y + 20,20,20);
                g.drawLine(x + 20,y + 30,x + 20, y);
                break;
            case 1:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y + 30,60,10,false);
                g.fill3DRect(x + 10,y + 10,40,20,false);
                g.fillOval(x + 20 ,y + 10,20,20);
                g.drawLine(x + 30,y + 20,x + 60, y + 20);
                break;
            case 2:
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x + 30,y,10,60,false);
                g.fill3DRect(x + 10,y + 10,20,40,false);
                g.fillOval(x + 10 ,y + 20,20,20);
                g.drawLine(x + 20,y + 30,x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y + 30,60,10,false);
                g.fill3DRect(x + 10,y + 10,40,20,false);
                g.fillOval(x + 20 ,y + 10,20,20);
                g.drawLine(x + 30,y + 20,x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    public void hitTank(Shot s,Tank enemyTank){
        //判断s击中坦克
        switch (enemyTank.getDirect()){
            case 0://坦克向下和向上的碰撞
            case 2:
                if(s.getX() > enemyTank.getX() && s.getX() < enemyTank.getX() + 40
                 && s.getY() > enemyTank.getY() && s.getY() < enemyTank.getY() + 60){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //如果击毁敌方坦克，AllEnemyTankNum就++
                    if(enemyTank instanceof Enemy){
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://向左与向右
            case 3:
                if(s.getX() > enemyTank.getX() && s.getX() < enemyTank.getX() + 60
                        && s.getY() > enemyTank.getY() && s.getY() < enemyTank.getY() + 40){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    if(enemyTank instanceof Enemy){
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            hero.moveUp();
        } else if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(1);
            hero.moveRight();
        } else if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(2);
            hero.moveDown();
        } else if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(3);
            hero.moveLeft();
        } else if(e.getKeyCode() == KeyEvent.VK_J){
//            if(hero.shot == null || !hero.shot.isLive){
//                hero.shotEnemyTank();
//            }
            if(hero.shots.size() < 5){
                hero.shotEnemyTank();
            }
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
