package statemachine;

import ui.ChessTile;

public class SourceSelectedState implements SelectionState {
	    @Override
	    public void onTileSelected(ChessTile tile, SelectionController context) {
	        ChessTile source = context.getSourceTile();

	        if (tile == source) {
	        	//tile.getStyleClass().remove("highlighted");
	            context.resetSelection();
	        } else {
	            context.setDestinationTile(tile);
	            //tile.select();
	            System.out.println("Move locked: " + source.getCoord() + " to " + tile.getCoord());
	            context.lockMove(); // transition to locked state
	        }
	    }
}



