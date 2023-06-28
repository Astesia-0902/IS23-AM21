package org.am21.model.enumer;

public enum ServerMessage {
    //Connection phase
    Connection_Ok("Connection successful"),
    Connection_No("Connection failed"),

    //Login phase
    Login_Ok(SC.CYAN+"Login successful."+ SC.RST),
    Login_No(SC.RED_B+"Access denied.\nThis username is already taken, please enter a new one..."+SC.RST),
    ListP("List of online players: "),

    //Create match phase
    PExceed("Exceeded player number limit. Try again"),
    CreateM_Ok(SC.CYAN+"Match successfully created"+ SC.RST),
    CreateM_No("Failed to create a new Match"),


    //Join match phase
    FindM_Ok(SC.CYAN+"Match found"+ SC.RST),
    FindM_No("The room does not exists. Please try again"),
    FullM("This room is full"),
    PExists_No("The player does not exist in any room. Try to create a new one"),

    //Match initialization phase
    BB(SC.RED_B +"The match is about to start. Building game board..."+ SC.RST),
    BB_Ok(SC.CYAN+"Board successfully built"+ SC.RST),
    BB_No("Board build failed"),

    //Game phase (Selection phase)
    Selection_Ok(SC.CYAN+"Selection successful"+ SC.RST),
    Selection_No("Selection Failed. Maybe the item is not available for selection."),
    Cell_Illegal("You selected an illegal cell"),
    ReSelected("You have already selected this cell"),
    No_Free("The cell was not selectable. Pick an item which has a free side"),
    No_Orthogonal("The cell was not selectable. Pick an item which is adjacent and in line with the other selected items"),
    Hand_Full("You reached the limit of items you could pick"),


    //Game phase (Deselection phase)
    DeSel_Ok("Deselection successful"),
    Clear_Ok("All selected items cleared"),
    DeSel_Null(SC.RED+"You did not select any card yet. Deselection is not necessary"+SC.RST),

    //Game phase (Insertion phase),
    InsFail("Insertion failed"),
    ColNo("This column has not enough slot available. Choose another one."),


    //Game phase (Sort phase)
    Sort_No("Not enough cards in your hand"),
    Sort_Index_NO("Index out of border"),
    Sort_Ok("Order changed"),


    //Game phase (Game over phase)
    LastRound(SC.RED_B+"It's the Last Round"+SC.RST),
    GameOver("GAME OVER"),
    YouWon("Congratulation! The winner is "),
    CloseOrPause(SC.RED_B + "The match is closed or paused" + SC.RST),
    MatchPaused(SC.YELLOW_BB + "Match paused, waiting for other players to reconnect. If non one reconnect within 60s, the last active player will be the winner." + SC.RST),

    //Leave match phase
    UserLeave("User left the game"),


    //Exit game phase
    SUD("Saving user data"),
    GoodBye("Bye! See you again"),

    //Any phase
    Error("Error"),
    WrongPhase("Wrong phase. Cannot use this command."),
    HandEmpty("No item selected"),
    BagEmpty("Bag is empty"),

    //Not your turn
    NotYourTurn(SC.RED_B+"Wait. It's not your turn"+SC.RST);

    private final String replyMessage;

    ServerMessage(String replyMessage) {
        this.replyMessage=replyMessage;
    }

    public String value() {


        return this.replyMessage;
    }
}
