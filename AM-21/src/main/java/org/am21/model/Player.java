package org.am21.model;

import org.am21.model.items.Card.ItemTileCard;
import org.am21.model.items.Card.PersonalGoalCard;
import org.am21.model.items.Card.ScoringTokenCard;
import org.am21.model.items.Shelf;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private final String name;
    public int playerScore;
    //A player score is visible by all during a game
    public Shelf myShelf;
    private final PersonalGoalCard myPersonalGoal;
    private boolean isChairMan;
    public int playerSeat;
    //player seat number used to determine playing order (counterclockwise)

    public List<ItemTileCard> playerHand;
    //memorize 1 to 3 cards selected by player during a turn



    public Player(String name,int seat, PersonalGoalCard playerPersonalGoal) {
        myShelf = new Shelf();  //eventualmente associare un id della shelf
        this.name = name;
        this.myPersonalGoal = playerPersonalGoal;
        this.playerSeat = seat;
        this.playerHand = new ArrayList<>();
        this.isChairMan = false; //default, it may change at the beginning of the game
        this.playerScore = 0;
    }
    //Constructor for player's data initialization

    public String getName() {
        return name;
    }

    public int getPlayerScore(){
        return playerScore;
    }

    public boolean getIsChairMan(){
        return isChairMan;
    }

    public PersonalGoalCard getMyPersonalGoal() {
        return myPersonalGoal;
    }

    public void getScoringToken(ScoringTokenCard score){
        this.playerScore += score.getScoreValue();
    }
    //Player obtain scoringToken => add points

    public void addGoalPoints(PersonalGoalCard myGoal){
        this.playerScore += myGoal.calculatePoints(myGoal.tilesMatches);
    }
    //Player receive points from PersonalGoal

    public void setAsChairMan(){
        this.isChairMan = true;
    }
    //Setting first player to start

    public List<List<ItemTileCard>> getBookShelf(){
        return myShelf.bookshelfGrid();
        //ritorna la lista degli elem della bookshelf del player(non realizzato)

    }

    public boolean insertTiles(int colNum,int numTiles){

        if(numTiles>0 && numTiles<4 && myShelf.slotAvailable(colNum)>=numTiles){
            //Se num di slot disponibili nella colonna colNum è >= dei Tiles da inserire allora ok
            for(int i=0;i<numTiles;i++){
                playerHand.get(i).addItemToShelf(colNum);
                playerHand.remove(i);   //???(Ken)
            }


            //
            return true;
        }else{
            return false;
        }

    }
    //restituisce false se l'inserimento in una colonna è fallito, causato da mancanza di slot

    public void addItemToHand(ItemTileCard item){
        playerHand.add(item);

    }
    //add Item to Player's Hand

    public void removeItemFromHand(ItemTileCard item){
        playerHand.remove(item);
    }
    //remove Item from Player's Hand

    public void changeHandOrder(int pos1,int pos2){
        // Ordine crescente (la prima carta è index-0 e l'ultima è potenzialmente index-2)
        ItemTileCard tmp = playerHand.get(pos1);
        playerHand.set(pos1,playerHand.get(pos2));
        playerHand.set(pos2,tmp);

    }
    /*
    Cambio ordine delle carte selezionate
    !!!!Rivedere il sistema di controllo dei valori che siano inferiori al numero di carte selezionate
    */
}
