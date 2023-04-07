package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;
import org.am21.utilities.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class CommonGoal4Group extends CommonGoal {

    private boolean[][] visited;
    private int nGroup=0;
    public CommonGoal4Group(int numPlayer) {
        super("CommonGoal4Group",numPlayer);
    }

    /**
     * Scan the shelf to find 3 groups of 4 tiles of the same color
     * @param s
     * @return
     */

    /*public boolean checkGoal(Shelf shelf) {
        boolean[][] visited = new boolean[6][5];
        int count = 0;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                //Once we find a tile != 0 and not visited, we check if it's the upper left tile of a 1x4 group
                if(shelf.getMatrix()[i][j] != null && !visited[i][j]) {
                    boolean result = true;
                    for(int k = 0; k < 4; k++) {
                        result = result && process(shelf,i+k,j,visited);
                    }
                    if(result) {
                        count++;
                        //If we find 3 groups of 4 tiles, we return true
                        if(count >= 3) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean process(Shelf s, int i, int j, boolean[][] visited) {
        //Check if the tile is in the shelf and not visited
        if(i < 0 || i >= 6 || j < 0 || j >= 5 || visited[i][j] || shelf.getMatrix()[i][j] == null)
            return false;

        //mark the tile as visited
        visited[i][j] = true;
        return true;
    }*/

    @Override
    public boolean checkGoal(Shelf s){
        nGroup=0;
        visited = new boolean[Shelf.sRow][Shelf.sColumn];


        for(int i=0;i<Shelf.sRow;i++){
            for(int j=0;j<Shelf.sColumn;j++){


                if(s.isOccupied(i,j)&&!visited[i][j]&&findGroup(i,j,s)){
                    nGroup++;
                    if(nGroup==4){
                        return true;
                    }

                }


            }
        }




        return false;
    }

    private boolean findGroup(int startX,int startY,Shelf s){
        String itemRef = s.getItemType(startX,startY);
        boolean newMember = false;
        Coordinates temp = null;

        List<Coordinates> group = new ArrayList<>();
        //default: first member
        group.add(new Coordinates(startX,startY));
        visited[startX][startY]=true;

        do{

            for(Coordinates member: group) {
                temp = nextMember(member.x, member.y, s, itemRef);
                if (temp != null && !group.contains(temp)) {
                    newMember = true;
                    break;
                }
                //if, after a group iteration, nextMember is not found and nGroup is still <4,
                // it means the group is not valid

                }
            if (newMember) {
                group.add(temp);
                newMember=false;
            }

        }while (temp!=null);

        if(group.size()>=4)
            return true;
        else
            return false;
    }

    private Coordinates nextMember(int x,int y,Shelf s,String itemRef){
        Coordinates tmp;
        if(x-1>=0&&!visited[x-1][y]&&s.isOccupied(x-1,y)&&s.getItemType(x-1,y).equals(itemRef)){
            visited[x-1][y]=true;
            tmp = new Coordinates(x-1,y);
        }else if(y-1>=0&&!visited[x][y-1]&&s.isOccupied(x,y-1)&&s.getItemType(x,y-1).equals(itemRef)){
            visited[x][y-1]=true;
            tmp = new Coordinates(x,y-1);
        } else if(x+1<Shelf.sRow&&!visited[x+1][y]&&s.isOccupied(x+1,y)&&s.getItemType(x+1,y).equals(itemRef)) {
            visited[x+1][y]=true;
            tmp = new Coordinates(x+1,y);
        }else if(y+1<Shelf.sColumn&&!visited[x][y+1]&&s.isOccupied(x,y+1)&&s.getItemType(x,y+1).equals(itemRef)){
            visited[x][y+1]=true;
            tmp = new Coordinates(x,y+1);
        }else {
            tmp=null;
        }
        return tmp;
    }
}
