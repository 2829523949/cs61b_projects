package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

import static utils.FileUtils.writeFile;

public class Character {
    public TETile[][]map;
    public int x;
    public int y;
    public Random random;
    public int score;
    public int mistake;
    public boolean gameover;
    public String file;

    public Character(TETile[][] map, int x, int y,Random random,String file){
        this.map=map;
        this.x=x;
        this.y=y;
        this.random=random;
        this.score=0;
        this.mistake=0;
        this.gameover=false;
        this.file=file;
    }/**用random来找出一个floor作为初始位置**/
    public void Move(){
        if(StdDraw.hasNextKeyTyped()){
            char x=StdDraw.nextKeyTyped();
            TETile NextPlace= Tileset.NOTHING;
            int XMove=0;
            int YMove=0;
            if(x=='q'){
                StringBuilder helper=new StringBuilder();
                helper.append(score).append(" ").append(mistake).append("\n");
                /**StringBuilder的第一行描述分数和失误，后面描述Map**/
                for(int i=0;i<50;i++){
                    for(int j=0;j<50;j++){
                        if(map[j][i]==Tileset.FLOWER){
                            helper.append(1);
                        }
                        if(map[j][i]==Tileset.GRASS){
                            helper.append(2);
                        }
                        if(map[j][i]==Tileset.AVATAR){
                            helper.append(3);
                        }
                        if(map[j][i]==Tileset.NOTHING){
                            helper.append(4);
                        }
                        if(map[j][i]==Tileset.FLOOR){
                            helper.append(5);
                        }
                        if(map[j][i]==Tileset.WALL){
                            helper.append(6);
                        }
                    }
                    helper.append("\n");
                }/**以行来进行分割，从第一行开始。花1草2人3空4地5墙6**/
                writeFile(file, helper.toString());
                gameover=true;
            }/**这是处理暂停游戏的情况**/
            if(x=='a'){
                NextPlace = map[this.x - 1][this.y];
                XMove=-1;
            }
            if(x=='d'){
                NextPlace = map[this.x + 1][this.y];
                XMove=1;
            }
            if(x=='w'){
                NextPlace = map[this.x][this.y + 1];
                YMove=1;
            }
            if(x=='s'){
                NextPlace = map[this.x][this.y - 1];
                YMove=-1;
            }
            if(NextPlace==Tileset.FLOOR){
                map[this.x][this.y]=Tileset.FLOOR;
                this.x=this.x+XMove;
                this.y=this.y+YMove;
                map[this.x][this.y]=Tileset.AVATAR;
                TERenderer ter=new TERenderer();
                ter.renderFrame(map);
            }/**这里需要加上Treasures的功能**/
            if(NextPlace==Tileset.FLOWER){
                map[this.x][this.y]=Tileset.FLOOR;
                this.x=this.x+XMove;
                this.y=this.y+YMove;
                map[this.x][this.y]=Tileset.AVATAR;
                World.CreateTreasures(map,random,1);
                this.score=this.score+100;
                TERenderer ter=new TERenderer();
                ter.renderFrame(map);
            }
            if(NextPlace==Tileset.WALL){
                this.mistake=this.mistake+1;
            }
        }
    }
    public void RenderScore(){
        StdDraw.setPenColor(StdDraw.WHITE);
        String ShowScore="score:"+score;
        StdDraw.text(4,46,ShowScore);
        StdDraw.show();
    }
    public void RenderMistake(){
        StdDraw.setPenColor(StdDraw.WHITE);
        String ShowMistake="mistake:"+mistake;
        StdDraw.text(45,46,ShowMistake);
        StdDraw.show();
    }
    public boolean GameOver(){
        if(gameover==true){
            for(int i=0;i<50;i++){
                for(int j=0;j<50;j++){
                    this.map[i][j]=Tileset.NOTHING;
                }
            }
            TERenderer ter=new TERenderer();
            ter.renderFrame(map);
            StdDraw.setPenColor(StdDraw.WHITE);
            String ShowScore="Suspend Now";
            StdDraw.text(24,25,ShowScore);
            StdDraw.show();
            return true;
        }
        if(score>=10000){
            for(int i=0;i<50;i++){
                for(int j=0;j<50;j++){
                    this.map[i][j]=Tileset.NOTHING;
                }
            }
            TERenderer ter=new TERenderer();
            ter.renderFrame(map);
            StdDraw.setPenColor(StdDraw.WHITE);
            String ShowScore="You Win!!!";
            StdDraw.text(20,25,ShowScore);
            StdDraw.show();
            writeFile(file,new String());
            return true;
        }
        else if(mistake>=10){
            for(int i=0;i<50;i++){
                for(int j=0;j<50;j++){
                    this.map[i][j]=Tileset.NOTHING;
                }
            }
            TERenderer ter=new TERenderer();
            ter.renderFrame(map);
            StdDraw.setPenColor(StdDraw.WHITE);
            String ShowScore="You Fail!!!";
            StdDraw.text(24,25,ShowScore);
            StdDraw.show();
            writeFile(file,new String());
            return true;
        }
        else{
            return false;
        }
    }
}
