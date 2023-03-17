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
    //Pubblico dato che il punteggio puo essere visto da tutti durante la partita
    public Shelf myShelf;
    private final PersonalGoalCard myPersonalGoal;
    private boolean isChairMan;

    public int playerSeat;
    //posto del player(per ordine antiorario dei turni)

    public List<ItemTileCard> playerHand;
    //per memorizzare le carte(1-3) selezionate dal player






    public Player(String name,int seat, PersonalGoalCard playerPersonalGoal) {
        myShelf = new Shelf();  //eventualmente associare un id della shelf
        this.name = name;
        this.myPersonalGoal = playerPersonalGoal;
        this.playerSeat = seat;
        this.playerHand = new ArrayList<>();
        this.isChairMan = false; //default, se cambia verrà deciso all'inizio del match
        this.playerScore = 0;
    }
    //Costruttore per inizializzare il player con i suoi dati

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


    public void gainScoringToken(ScoringTokenCard score){
        this.playerScore = score.getScoreValue();
    }
    //Player get ScoringToken

    public void addGoalPoints(PersonalGoalCard myGoal){
        this.playerScore += myGoal.calculatePoints(myGoal.tilesMatches);
    }
    //Player riceve punti dal Personal Goal Card

    public void setAsChairMan(){
        this.isChairMan = true;
    }
    //metodo per impostare il player come chairman(primo ad iniziare)

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
    //aggiungo Item alla mano del player

    public void removeItemFromHand(ItemTileCard item){
        playerHand.remove(item);
    }
    //rimuovo item dalla mano del player

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
