package tests.logic.statemachine;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import helpers.ChessEmulator;
import helpers.Pair;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.VerifyMovePawn;
import statemachine.IdleState;
import statemachine.SelectionController;
import statemachine.SelectionLockedState;
import statemachine.SourceSelectedState;
import uicomponents.GUIChessBoard;
import ui.ChessUI; 
import javafx.scene.Scene; 

public class SelectionControllerTest extends ApplicationTest {
	
	
	private static SelectionController controller; 
	private static GUIChessBoard board; 
	private ChessUI ui; 
	
	 @Override
	    public void start(Stage stage) {
		 	ui = new ChessUI(); 
		 	board = new GUIChessBoard(); 
		 	controller = new SelectionController();	
			controller.setCurrentPlayer(ChessConstants.WHITE);
			  // Minimal valid JavaFX scene (required for toolkit init)
		    Scene scene = new Scene(board, 100, 100);
		    stage.setScene(scene);
		    stage.show();
		    System.out.println("I am here"); 
			
	    }
	 
	



	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		 controller = null; 
		 board = null; 
		 
	}

	@BeforeEach
	public void setUp() throws Exception {
		controller.resetSelection();
		
	}
	
	
	@Test
	@DisplayName("SelectionController constructor initializes controller in IdleState")
	public void selectionController_InitializedInIdleState_ReturnsTrue() {
		assertTrue(controller.getState() instanceof IdleState); 
	}
	
	@Test
	@DisplayName("SelectionController isPlayerPiece valid piece returns true ")
	public void isPlayerPiece_ValidPiece_ReturnsTrue() {
		interact(()->{
			assertTrue(controller.isPlayerPiece(board.peek(new Pair(1,1)))); 
		}); 
		
	}
	
	@Test
	@DisplayName("lockMove sets state to SelectionLockedState")
	public void lockMove_SetsSelectionStateToLocked_ReturnsTrue() {
		controller.lockMove();
		assertTrue(controller.getState() instanceof SelectionLockedState); 
	}
	
	@Test
	@DisplayName("resetSelection resets state to IdleState")
	public void resetSelection_SetsStateToIdle_ReturnsTrue() {
		controller.resetSelection();
		assertTrue(controller.getState() instanceof IdleState); 
	}
	
	@Test
	@DisplayName("onTileClicked IdleState for valid square sets state to SourceSelectedState")
	public void onTileClicked_ValidSquare_SetsStateToSourceSelectedState() {
		interact(()->{
			controller.onTileClicked(board.peek(new Pair(1,1)));
		});
		
		assertTrue(controller.getState() instanceof SourceSelectedState); 
	}
	
	@Test
	@DisplayName("onTileClicked SourceSelectedState same source square sets state to IdleState")
	public void onTileClicked_SourceSelectedState_SameSourceSquare_ResetsStateToIdle() {
		interact(()->{
			controller.onTileClicked(board.peek(new Pair(1,1)));
			controller.onTileClicked(board.peek(new Pair(1,1)));
		});
		
		assertTrue(controller.getState() instanceof IdleState); 
	}
	
	@Test
	@DisplayName("onTileClicked SourceSelectedState different square sets state to SelectionLockedState")
	public void onTileClicked_SourceSelectedState_DifferentSquare_SetsStateToLocked() {
		interact(()->{
			controller.onTileClicked(board.peek(new Pair(1,1)));
			controller.onTileClicked(board.peek(new Pair(2,1)));
		});
	
		assertTrue(controller.getState() instanceof SelectionLockedState); 
	}
	
	@Test
	@DisplayName("onTileClicked SelectionLockedState clicked square does nothing")
	public void onTileClicked_SelectionLockedState_ClickedSquare_DoesNothing() {
		interact(()->{
			controller.onTileClicked(board.peek(new Pair(1,1)));
			controller.onTileClicked(board.peek(new Pair(2,1)));
		});
		
		assertTrue(controller.getState() instanceof SelectionLockedState); 
		interact(()->{
			controller.onTileClicked(board.peek(new Pair(3,1)));
		});
		
		assertTrue(controller.getState() instanceof SelectionLockedState); 
		
	}
	
	


}
