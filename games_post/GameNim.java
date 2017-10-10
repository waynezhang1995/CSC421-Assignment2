import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {

    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;

    public GameNim() {
        currentState = new StateNim();
    }

    public boolean isWinState(State state) {
        StateNim tstate = (StateNim) state;
        //player who did the last move
        return tstate.coins == 1 ? true : false;
    }

    /**
     * No Stuck State in Nim. Always return false
     */
    public boolean isStuckState(State state) {

        return false;
    }

    public Set<State> getSuccessors(State state) {
        if (isWinState(state) || isStuckState(state))
            return null;

        Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;

        StateNim successor_state;

        // take 1 pile
        successor_state = new StateNim(tstate);
        successor_state.coins -= 1;
        successor_state.player = (state.player == 0 ? 1 : 0);

        successors.add(successor_state);

        // take 2 pile
        successor_state = new StateNim(tstate);
        successor_state.coins -= 2;
        successor_state.player = (state.player == 0 ? 1 : 0);

        successors.add(successor_state);

        // take 3 pile
        successor_state = new StateNim(tstate);
        successor_state.coins -= 3;
        successor_state.player = (state.player == 0 ? 1 : 0);

        successors.add(successor_state);

        return successors;
    }

    public double eval(State state) {
        if (isWinState(state)) {
            //player who made last move
            int previous_player = (state.player == 0 ? 1 : 0);

            if (previous_player == 0) //computer wins
                return WinningScore;
            else //human wins
                return LosingScore;
        }

        return NeutralScore;
    }

    public static void main(String[] args) throws Exception {

        Game game = new GameNim();
        Search search = new Search(game);
        int depth = 8;

        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            StateNim nextState = null;

            switch (game.currentState.player) {
            case 1: //Human

                //get human's move
                System.out.print("Enter # of pile you want to take> ");
                int piles = Integer.parseInt(in.readLine());

                nextState = new StateNim((StateNim) game.currentState);
                nextState.player = 1;
                nextState.coins -= piles;
                System.out.println("\nHuman took: " + piles + " " + nextState);
                break;

            case 0: //Computer

                StateNim cState = (StateNim) game.currentState;
                nextState = (StateNim) search.bestSuccessorState(depth);
                nextState.player = 0;
                System.out.println("Computer took: " + (cState.coins - nextState.coins) + " " + nextState);
                break;
            }

            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player == 0 ? 1 : 0);

            //Who wins?
            if (game.isWinState(game.currentState)) {

                if (game.currentState.player == 1) //i.e. last move was by the computer
                    System.out.println("Computer wins!");
                else
                    System.out.println("You win!");

                break;
            }

            if (game.isStuckState(game.currentState)) {
                System.out.println("Cat's game!");
                break;
            }
        }
    }
}