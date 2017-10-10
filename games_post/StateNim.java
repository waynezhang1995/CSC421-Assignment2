public class StateNim extends State {

    public int coins;

    public StateNim() {

        this.coins = 13;
        player = 1;
    }

    public StateNim(StateNim state) {

        this.coins = state.coins;
        player = state.player;
    }

    public String toString() {

    	return "Number of coins left: " + this.coins + "\n";
    }
}
