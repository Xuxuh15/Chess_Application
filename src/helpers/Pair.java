package helpers;

public class Pair {

	/**
	 * the row
	 */
	private int row; 
	/**
	 * the column
	 */
	private int col; 
	
	/**
	 * Constructor
	 * @param row the row
	 * @param col the column
	 */
	public Pair(int row, int col) {
		this.row = row; 
		this.col = col; 
	}
	
	/**
	 * Returns the row
	 * @return int row
	 */
	public int row() {
		return this.row; 
	}
	
	/**
	 * Returns the column
	 * @return int col
	 */
	public int col() {
		return this.col; 
	}
	
	/**
	 * Sets the value of the row
	 * @param row value to be set
	 */
	public void setRow(int row) {
		this.row = row; 
	}
	
	/**
	 * Sets the value of the column
	 * @param col the value to be set
	 */
	public void setCol(int col) {
		this.col = col; 
	}
	
	/**
	 * String representation of the pair
	 */
	@Override 
	public String toString() {
		return "[" + this.row() + "," + this.col() + "]"; 
	}
}
