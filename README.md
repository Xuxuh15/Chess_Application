# Chess_Application
A chess game application built in Java that features both a text-console and GUI version. 


## JSON Protocol



### Move

Request: 

``` {"type": "move", "row":<int>, "column":<int>} ```

Success Response:

``` {"ok":true, "message":<String>} ```

Error Response:

``` {"ok":false, "message":<String>} ```

### Status Updates

#### Types

checkmate : checkmate
check : opponent in check
start: start game
end: end game
play: current players turn


``` {"type": , "code":<int>} ```
