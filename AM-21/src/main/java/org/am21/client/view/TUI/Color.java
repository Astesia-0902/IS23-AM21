package org.am21.client.view.TUI;

public enum Color {
    //Color end string, color reset
    RESET("\033[0m"),

    //Background

    RED_BG("\u001B[41m"),
    WHITE_BG("\u001B[47m"),
    RED_BRIGHT("\033[0;91m"),
    RED_BB("\033[1;91m"),

    // Regular Colors. Normal color, no bold, background color etc.
    RED("\033[0;31m"),      // RED
    YELLOW("\033[0;33m"),   // YELLOW
    CYAN("\033[0;36m"),     // CYAN

    YELLOW_BOLD("\033[1;33m"),  // YELLOW
    WHITE_BOLD("\033[1;37m"),   // WHITE
    BLUE_BOLD("\033[1;34m"),    // BLUE

    GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
    YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
    BLUE_BOLD_BRIGHT("\033[1;94m"),
    MAGENTA_BOLD_BRIGHT("\033[1;95m"),
    CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
    WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

    CATS("\033[1;92mCats\033[0m"),
    BOOKS("\033[1;97mBooks\033[0m"),
    GAMES("\033[1;93mGames\033[0m"),
    FRAMES("\033[1;94mFrames\033[0m"),
    TROPHIES("\033[1;96mTrophies\033[0m"),
    PLANTS("\033[1;95mPlants\033[0m");
    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
