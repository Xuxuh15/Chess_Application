# Chess_Application
A chess game application built in Java that features both a text-console and GUI version. 


## JSON Protocol

Describes the JSON protocol that the client and server use to communciate with each other. 


#### Assign Color <GameSession.java>

Response: Assigns a color to each client.

```
{
    "type": <int>, // ChessConstants.ASSIGNMENT
    "color": <char>,  //assigns a color to the player
}
```

#### Start <GameSession.java>

Response: Signal for the game to start. 

```
{
    "type": <int> // ChessConstants.START
}
```

#### Play <GameSession.java>

Response: Signals which client should play for this turn. 

```
{ 
    "type": <int>, // ChessConstants.PLAY
    "shouldPlay": <boolean>, // determines which player's turn it is
}
```


#### Send Move <ChessClient.java>

Request: To send the client's a move to the ChessServer. 

```{
    "type": <int>, // ChessConstants.MOVE
    "from": <Pair>, // the source square
    "to": <Pair>,  // the destination square
    }
```

#### Valid Move <GameSession.java> (Success Case)

Response: Signals to the client that their move was valid. 

```
{
    "type": <int>, // ChessConstants.WAS_VALID_MOVE
    "ok": <boolean>,  // whether the move was valid or not
    "message": <String>,

}
```

#### Invalid Move <GameSession.java> (Failure Case )

Response: Signals to the client that their move was invalid.

```
{
    "type": <int>, // ChessConstants.WAS_VALID_MOVE
    "ok": <boolean>, // whether the move was valid or not
    "message": <String> // reason why the move was invalid
}
```

#### Update Board <GameSession.java>

Response: Response that tells client to update their board. 

```
{
    "type": <int>, // ChessConstants.UPDATE
    "from": <Pair>, // the source square
    "to": <Pair>, // the destination square
}
```

#### Continue Playing <GameSession.java>

Response: Response that tells the clients to continue playing since no one has achieved a win state.

```
{
    "type": <int>, //ChessConstants.CONTNINUE
    "message": <String>,
}
```

#### Game Over <GameSession.java>

Response: Signals to the client that a win state has been acchieved and announces the winner.

```
{
    "type": <int>, // ChessConstants.END
    "message": <String> // announces which color won
}
```





