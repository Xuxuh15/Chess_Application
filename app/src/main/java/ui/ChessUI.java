package ui;

import java.net.Socket;
import java.util.Iterator;

import org.json.JSONObject;

import client.ChessClient;
import helpers.Pair;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.ChessConstants;
import statemachine.SelectionController;
import statemachine.SelectionLockedState;
import uicomponents.BoardLabel;
import uicomponents.ChessTile;
import uicomponents.GUIChessBoard;
import uicomponents.PlayerInfoPanel;


/*
 * The UI interface for the Chess game.
 */
public class ChessUI extends Application {
	
	/**
	 * Handles state of player board selection. 
	 */
	private SelectionController controller = new SelectionController();
	
	private PlayerInfoPanel player1Panel; 
	
	private PlayerInfoPanel player2Panel; 
	
	private static ChessClient client; 
	

	
	
	
	/**
	 * The GUI ChessBoard.
	 */
	private GUIChessBoard board = new GUIChessBoard(); 
	
	private boolean ready = false; 
	boolean inSession = true; 
	private Stage primaryStage = null; 
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
		ObservableList<Node> tiles = board.getChildren();
		this.player1Panel = new PlayerInfoPanel("Player 1", "white_pawn", ChessConstants.YOUR_MOVE); 
		this.player2Panel = new PlayerInfoPanel("Player 2", "black_pawn", ChessConstants.WAIT);  
		
		Iterator iter = tiles.iterator(); 
		
		//add event listener to the board tiles
		while(iter.hasNext()) {
			ChessTile tile = (ChessTile) iter.next(); 
			tile.setOnMouseClicked(tileClickHandler);  
				
		}
		
	
		
		
		

		
		Scene scene = new Scene(createMainGrid(), 1600, 800);
		//scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
        primaryStage.setTitle("Chess UI");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        handleGameSession(); 
      
		
	}
	
	
	
	private void handleGameSession() {
		
		Thread gameThread = new Thread(()->{
			//get the assigned color
			this.client.getAssignedColor();
			
			
			
			//wait for the game to start
			this.client.waitForStart();
			
			boolean gameOver = false; 
			System.out.println("Entering loop");
			
			while(!gameOver) {
				
				this.controller.setCanPlay(client.isMyTurn()); 
				
				if(this.controller.getCanPlay()) {

					while(this.controller.getCanPlay()) {
						System.out.println("My Turn");
						System.out.println("<ChessUI> Selection Controller State: "); 
						System.out.println("<ChessUI> myTurn = " + controller.getCanPlay());
						System.out.println("<ChessUI> Selection Controller Color: " + controller.getCurrentPlayer()); 
						System.out.println("<ChessUI> Selection Controller State: " + controller.getState()); 
						
						
						
						while (!(this.controller.getState() instanceof SelectionLockedState)) {
						    try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
						} //wait for a move to be locked
						
						Pair from = this.controller.getSourceTile().getCoord(); 
						
						Pair to = this.controller.getDestinationTile().getCoord(); 
						
						this.client.sendMove(from, to); //send move to server
						System.out.println("Move Sent");
						
						boolean validMove = this.client.wasValidMove(); 
						
						//only for a valid move
						if(validMove) {
							Pair[] update = this.client.getUpdate(); 
							//update the ui board
							Platform.runLater(()->{
								System.out.println("<ChessUI.UpdateBoard> : " + update);
								
								Pair source = update[0]; 
								Pair destination = update[1]; 
								System.out.println("<ChessUI.UpdateBoard> : source = " + source); 
								System.out.println("<ChessUI.UpdateBoard> : destination = " + destination); 
								ImageView view = this.board.updateBoard(source, destination); 
								 if(view != null) {
					              	  
					              	  if (this.controller.getCurrentPlayer() == ChessConstants.WHITE) {
					              		    player1Panel.addCapture(view);
					              		} else {
					              		    player2Panel.addCapture(view);
					              		}
					                }
							}); 
							this.controller.resetSelection(); 
							this.controller.setCanPlay(false);  
						}
						else {
							this.controller.resetSelection(); 
						}
						
						
					}
				
				}
				else {
					Pair[] update = this.client.getUpdate(); 
					//update the ui board
					Platform.runLater(()->{
						
						Pair source = update[0]; 
						Pair destination = update[1]; 
						ImageView view = this.board.updateBoard(source, destination); 
						 if(view != null) {
			              	  
			              	  if (this.controller.getCurrentPlayer() == ChessConstants.WHITE) {
			              		    player1Panel.addCapture(view);
			              		} else {
			              		    player2Panel.addCapture(view);
			              		}
			                }
					}); 
				}
				//check for win
				gameOver = !this.client.continuePlaying();
				if(gameOver) {
					break; 
				}
				this.controller.toggleCurrentPlayer(); 
				
				
			} //end of game loop
			
			
		});
		 //gameThread.setDaemon(true);  
		 gameThread.start();          
		
		
	}
	
	public EventHandler<MouseEvent> tileClickHandler = e -> {
	    ChessTile clickedTile = (ChessTile) e.getSource();
	    if(this.controller.getCanPlay()) {
			  this.controller.onTileClicked(clickedTile);
            }
	}; 
		  
	 
	
	public static void setSocket(ChessClient s){
		client = s; 
	}
	    
	
	
	/**
	 * Creates grid to hold chess board and chess labels. 
	 * @return GridPane
	 */
	private GridPane createMainGrid() {
		GridPane mainGrid = new GridPane(); 
		mainGrid.setAlignment(Pos.CENTER);
	    // Make sure TilePane does not resize the number of columns (fixed grid)
	    mainGrid.setPrefWidth(800);
	    mainGrid.setPrefHeight(800);
	    mainGrid.setPadding(new Insets(30));
	    mainGrid.add(new ColumnLabel(), 2, 0);
	    mainGrid.add(this.board, 2,1); 
	    mainGrid.add(new RowLabel(), 1, 1);
	    
	    mainGrid.add(this.player1Panel, 0,1); 
	    mainGrid.add(this.player2Panel, 3,1);
	    return mainGrid; 
	}
	
	/**
	 * The column labels for the chess board.
	 */
	public class ColumnLabel extends GridPane  {
		
		public ColumnLabel() {
			char label = 'A'; 
			for(int j = 0;  j < ChessConstants.COLUMNS; j++) {
				BoardLabel colLabel = new BoardLabel(label);
				colLabel.setPrefSize(BoardLabel.PREF_WIDTH, BoardLabel.PREF_HEIGHT - 50);
				this.add(colLabel,j,0); 
				this.setAlignment(Pos.CENTER); 
				label++; 
				
			}
			
		}
		
	}
	
	/**
	 * The row labels for the chess board.
	 */
	public class RowLabel extends GridPane  {
		
		public RowLabel() {
			
			int label = 1; 
			for(int i = 0;  i < ChessConstants.ROWS; i++) {
				BoardLabel rowLabel = new BoardLabel(String.valueOf(label)); 
				rowLabel.setPrefSize(BoardLabel.PREF_WIDTH - 50, BoardLabel.PREF_HEIGHT);
				this.add(rowLabel,0,i); 
				this.setAlignment(Pos.CENTER);
				label++; 
				
			}
			
		}
	}

	
	
	public static void main(String[] args) {
		
		launch(args); 
		
		
	}

}
