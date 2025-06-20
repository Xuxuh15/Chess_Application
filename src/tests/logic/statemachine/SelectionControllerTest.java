package tests.logic.statemachine;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class SelectionControllerTest {
	
	
	private static SelectionController controller; 
	private static GUIChessBoard board; 
	
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		
		controller = new SelectionController();	
		controller.setCurrentPlayer(ChessConstants.WHITE);  
		board = new GUIChessBoard(); 
		
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
		assertTrue(controller.isPlayerPiece(board.peek(new Pair(1,1)))); 
		
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
		controller.onTileClicked(board.peek(new Pair(1,1)));
		assertTrue(controller.getState() instanceof SourceSelectedState); 
	}
	
	@Test
	@DisplayName("onTileClicked SourceSelectedState same source square sets state to IdleState")
	public void onTileClicked_SourceSelectedState_SameSourceSquare_ResetsStateToIdle() {
		controller.onTileClicked(board.peek(new Pair(1,1)));
		controller.onTileClicked(board.peek(new Pair(1,1)));
		assertTrue(controller.getState() instanceof IdleState); 
	}
	
	@Test
	@DisplayName("onTileClicked SourceSelectedState different square sets state to SelectionLockedState")
	public void onTileClicked_SourceSelectedState_DifferentSquare_SetsStateToLocked() {
		controller.onTileClicked(board.peek(new Pair(1,1)));
		controller.onTileClicked(board.peek(new Pair(2,1)));
		assertTrue(controller.getState() instanceof SelectionLockedState); 
	}
	
	@Test
	@DisplayName("onTileClicked SelectionLockedState clicked square does nothing")
	public void onTileClicked_SelectionLockedState_ClickedSquare_DoesNothing() {
		controller.onTileClicked(board.peek(new Pair(1,1)));
		controller.onTileClicked(board.peek(new Pair(2,1)));
		assertTrue(controller.getState() instanceof SelectionLockedState); 
		controller.onTileClicked(board.peek(new Pair(3,1)));
		assertTrue(controller.getState() instanceof SelectionLockedState); 
		
	}
	
	


}
