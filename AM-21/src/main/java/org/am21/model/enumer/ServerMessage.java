package org.am21.model.enumer;

public enum ServerMessage {
    //Connection phase
    Connection_Ok("Server > Connection successful"),
    Connection_No("Server > Connection failed"),

    //Login phase
    Login_Ok("Server > Login successful.\nServer > Hi "),
    Login_No("Server > Access denied. This username is already taken, please enter a new one..."),
    ListP("Server > This is list of player online: "),

    //Create match phase
    PExists("Server > The player already exists in match. In order to create a new match, you need to abandon the current one. Do you wish to continue?"),
    PExceed("Server > Exceeded player number limit. Try again"),
    CreateM_Ok("Server > Create match successful"),
    CreateM_No("Server > Create match failed"),


    //Join match phase
    FindM_Ok("Server > Match is found"),
    FindM_No("Server > The room does not exists. Please try again"),
    FullM("Server > The room is full"),
    PExists_No("Server > The player does not exist in any room. Try to create a new one"),

    //Match initialization phase
    BB("Server > The match is about to start. Building game board... Please wait"),
    BB_Ok("Server > Board build successful"),
    BB_No("Server > Board build failed"),

    //Game phase (Selection phase)
    Selection_Ok("Server > Selection successful"),
    Selection_No("Server > Selection failed"),
    Cell_Empty("Server > The cell is empty"),
    Cell_Illegal("Server > You selected an illegal cell"),
    Cell_Selected("Server > You have already selected this cell"),
    Cell_Free("Server > The cell was not selectable. Pick an item which has a free side"),
    Cell_Orthogonal("Server > The cell was not selectable. Pick an item which is adjacent and in line with the other selected items"),
    Hand_Full("Server > You reached the limit of items you could pick"),

    //Game phase (Deselection phase)
    DeSel_Ok("Server > Deselection successful"),
    //TODO: maybe not necessary
    // DeSel_No("Server > Deselection failed"),
    DeSel_Null("Server > You did not select any card yet. Deselection is not necessary"),

    //Game phase (Insertion phase)
    WhichCellWriteTheCoordinates(""),
    YouCanNotInsertCardsIfYouDidNotSelectAny(""),
    SelectColumnToInsert(""),
    ColumnNotAvailable(""),

    //Game phase (Sort phase)
    Sort_No("Server > Not enough cards in your hand"),
    Sort_Index("Server > Index out of border"),
    Sort_Ok("Server > Order changed"),


    //Game phase (Show object phase)
    TheseAreTheCommonGoals(""),
    ThisIsYourPersonalGoal(""),
    HereIsTheBoard(""),
    HereIsTheShelf(""),
    HereIsTheCommonGoal(""),
    HereIsThePersonalGoal(""),
    HereIsThePlayersStats(""),

    //Game phase (Goal achievement phase)
    YouMatchedAnItem(""),
    GoalAchieved(""),
    Points(""),

    //Chat phase
    WriteMessage(""),
    ChatHistory(""),


    //Help Command phase

    //Game phase (Game over phase)
    GameOver(""),
    YouWon(""),
    YouAreTheSecond(""),
    YouAreTheThird(""),
    YouAreTheFourth(""),
    HereIsTheEndGameToken(""),

    //Leave match phase
    UserLeave("Server > User left the game"),


    //Exit game phase
    SUD("Server > Saving user data"),
    WaitingForPlayers(""),

    //Any phase
    ItIsYourTurn(""),
    NotYourTurn(""),
    RunOutOfTime(""),
    WrongPhase(""),
    Error("Error");

    private final String replyMessage;

    ServerMessage(String replyMessage) {
        this.replyMessage=replyMessage;
    }

    public String value() {


        return this.replyMessage;
    }
}
