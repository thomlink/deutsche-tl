
Tom Linker's submission for the Tic Tac Toe exercise.

## Assumptions
- Board is 3x3 and unlikely to change
- Only two players and unlikely to change
- Calculating the winner does not assume any correct sequence of moves before then
  - i.e. any combination of moves is a valid input 


### Requirements for calculating the winner
- Possible (non-error) results are
  - Winner (and who) but not highlighting *where* the winning position is
  - Draw - requires the board to be filled, so no calculating of possible future moves to determine whether a draw is inevitible
  - Incomplete - no winner found, but there are still empty squares
- All errors must be handled


## Approach

(Assuming we can & will go through this in more detail in the follow-up)

- Think about the game of TTT to come up with our requirements
- Decide on a data model
  - in particular how to model the board
- Think about code structure
  - App -> Service -> client model
- Predict what errors are possible
- Write tests for all cases
- Implement method to pass tests. 
