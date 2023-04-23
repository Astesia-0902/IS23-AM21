package org.am21.model.enumer;

public enum ServerMessage {
    //Connection phase
    Connection_Ok("Server > Connection successful\n"),
    Connection_No("Server > Connection failed\n"),

    //Login phase
    Login_Ok("Server > Login successful\n"),
    Login_No("Server > Login failed\n"),

    //Game menu phase
    CreateM_Ok("Server > Create match successful\n"),
    CreateM_No("Server > Create match failed\n"),
    JoinM_Ok("Server > Join match successful\n"),
    JoinM_No("Server > Join match failed\n"),

    //Create match phase
    GenerateR("Server > Generating room\n"),
    PExists("Server > The player already exists in match. In order to create a new match, you need to abandon the current one. Do you wish to continue?\n"),
    PExceed("Server > Exceeded player number limit. Try again\n"),

    //Join match phase
    FindM_Ok("Server > Match is found\n"),
    FindM_No("Server > Match is not found\n"),
    FullM("Server > The match is full\n"),
    PExists_No("Server > The player does not exist in any room. Try to create a new one\n"),

    //Match initialization phase
    BB("Server > The match is about to start. Building game board... Please wait\n"),
    BB_Ok("Server > Board build successful\n"),
    BB_No("Server > Boald build failed\n"),

    //Game phase (Selection phase)
    Selection_Ok("Server > Selection successful\n"),
    Selection_No("Server > Selection failed\n"),
    Cell_Empty("Server > The cell is empty\n"),
    Cell_Illegal("Server > You selected an illegal cell\n"),
    Cell_Selected("Server > You have already selected this cell\n"),
    Cell_Free("Server > The cell was not selectable. Pick an item which has a free side\n"),
    Cell_Orthogonal("Server > The cell was not selectable. Pick an item which is adjacent and in line with the other selected items\n"),
    Hand_Full("Server > You reached the limit of items you could pick\n"),

    //Game phase (Deselection phase)


    //Game phase (Insertion phase)
    WhichCellWriteTheCoordinates(""),
    YouCanNotInsertCardsIfYouDidNotSelectAny(""),
    SelectColumnToInsert(""),
    ColumnNotAvailable(""),

    //Game phase (Sort phase)
    DoYouWantToChangeInsertionOrder(""),
    WitchCardsShouldSwitch(""),
    CardOrderChanged(""),


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
    UserLeave("Server > User is leaved game\n"),


    //Exit game phase
    SUD("Server > Saving user data"),
    WaitingForPlayers(""),

    //Any phase
    ItIsYourTurn(""),
    NotYourTurn(""),
    RunOutOfTime(""),
    WrongPhase(""),
    Error("Error");

    ServerMessage(String s) {

    }

    public static void printMessage(ServerMessage value){
        System.out.println(value);
    }
}
