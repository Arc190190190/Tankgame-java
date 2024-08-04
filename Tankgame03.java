package com.learn.tankgame03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author Arc
 * @version v1.0
 */
public class Tankgame03 extends JFrame {
    MyPanel mp = null;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Tankgame03 version3 = new Tankgame03();
    }

    public  Tankgame03(){
        System.out.println("请输入选择 1：新游戏 2：继续上局");
        String key = sc.next();
        mp = new MyPanel(key);
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把画板添加到画框中
        this.addKeyListener(mp);//添加键盘监听器
        this.setSize(1300,750);//设置窗口大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出
        this.setVisible(true);//可视化
        //在Jframe中监听关闭窗口
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
