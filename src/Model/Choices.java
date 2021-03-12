package Model;

public class Choices {

	public int x;
	public int y;
	public int ans;

	/**
	 * @param args
	 */
	
	public Choices() {
	}
	public Choices(int x, int y) {
		//it is used in Main.getValidMoves (to set the components that has given from Game class)
		this.x = x;
		this.y = y;
	}

}
