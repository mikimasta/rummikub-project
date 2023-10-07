package com.rummikub.game;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Group {

    private List<Tile> group;

    private List<Tile> addGroup(List<Tile> group, Tile t){
        if(group.size() == 0){
            group.add(t);
        }
        else if(group.size() != 0){
            for(int i=0; i<group.size(); i++){
                if(checkColor(group.get(i),t)){
                    if(checkNumber(group.get(i),t)){
                        group.add(t);
                    }
                }
            }
        }
        
        return group;
    }

    private boolean checkColor(Tile t1, Tile t2){
        if(t1.getColor()!=t2.getColor()){
            return true;
        }
        else 
        return false;
    }


    private boolean checkNumber(Tile t1, Tile t2){
        if(t1.getNumber()==t2.getNumber()){
            return true;
        }
        else 
        return false;
    }


    private List<Tile> getGroup(){
        return group;  
    }



    /* 
    private List<Tile> setGroup3(Tile t1, Tile t2, Tile t3){
        if(checkColor3(t1,t2,t3)){
            if(checkNumber3(t1,t2,t3)){
                group.add(t1);
                group.add(t2);
                group.add(t3);
            }
        }
        return group;
    }

    private boolean checkColor3(Tile t1, Tile t2, Tile t3){
        if(t1.getColor!=t2.getColor && t1.getColor!=t3.getColor){
            return true;
        }
        else 
        return false;
    }

    private boolean checkNumber3(Tile t1, Tile t2, Tile t3){
        if(t1.getNumber==t2.getNumber && t1.getNumber==t3.getNumber){
            return true;
        }
        else 
        return false;
    }
    */





}
