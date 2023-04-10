package org.am21.model.Cards.CommonGoals;

import org.am21.model.Cards.CommonGoal;
import org.am21.model.items.Shelf;

/**
 * This class includes the common goal of 4 groups and common goal of 6 groups
 * User can define the number of groups and the number of tiles in each group by using the constructor
 */
public class CommonGoalGroup extends CommonGoal {

    //private boolean[][] visited;

    //public static int minMembers = 4;
    //public static int numGroup = 4;

    public int groupNum;
    public int groupSize;


    public CommonGoalGroup(int numPlayer, int groupNum, int groupSize){
        super("CommonGoal" + groupNum + "Group", numPlayer);
        this.groupNum = groupNum;
        this.groupSize = groupSize;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        boolean[][] visited = new boolean[Shelf.sRow][Shelf.sColumn];
        int count = 0;
        for (int i = 0; i < Shelf.sRow; i++) {
            for (int j = 0; j < Shelf.sColumn; j++) {
                if (shelf.getCell(i, j) == null || visited[i][j]) {
                    continue;
                }
                String itemType = shelf.getItemType(i, j);
                if (process(shelf, i, j, visited, 1, itemType)) {
                    count++;

                    if (count >= groupNum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method recursively checks if there is a group of tiles of the same type
     * Use this version if a continuous set of tiles is regarded as a group no matter how many tiles are in the group
     * @param shelf the shelf to check
     * @param i row index
     * @param j column index
     * @param visited a boolean matrix to keep track of visited tiles
     * @param depth the depth of the recursion
     * @param itemType the type of the item we are looking for
     * @return true if there is a group of tiles of the same type
     */
    private boolean process(Shelf shelf, int i, int j, boolean[][] visited, int depth, String itemType) {
        boolean flag = depth >= groupSize;

        visited[i][j] = true;

        //Check all 4 directions
        //If we find a tile of the same type, we recursively call process
        //check the conditions before calling process, building calls on stack is expensive
        if (i > 0 && !visited[i - 1][j] && shelf.getCell(i - 1, j) != null && shelf.getItemType(i - 1, j).equals(itemType)) {
            if (process(shelf, i - 1, j, visited, depth + 1, itemType)) {
                return true;
            }
        }

        if (i < Shelf.sRow - 1 && !visited[i + 1][j] && shelf.getCell(i + 1, j) != null && shelf.getItemType(i + 1, j).equals(itemType)) {
            if (process(shelf, i + 1, j, visited, depth + 1, itemType)) {
                return true;
            }
        }

        if (j > 0 && !visited[i][j - 1] && shelf.getCell(i, j - 1) != null && shelf.getItemType(i, j - 1).equals(itemType)) {
            if (process(shelf, i, j - 1, visited, depth + 1, itemType)) {
                return true;
            }
        }

        if (j < Shelf.sColumn - 1 && !visited[i][j + 1] && shelf.getCell(i, j + 1) != null && shelf.getItemType(i, j + 1).equals(itemType)) {
            if (process(shelf, i, j + 1, visited, depth + 1, itemType)) {
                return true;
            }
        }

        //reset visited if we don't find a group
        if(!flag) visited[i][j] = false;

        return flag;
    }

//    /**
//     * Use this version a continuous group of tiles is regarded as a group only if the number of tiles is smaller than or equal to groupSize
//     */
//    private boolean process(Shelf shelf, int i, int j, boolean[][] visited, int depth, String itemType) {
//        if (depth == groupSize) {
//            return true;
//        }
//
//        visited[i][j] = true;
//
//        //Check all 4 directions
//        //If we find a tile of the same type, we recursively call process
//        //check the conditions before calling process, building calls on stack is expensive
//        if (i > 0 && !visited[i - 1][j] && shelf.getCell(i - 1, j) != null && shelf.getItemType(i - 1, j).equals(itemType)) {
//            if (process(shelf, i - 1, j, visited, depth + 1, itemType)) {
//                return true;
//            }
//        }
//
//        if (i < Shelf.sRow - 1 && !visited[i + 1][j] && shelf.getCell(i + 1, j) != null && shelf.getItemType(i + 1, j).equals(itemType)) {
//            if (process(shelf, i + 1, j, visited, depth + 1, itemType)) {
//                return true;
//            }
//        }
//
//        if (j > 0 && !visited[i][j - 1] && shelf.getCell(i, j - 1) != null && shelf.getItemType(i, j - 1).equals(itemType)) {
//            if (process(shelf, i, j - 1, visited, depth + 1, itemType)) {
//                return true;
//            }
//        }
//
//        if (j < Shelf.sColumn - 1 && !visited[i][j + 1] && shelf.getCell(i, j + 1) != null && shelf.getItemType(i, j + 1).equals(itemType)) {
//            if (process(shelf, i, j + 1, visited, depth + 1, itemType)) {
//                return true;
//            }
//        }
//
//        //reset visited if we don't find a group
//        visited[i][j] = false;
//
//        return false;
//    }




//    @Override
//    public boolean checkGoal(Shelf s){
//        nGroup=0;
//        visited = new boolean[Shelf.sRow][Shelf.sColumn];
//
//        for(int i=0;i<Shelf.sRow;i++){
//            for(int j=0;j<Shelf.sColumn;j++){
//
//                if(s.isOccupied(i,j)&&!visited[i][j]&&findGroup(i,j,s)){
//                    nGroup++;
//                    if(nGroup== numGroup){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * @param startX
//     * @param startY
//     * @param s
//     * @return
//     */
//    private boolean findGroup(int startX,int startY,Shelf s){
//        String itemRef = s.getItemType(startX,startY);
//        boolean newMember = false;
//        Coordinates temp = null;
//
//        List<Coordinates> group = new ArrayList<>();
//        //default: first member
//        group.add(new Coordinates(startX,startY));
//        visited[startX][startY]=true;
//
//        do{
//
//            for(Coordinates member: group) {
//                temp = nextMember(member.x, member.y, s, itemRef);
//                if (temp != null && !group.contains(temp)) {
//                    newMember = true;
//                    break;
//                }
//                //if, after a group iteration, nextMember is not found and nGroup is still <4,
//                // it means the group is not valid
//
//                }
//            if (newMember) {
//                group.add(temp);
//                newMember=false;
//            }
//
//        }while (temp!=null);
//
//        if(group.size()>=minMembers)
//            return true;
//        else
//            return false;
//    }
//
//    /**
//     *
//     * @param x
//     * @param y
//     * @param s
//     * @param itemRef
//     * @return
//     */
//    private Coordinates nextMember(int x,int y,Shelf s,String itemRef){
//        Coordinates tmp;
//        if(x-1>=0&&!visited[x-1][y]&&s.isOccupied(x-1,y)&&s.getItemType(x-1,y).equals(itemRef)){
//            visited[x-1][y]=true;
//            tmp = new Coordinates(x-1,y);
//        }else if(y-1>=0&&!visited[x][y-1]&&s.isOccupied(x,y-1)&&s.getItemType(x,y-1).equals(itemRef)){
//            visited[x][y-1]=true;
//            tmp = new Coordinates(x,y-1);
//        } else if(x+1<Shelf.sRow&&!visited[x+1][y]&&s.isOccupied(x+1,y)&&s.getItemType(x+1,y).equals(itemRef)) {
//            visited[x+1][y]=true;
//            tmp = new Coordinates(x+1,y);
//        }else if(y+1<Shelf.sColumn&&!visited[x][y+1]&&s.isOccupied(x,y+1)&&s.getItemType(x,y+1).equals(itemRef)){
//            visited[x][y+1]=true;
//            tmp = new Coordinates(x,y+1);
//        }else {
//            tmp=null;
//        }
//        return tmp;
//    }
}


/*
 * Scan the shelf to find 3 groups of 4 tiles of the same color
 *
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
