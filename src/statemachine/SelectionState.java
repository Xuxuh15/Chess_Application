package statemachine;

import ui.ChessTile;

public interface SelectionState {
	
	    void onTileSelected(ChessTile tile, SelectionController context);
}



