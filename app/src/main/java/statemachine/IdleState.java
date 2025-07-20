package statemachine;

import uicomponents.ChessTile;

public class IdleState implements SelectionState  {
	
	    @Override
	    public void onTileSelected(ChessTile tile, SelectionController context) {
	        if (context.isPlayerPiece(tile)) {
	            context.setSourceTile(tile);
	            //tile.getStyleClass().add("highlighted");
	            context.setState(new SourceSelectedState());
	        } else {
	        	System.out.println("Invalid selection"); 
	            // Clicked empty tile or enemy piece
	            // Do nothing
	       }
	   }
}



