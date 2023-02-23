package model;

enum Difficulty {
    EASY(4),
    MEDIUM(5),
    HARD(6);

    private final int wordLength;

    Difficulty(int wordLength) {
        this.wordLength = wordLength;
    }
}
