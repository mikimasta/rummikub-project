package com.rummikub.game;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Run {
    
    private List<Tile> group;

    private List<Tile> addRun(List<Tile> group, Tile t){
        if(group.size() == 0){
            group.add(t);
        }

        if(group.size() == 1){
            if(checkColor(group.get(1),t)){
                if(group.get(1).getNumber()+1 == t){
                    group.addLast(t);
                }

                if(group.get(1).getNumber()-1 == t){
                    group.addFirst(t);
                }
            }
        }

        if(group.size() == 2){
            if(checkColor(group.getFirst(),t) && checkColor(group.getLast(),t)){
                if(group.getFirst().getNumber()-1 == t && group.getFirst().getNumber() > 1){
                    group.addFirst(t);
                }

                if(group.getLast().getNumber()+1 == t && group.getLast().getNumber() < 13){
                    group.addLast(t);
                }
            }
        }

        if(group.size() > 2 && group.size() < 13){
            if(checkColor(group.getFirst(),t)){

                // 1. Add consecutive numbers： 2345, add 1 or 6
                if(group.size() > 2 && group.size() < 4 && group.getFirst().getNumber()-1 == t && group.getFirst().getNumber() > 1){
                    group.addFirst(t);
                }

                if(group.size() > 2 && group.size() < 4 && group.getLast().getNumber()+1 == t && group.getLast().getNumber() < 13){
                    group.addLast(t);
                }

                //2. The number of tile of consecutive numbers more than 5，can add a same tlie in middle 
                // But the tile number is different of first two and last two tiles
                // For example： 23456，can add 4: 234,456. 234567，can add 4 or 5: 234, 4567
                if(group.size() >= 4 && group.size() < 12 && group.getFirst().getNumber()-1 == t && group.getFirst().getNumber() > 1){
                    group.addFirst(t);
                }

                if(group.size() >= 4 && group.size() < 12 && group.getLast().getNumber()+1 == t && group.getLast().getNumber() < 13){
                    group.addLast(t);
                }

                if(group.size() >= 4 && group.size() <= 12){

                    List<Tile> f = group.sublist(0,1);
                    List<tile> l = group.subList(group.size()-2, group.size());

                    for(int i=0, i<l.size(), i++){
                        if(t.getNumber() != f.get(i).getNumber() && t.getNumber() != l.get(i).getNumber()){
                            for(int j=0, j<group.size(), j++){
                                if(t.getNumber() == group.get(j).getNumber()){
                                    group.add(j+1,t);
                                }
                            }
                        }
                        
                    }
                }

                //3. If the first tile is 1, add tiles to the back. 
                // If the last tile is 13, add tiles to the front.
                // If first tile is 1, and last tile is 13, the list tile is from 1 to 13.
                // We can add the first two and the last two different tile
                if(group.getFirst().getNumber()==1 && group.getLast().getNumber()+1 == t.getNumber()){
                    group.add(t);
                }

                if(group.getLast().getNumber()==13 && group.getFirst().getNumber()-1 == t.getNumber()){
                    group.addFirst(t);
                }

                if(group.getFirst().getNumber()==1 && group.getLast().getNumber()==13){
                    List<Tile> f1 = group.sublist(0,1);
                    List<tile> l1 = group.subList(group.size()-2, group.size());
                    for(int k=0, k<l1.size(), k++){
                        if(t.getNumber() != f1.get(k).getNumber() && t.getNumber() != l1.get(k).getNumber()){
                            for(int z=0, z<group.size(), z++){
                                if(t.getNumber() == group.get(z).getNumber()){
                                    group.add(z+1,t);
                                }
                            }
                        }
                        
                    }
                }
                

            }


        }

        
        return group;
    }



    private boolean checkColor(Tile t1, Tile t2){
        if(t1.getColor()==t2.getColor()){
            return true;
        }
        else 
        return false;
    }



}
