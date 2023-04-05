package org.am21.model.enumer;

public enum Mx {

    Neutral("None"),
    HandLimit("Shelf > Cannot pick more item"),
    NoCell("Board[!] > Empty cell. Try again"),
    NoOrtho("Board > Not Orthogonal"),
    SelectWin(""),
    Success("Match > Success"),
    Retry("Match > Try again"),
    SelFail("Match > Selection failed"),
    WrongPhase("Match > Move not allowed in this Phase"),
    ColSlotF("Shelf[!] > Not enough space in this column"),
    InsertWin(""),
    InsFail("Match > Insertion failed"),
    WrongPlayer("Match > Not your turn");




    private final String mex;

    Mx(String mex) {
        this.mex = mex;
    }
    public static void show(Mx in){

        System.out.println(in);

    }


}
