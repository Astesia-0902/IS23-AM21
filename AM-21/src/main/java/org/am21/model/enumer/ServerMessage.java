package org.am21.model.enumer;

public enum ServerMessage {
    //Connection phase
    Connection_Ok("Server > Connection successful"),
    Connection_No("Server > Connection failed"),

    //Login phase
    Login_Ok(SC.CYAN+"Server > Login successful."+ SC.RST),
    Login_No(SC.RED_B+"Server > Access denied.\nThis username is already taken, please enter a new one..."+SC.RST),
    ListP("Server > List of online players" +
            ": "),

    //Create match phase
    PExists("Server > The player already exists in match.\nIn order to create a new match, you need to abandon the current one.\nDo you wish to continue?"),
    PExceed("Server > Exceeded player number limit. Try again"),
    CreateM_Ok(SC.CYAN+"Server > Match successfully created"+ SC.RST),
    CreateM_No("Server > Failed to create a new Match"),


    //Join match phase
    FindM_Ok(SC.CYAN+"Server > Match found"+ SC.RST),
    FindM_No("Server > The room does not exists. Please try again"),
    FullM("Server > This room is full"),
    PExists_No("Server > The player does not exist in any room. Try to create a new one"),

    //Match initialization phase
    BB(SC.RED_B +"\nServer[!]> The match is about to start. Building game board..."+ SC.RST),
    BB_Ok(SC.CYAN+"Server > Board successfully built"+ SC.RST),
    BB_No("Server > Board build failed"),

    //Game phase (Selection phase)
    Selection_Ok(SC.CYAN+"Server > Selection successful"+ SC.RST),
    Selection_No("Server > Selection failed"),
    Cell_Empty("Server > The cell is empty"),
    Cell_Illegal("Server > You selected an illegal cell"),
    ReSelected("Server > You have already selected this cell"),
    No_Free("Server > The cell was not selectable. Pick an item which has a free side"),
    No_Orthogonal("Server > The cell was not selectable. Pick an item which is adjacent and in line with the other selected items"),
    Hand_Full("Server > You reached the limit of items you could pick"),


    //Game phase (Deselection phase)

    DeSel_Ok("Server > Deselection successful"),
    Clear_Ok("Server > All selected items cleared"),
    DeSel_Null(SC.RED+"Server > You did not select any card yet. Deselection is not necessary"+SC.RST),

    //Game phase (Insertion phase)
    WhichCellWriteTheCoordinates(""),
    YouCanNotInsertCardsIfYouDidNotSelectAny(""),
    SelectColumnToInsert(""),
    ColumnNotAvailable(""),
    ColNo("Server > This column has not enough slot available. Choose another one."),


    //Game phase (Sort phase)
    Sort_No("Server > Not enough cards in your hand"),
    Sort_Index_NO("Server > Index out of border"),
    Sort_Ok("Server > Order changed"),


    //Game phase (Show object phase)

    //Game phase (Goal achievement phase)
    YouMatchedAnItem(""),
    GoalAchieved(""),
    Points(""),

    //Chat phase
    WriteMessage(""),
    ChatHistory(""),


    //Help Command phase

    //Game phase (Game over phase)
    LastRound(SC.RED_B+"Server > It's the Last Round"+SC.RST),
    GameOver("Server > GAME OVER"),
    YouWon("Server > Congratulation! The winner is "),

    //Leave match phase
    UserLeave("Server > User left the game"),


    //Exit game phase
    SUD("Server > Saving user data"),
    GoodBye("Server > Bye! See you again"),

    //Any phase
    Error("Error"),
    WrongPhase("Wrong phase. Cannot use this command."),
    HandEmpty("No item selected"),

    //NOt your turn
    NotYourTurn(SC.RED_B+"Server > Wait. It's not your turn"+SC.RST);

    private final String replyMessage;

    ServerMessage(String replyMessage) {
        this.replyMessage=replyMessage;
    }

    public String value() {


        return this.replyMessage;
    }
}
