package org.am21.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardUtil {

    /*
     *  2Players:
     *              *0: [ ][ ][ ][ ][ ][ ][ ][ ][ ]
     *              *1: [ ][ ][ ][*][*][ ][ ][ ][ ]
     *              *2: [ ][ ][ ][*][*][*][ ][ ][ ]
     *              *3: [ ][ ][*][*][*][*][*][*][ ]
     *              *4: [ ][*][*][*][*][*][*][*][ ]
     *              *5: [ ][*][*][*][*][*][*][ ][ ]
     *              *6: [ ][ ][ ][*][*][*][ ][ ][ ]
     *              *7: [ ][ ][ ][ ][*][*][ ][ ][ ]
     *              *8: [ ][ ][ ][ ][ ][ ][ ][ ][ ]
     *              *
     *  3Players:
     *              *0: [ ][ ][ ][+][ ][ ][ ][ ][ ]
     *              *1: [ ][ ][ ][*][*][ ][ ][ ][ ]
     *              *2: [ ][ ][+][*][*][*][+][ ][ ]
     *              *3: [ ][ ][*][*][*][*][*][*][+]
     *              *4: [ ][*][*][*][*][*][*][*][ ]
     *              *5: [+][*][*][*][*][*][*][ ][ ]
     *              *6: [ ][ ][+][*][*][*][+][ ][ ]
     *              *7: [ ][ ][ ][ ][*][*][ ][ ][ ]
     *              *8: [ ][ ][ ][ ][ ][+][ ][ ][ ]
     *              *
     *  4Players:
     *              *0: [ ][ ][ ][*][+][ ][ ][ ][ ]
     *              *1: [ ][ ][ ][*][*][+][ ][ ][ ]
     *              *2: [ ][ ][*][*][*][*][*][ ][ ]
     *              *3: [ ][+][*][*][*][*][*][*][*]
     *              *4: [+][*][*][*][*][*][*][*][+]
     *              *5: [*][*][*][*][*][*][*][+][ ]
     *              *6: [ ][ ][*][*][*][*][*][ ][ ]
     *              *7: [ ][ ][ ][+][*][*][ ][ ][ ]
     *              *8: [ ][ ][ ][ ][+][*][ ][ ][ ]
     */


    /**
     * Create a list of playable Boundaries for each row of the board
     * @param maxSeats number of max players
     * @return list of boundaries for each row
     */
    public static List<Coordinates> boardBounder(int maxSeats){
        List<Coordinates> boundaries = new ArrayList<>();

        switch (maxSeats){
            case 2: Collections.addAll(boundaries,
                        null,  //!!!!
                        new Coordinates(3,4),
                        new Coordinates(3,5),
                        new Coordinates(2,7),
                        new Coordinates(1,7),
                        new Coordinates(1,6),
                        new Coordinates(3,5),
                        new Coordinates(4,5),
                        null);
                break;
            case 3: Collections.addAll(boundaries,
                    new Coordinates(3,3),
                    new Coordinates(3,4),
                    new Coordinates(2,6),
                    new Coordinates(2,8),
                    new Coordinates(1,7),
                    new Coordinates(0,6),
                    new Coordinates(2,6),
                    new Coordinates(4,5),
                    new Coordinates(5,5));
                break;
            case 4: Collections.addAll(boundaries,
                    new Coordinates(3,4),
                    new Coordinates(3,5),
                    new Coordinates(2,6),
                    new Coordinates(1,8),
                    new Coordinates(0,8),
                    new Coordinates(0,7),
                    new Coordinates(2,6),
                    new Coordinates(3,5),
                    new Coordinates(4,5));
                break;
        }
        return boundaries;
    }



}
