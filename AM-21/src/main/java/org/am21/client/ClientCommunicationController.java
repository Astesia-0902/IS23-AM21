package org.am21.client;

import org.am21.networkRMI.IClientCallBack;

public class ClientCommunicationController {

    public boolean joinGame(int matchID){

        return false;
    }


    public boolean checkPlayerActionPhase()  {
        return false;
    }

    public boolean logIn(String username, IClientCallBack clientCallBack){
        return false;
    }
    public boolean createMatch(int playerNum){
        return false;
    }


    public boolean selectCell(int row, int col){
        return false;
    }

    public boolean confirmSelection(){
        return false;
    }

    public boolean insertInColumn(int colNum){
        return false;
    }

    public boolean deselectCards() {
        return false;
    }

    public boolean sortHand(int pos1, int pos2){
        return false;
    }

    public boolean leaveMatch(){
        return false;
    }

    public boolean exitGame() {
        return false;
    }

    public String getVirtualView() {
        return "";
    }

    public boolean registerCallBack(IClientCallBack callBack){
        return false;
    }

    public boolean sendChatMessage(String message){
        return false;
    }

    public boolean sendPlayerMessage(String message,String receiver){
        return false;
    }

    public void printOnlinePlayers(){

    }

    public void printMatchList() {
    }

    public boolean endTurn() {
        return false;
    }
    //TODO:
    public void openChat() {

    }

    public boolean changeMatchSeats(int newMaxSeats) {
        return false;
    }

    public boolean changeInsertLimit(int newLimit) {
        return false;
    }

}
