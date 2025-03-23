package ui;

import helpers.Pair;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import logic.ChessConstants;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ChessUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	

		
		
		
		GridPane root = new GridPane(); 
		root.setAlignment(Pos.CENTER);
	    // Make sure TilePane does not resize the number of columns (fixed grid)
	    root.setPrefWidth(600);
	    root.setPrefHeight(600);
	    
	    
	  
		
		for(int i = 0; i < ChessConstants.ROWS; i++) {
	
			for(int j = 0; j < ChessConstants.COLUMNS; j++) {
	
				Color tileColor = ((i + j) % 2 == 0 ? Color.BEIGE : Color.BROWN);
				ChessTile tile = new ChessTile(new Pair(i,j), tileColor); 
				tile.setPrefSize(70, 70);
				// Bind the width and height of the tile to the individual cell size based on the root size
				tile.autosize();
				 
				
				root.add(tile, j, i); 
			}
		}

		
		root.setPadding(new Insets(30));
		Scene scene = new Scene(root, 800, 800);
	
		
		
		
		
		
        primaryStage.setTitle("Chess UI");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		
		launch(args); 
	}

}
