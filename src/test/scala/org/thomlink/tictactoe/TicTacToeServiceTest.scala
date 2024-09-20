package org.thomlink.tictactoe

import cats.Eval.One
import model._
import org.thomlink.tictactoe
import org.thomlink.tictactoe.TestData._
import org.thomlink.tictactoe.TicTacToeService
import org.thomlink.tictactoe.model.Player.X
import weaver.scalacheck.Checkers

//import weaver.scalacheck.Checkers

object TicTacToeServiceTest extends weaver.SimpleIOSuite with Checkers {

  /*
  Test Cases:
  Happy:
    X has won
    O has won
    Draw
    Incomplete game

  Sad:
    Invalid board
      - multiple winners
      - either player moves after losing
      - one side has moved too many times

   */


  test(
    """
      |The TicTacToeService.gameResult function should
      |indicate that X has won
      |in all cases where X should win
      |""".stripMargin
  ) {
    forall(winningBoardGen(Player.X)) { board =>
      val result = TicTacToeService.gameResult(board)
      expect(result == Right(Winner(Player.X)))
    }
  }

  test(
    """
      |The TicTacToeService.gameResult function should
      |indicate that O has won
      |in all cases where O should win
      |""".stripMargin
  ) {
    forall(winningBoardGen(Player.O)) { board =>
      val result = TicTacToeService.gameResult(board)
      expect(result == Right(Winner(Player.O)))
    }
  }

  pureTest(
    """
      |The TicTacToeService.gameResult function should
      |indicate that the game has ended in a draw
      |when the board is full and neither side has won
      |""".stripMargin
  ) {
    val board = TestData.drawGame
    val result = TicTacToeService.gameResult(board)
    expect(result == Right(Draw))
  }

  pureTest(
    """
      |The TicTacToeService.gameResult function should
      |indicate that the game is incomplete
      |when the board is not full and neither side has won
      |""".stripMargin
  ) {
    val board = TestData.incompleteGame2
    val result = TicTacToeService.gameResult(board)
    expect(result == Right(Incomplete))
  }

  pureTest(
    """
      |The TicTacToeService.gameResult function should
      |raise a Multiple Winners error
      |when the multiple winning positions are found
      |""".stripMargin
  ) {
    val board = TestData.multipleWinners
    TicTacToeService.gameResult(board) match {
      case Left(error) => error match {
        case MultipleWinners(_) => success
        case MovedAfterLoss(_) => failure(s"Expected Multiple Winners, found $error")
        case IncorrectMoveCount(_) => failure(s"Expected Multiple Winners, found $error")
      }
      case Right(value) => failure(s"Expected Multiple Winners, found $value")
    }
  }

  test(
    """
      |The TicTacToeService.gameResult function should
      |raise a MovedAfterLoss error
      |when the a winning position is found, but the game has not ended
      |""".stripMargin
  ) {
    forall(movedAfterLossGen) { board =>

      TicTacToeService.gameResult(board) match {
        case Left(error) => error match {
          case MultipleWinners(_) => failure(s"Expected Multiple Winners, found $error")
          case MovedAfterLoss(_) => success
          case IncorrectMoveCount(_) => failure(s"Expected Multiple Winners, found $error")
        }
        case Right(value) => failure(s"Expected Multiple Winners, found $value")
      }
    }
  }

  test(
    """
      |The TicTacToeService.gameResult function should
      |raise a IncorrectMoveCount error
      |when one side has moved too many times
      |""".stripMargin
  ) {
    forall(unbalancedMovesGen) { board =>

      TicTacToeService.gameResult(board) match {
        case Left(error) => error match {
          case MultipleWinners(_) => failure(s"Expected Multiple Winners, found $error")
          case MovedAfterLoss(_) => failure(s"Expected Multiple Winners, found $error")
          case IncorrectMoveCount(_) => success
        }
        case Right(value) => failure(s"Expected Multiple Winners, found $value")
      }
    }
  }







  // ---


  pureTest(
    """
      |The TicTacToeService.boardAfterMove function should
      |return a new board
      |when a valid move was played
      |""".stripMargin
  ) {

    val board = Board.initial

    val move = Move(Player.X, position = Position(column = Column.A, row = Row.One))

    expect(
      TicTacToeService.boardAfterMove(board, move).contains(Board(
        a1 = Occupied(Player.X), b1 = Free, c1 = Free, a2 = Free, b2 = Free, c2 = Free, a3 = Free, b3 = Free, c3 = Free
      ))
    )

  }

  pureTest(
    """
      |The TicTacToeService.boardAfterMove function should
      |return a new board
      |when a valid move was played
      |""".stripMargin
  ) {


    val board = Board.initial.copy(a1= Occupied(X))

    val move = Move(Player.O, position = Position(column = Column.A, row = Row.One))


      TicTacToeService.boardAfterMove(board, move) match {
        case Left(PositionUnavailable(_)) => success
        case Right(value) => failure(s"Expected Position unavailable, got $value")
      }


  }



  pureTest(
    """
      |The TicTacToeService.boardAfterMove function should
      |return a new board
      |when a valid move was played
      |""".stripMargin
  ) {



  }




}
