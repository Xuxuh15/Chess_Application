package statemachine;

import uicomponents.ChessTile;

public class SelectionLockedState implements SelectionState {
	
	    @Override
	    public void onTileSelected(ChessTile tile, SelectionController context) {
	        // Do nothing — move is locked
	    	System.out.println("Wait on response from server"); 
	    }
}


