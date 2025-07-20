package statemachine;

import uicomponents.ChessTile;

public interface SelectionState {
	
	    void onTileSelected(ChessTile tile, SelectionController context);
}



