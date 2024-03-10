package Lives;

import java.util.ArrayList;
import java.util.Random;

public class Animal {

    public AnimalSpeices speices;
    public int move;
    public String gender;
    public Boolean isLive;
    public int xLocation;
    public int yLocation;
    public int range;

    public void process(){
        Random r = new Random();
        int newposition=r.nextInt(4);
        if(newposition==2&&yLocation>=(500-move)){
            newposition=r.nextInt(3);
            if(newposition==2)
                newposition=3;
        }else if(newposition==3&&yLocation<=(move-1)){
            newposition=r.nextInt(3);
        }
        if (newposition==0&&xLocation>=(500-move)){
            newposition=r.nextInt(3);
            if(newposition==0)
                newposition=3;
        }else if(newposition==1&&xLocation<=(move-1)){
            newposition=r.nextInt(3);
            if (newposition==1)
                newposition=3;
        }
        switch (newposition){
            case 0:
                xLocation +=move;
                break;
            case 1:
                xLocation -=move;
                break;
            case 2:
                yLocation +=move;
                break;
            case 3:
                yLocation -=move;
                break;
        }
    }


}
