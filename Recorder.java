package com.learn.tankgame03;

import java.io.*;
import java.util.Vector;

/**
 * @author Arc
 * @version v1.0
 */
@SuppressWarnings({"all"})
public class Recorder {
    //定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    //定义io对象，准备写数据到文件中
    private static BufferedReader br = null;
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    //定义一个node的集合
    private static Vector<Node> nodes = new Vector<>();
    private static Vector<Enemy> enemyTanks = new Vector<>();
    private static  String recordFile = "src\\myRecord.txt";

    //增加一个方法，用于读取文件，恢复信息
    public static Vector<Node> getNodesAndallEnemyTankNum(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，存入Node集合中
            String line = "";
            while((line = br.readLine()) != null){
                String xyd[] = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br!= null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return nodes;
    }

    //当游戏退出时，把 allEnemyTankNum 保存到recordFile中
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //根据情况保存敌人坦克
            if (!enemyTanks.isEmpty()) {
                for(int i = 0;i < enemyTanks.size();i++){
                    //取出敌人坦克
                    Enemy enemyTank = enemyTanks.get(i);
                    if(enemyTank.isLive){
                        String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                        //写入到文件中
                        bw.write(record + "\r\n");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    public static void addAllEnemyTankNum(){
        allEnemyTankNum++;
    }

    public static void setEnemyTanks(Vector<Enemy> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile() {
        return recordFile;
    }
}
