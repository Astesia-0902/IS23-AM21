package org.am21.model.enumer;

public enum SC {
    //Color end string, color reset
    RST("\033[0m"),

    // Regular Colors. Normal color, no bold, background color etc.
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m"),    // WHITE

    BLACK_B("\033[1;30m"),   // BLACK
    RED_B("\033[1;31m"),     // RED
    GREEN_B("\033[1;32m"),   // GREEN
    YELLOW_BOLD("\033[1;33m"),  // YELLOW
    BLUE_BOLD("\033[1;34m"),    // BLUE
    MAGENTA_BOLD("\033[1;35m"), // MAGENTA
    CYAN_BOLD("\033[1;36m"),    // CYAN
    WHITE_B("\033[1;37m"),   // WHITE
    WHITE_BB("\033[1;97m"),
    YELLOW_BB("\033[1;93m"),   // YELLOW

    CATS("\033[1;32mCats\033[0m"),
    BOOKS("\033[1;37mBooks\033[0m"),
    GAMES("\033[1;33mGames\033[0m"),
    FRAMES("\033[1;34mFrames\033[0m"),
    TROPHIES("\033[1;36mTrophies\033[0m"),
    PLANTS("\033[1;35mPlants\033[0m");
    private final String code;

    SC(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
