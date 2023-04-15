package org.am21.view;

public interface View {
    //String chooseView();

    void askLogin();
    void askAction();

    void askCreateGame();
    int askMaxSeats();
    void askJoinGame();
    void askLeaveGame();


    void showCommonGoals(String username);
    void showPersonalGoal(String username);
    void showCurrentPlayer(String username);
//
//    void askPlayerMove();
//
//    void showBoard();
//    void askSelection();
//    void askDeselection();
//    void askInsertion();
//    boolean askSort();
//    void askIndex(int item1, int item2);
//    void askColumn(int numColumn);
//
//    void askNextMove();
//
//

}
