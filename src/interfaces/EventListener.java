package interfaces;

import helpers.Pair;

public interface EventListener {
	
	/**
	 * Updates the state of the chess board.
	 * @param initPos the initial square.
	 * @param destination the destination square.
	 */
	public void update(Pair initPos, Pair destination); 
}
