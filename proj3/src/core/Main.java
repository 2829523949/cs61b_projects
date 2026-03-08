package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static utils.FileUtils.writeFile;

public class Main {
    private static final String SAVE_WORLD="src/save.txt";
    public static void main(String[] args) throws FileNotFoundException {
        File file=new File(SAVE_WORLD);
        if((!file.exists())||(file.length()==0)){
            graph HelperGraph=new graph();
            int seed=114514;
            Random random=new Random(seed);
            int RoomTime= random.nextInt(100,200);
            // build your own world!
            TETile[][]blank=World.StartUp();
            for(int i=1;i<RoomTime;i++){
                World.GenerateRooms(random,blank,HelperGraph);
            }
            World.GenerateWallways(random,blank,HelperGraph);
            World.Around(blank);
            World.Beautiful(blank);
            int[]OriginPlace=World.SelectOrigin(random,blank);
            int OriginX=OriginPlace[0];
            int OriginY=OriginPlace[1];
            Character character=new Character(blank,OriginX,OriginY,random,SAVE_WORLD);
            World.CreateTreasures(blank,random,5);
            while(!character.GameOver()){
                character.Move();
                character.RenderScore();
                character.RenderMistake();
            }
        }
        else{
            Random random=new Random();
            TETile[][]blank=World.StartUp();
            Scanner helper=new Scanner(file);
            int score=helper.nextInt();
            int mistake=helper.nextInt();
            helper.nextLine();
            int HangShu=0;
            while(helper.hasNextLine()){
                String hang=helper.nextLine();
                for(int i=0;i<50;i++){
                    char num=hang.charAt(i);
                    if(num=='1'){
                        blank[i][HangShu]= Tileset.FLOWER;
                    }
                    else if(num=='2'){
                        blank[i][HangShu]=Tileset.GRASS;
                    }
                    else if(num=='3'){
                        blank[i][HangShu]=Tileset.AVATAR;
                    }
                    else if(num=='4'){
                        blank[i][HangShu]=Tileset.NOTHING;
                    }
                    else if(num=='5'){
                        blank[i][HangShu]=Tileset.FLOOR;
                    }
                    else if(num=='6'){
                        blank[i][HangShu]=Tileset.WALL;
                    }
                }/**花1草2人3空4地5墙6**/
                HangShu++;
            }
            TERenderer ter=new TERenderer();
            ter.renderFrame(blank);
            int StartX=0;
            int Starty=0;
            for(int i=0;i<50;i++){
                for(int j=0;j<50;j++){
                    if(blank[i][j]==Tileset.AVATAR){
                        StartX=i;
                        Starty=j;
                    }
                }
            }
            Character character=new Character(blank,StartX,Starty,random,SAVE_WORLD);
            character.score=score;
            character.mistake=mistake;
            while(!character.GameOver()){
                character.Move();
                character.RenderScore();
                character.RenderMistake();
            }
        }
    }
}
