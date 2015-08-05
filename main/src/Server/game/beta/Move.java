package Server.game.beta;

import java.io.Serializable;

/**
 * Created by shagi on 06.08.15.
 */
public class Move implements Serializable {
    String name=GameClientBeta.playerName;
    String move;
    boolean isLost;
    int currentChips;
    int bet;

    Move(String move, int currentChips,int bet, boolean isLost){
        this.move=move;
        this.currentChips=currentChips;
        this.isLost=isLost;
        this.bet=bet;
    }
}