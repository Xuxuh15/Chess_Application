package statemachine;

import logic.ChessConstants;
import ui.ChessTile;

public class SelectionController {
	

	    private SelectionState currentState;
	    private boolean canPlay = true; // player's turn flag
	    private char currentPlayer = ChessConstants.WHITE; 

	    private ChessTile sourceTile = null;
	    private ChessTile destinationTile = null;

	    public SelectionController() {
	        this.currentState = new IdleState();
	    }
	    
	    public void setCurrentPlayer(char player) {
	    	this.currentPlayer = player; 
	    }
	    public char getCurrentPlayer() {
	    	return this.currentPlayer; 
	    }

	    public void setState(SelectionState state) {
	        this.currentState = state;
	    }

	    public void onTileClicked(ChessTile tile) {
	        if (canPlay) {
	            currentState.onTileSelected(tile, this);
	        }
	    }

	    public void setSourceTile(ChessTile tile) {
	        this.sourceTile = tile;
	    }

	    public void setDestinationTile(ChessTile tile) {
	        this.destinationTile = tile;
	        this.canPlay = false; // Lock move
	    }

	    public ChessTile getSourceTile() {
	        return sourceTile;
	    }
	    
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

	    public void resetSelection() {
	        this.sourceTile = null;
	        this.destinationTile = null;
	        this.canPlay = true;
	        this.setState(new IdleState());
	    }

	    public boolean isPlayerPiece(ChessTile tile) {
	        // You implement this: check if tile has a piece of the player's color
	        return !tile.isEmpty() && this.selectedCorrectColor(tile); 
	    }

	    public void lockMove() {
	        this.setState(new SelectionLockedState());
	        this.canPlay = false;
	    }
	}



