package org.thomlink.tictactoe
import cats.effect.{ExitCode, IO, IOApp}
import org.thomlink.tictactoe.model.Row.One
import org.thomlink.tictactoe.model._


/*
Assumptions Made:

The board will never extend beyond 3x3
There will only ever be two players
Draw can only occur when the board is full
  ... otherwise it is incomplete


 */

/*
fn1 boardAfterMove: board, move -> ?board
fn2 move: board, move -> ?board




 */


object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val move = Move(player = Player.X, position = Position(column = Column.A, row = One))
    val board = Board.initial




    IO.fromEither{
      for {
      r <- TicTacToeService.makeMove(board, move)
      _ = print(r)
    } yield ExitCode.Success
  }
  }
}