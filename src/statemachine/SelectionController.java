package statemachine;

import logic.ChessConstants;
import ui.ChessTile;

public class SelectionController {
	
		/**
		 * The current state.
		 */
	    private SelectionState currentState;
	    /**
	     * Whether player can play. When false, clickEvents are ignored.
	     */
	    private boolean canPlay = true; // player's turn flag
	    /**
	     * The current player whose turn it is.
	     */
	    private char currentPlayer = ChessConstants.WHITE; 
	    
	    /**
	     * The source tile the player selected.
	     */
	    private ChessTile sourceTile = null;
	    /**
	     * The destination tile the player selected.
	     */
	    private ChessTile destinationTile = null;
	    
	    /**
	     * Constructor.
	     */
	    public SelectionController() {
	        this.currentState = new IdleState();
	    }
	    
	    /**
	     * Setter for currentPlayer.
	     * @param player player to set
	     */
	    public void setCurrentPlayer(char player) {
	    	this.currentPlayer = player; 
	    }
	    
	    /**
	     * Returns the current player.s
	     * @return char 
	     */
	    public char getCurrentPlayer() {
	    	return this.currentPlayer; 
	    }
	    
	    /**
	     * Setter for currentState.
	     * @param state the state to set
	     */
	    public void setState(SelectionState state) {
	        this.currentState = state;
	    }

	    /**
	     * Handles logic for mouseClickEvent. 
	     * @param tile the selected tile
	     */
	    public void onTileClicked(ChessTile tile) {
	        if (canPlay) {
	            currentState.onTileSelected(tile, this);
	        }
	    }
	    
	    /**
	     * Setter for sourceTile. 
	     * @param tile tile to set
	     */
	    public void setSourceTile(ChessTile tile) {
	        this.sourceTile = tile;
	    }
	    
	    /**
	     * Setter for destinationTile.
	     * @param tile the new tile
	     */
	    public void setDestinationTile(ChessTile tile) {
	        this.destinationTile = tile;
	        this.canPlay = false; // Lock move
	    }

	    /**
	     * Getter for sourceTile.
	     * @return
	     */
	    public ChessTile getSourceTile() {
	        return sourceTile;
	    }
	    
	    /**
	     * Getter for currentState.
	     * @return
	     */
	    public SelectionState getState() {
	    	return this.currentState; 
	    }
	    
		 /**
		  * Checks if the player has selected the correct ChessPiece color. 
		  * @param tile the tile.
		  * @return a boolean.
		  */
		private boolean selectedCorrectColor(ChessTile tile) {
			char color = tile.getSelectedChessPieceColor(); 
			boolean selectedCorrectColor = false; 
			if(color == this.currentPlayer) {
				selectedCorrectColor = true; 
			}
			return selectedCorrectColor; 
			
		}
		
		/**
		 * Resets the selection. 
		 */
	    public void resetSelection() {
	        this.sourceTile = null;
	        this.destinationTile = null;
	        this.canPlay = true;
	        this.setState(new IdleState());
	    }

	    /**
	     * Checks to see if player's piece is on selected square.
	     * @param tile the selected tile
	     * @return boolean
	     */
	    public boolean isPlayerPiece(ChessTile tile) {
	        // You implement this: check if tile has a piece of the player's color
	        return !tile.isEmpty() && this.selectedCorrectColor(tile); 
	    }
	    
	    /**
	     * Locks player move and disables clickEvents from updating selection. 
	     */
	    public void lockMove() {
	        this.setState(new SelectionLockedState());
	        this.canPlay = false;
	    }
	}



