package game;

import engine.GameEngine;

/*
* This is a startup class that triggers before game logic.
* Use this class for setup, references, etc.
*/

public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        engine.start();
    }
}
