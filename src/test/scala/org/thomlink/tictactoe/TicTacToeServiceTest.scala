package org.thomlink.tictactoe

import model._
import org.thomlink.tictactoe.TestData._
import weaver.scalacheck.Checkers

//import weaver.scalacheck.Checkers

object TicTacToeServiceTest extends weaver.SimpleIOSuite  with Checkers{

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
    forall(winningBoardGen(X)) { board =>
      val result = TicTacToeService.gameResult(board)
      expect(result == Right(Winner(X)))
    }
  }

  test(
    """
      |The TicTacToeService.gameResult function should
      |indicate that O has won
      |in all cases where O should win
      |""".stripMargin
  ) {
    forall(winningBoardGen(O)) { board =>
      val result = TicTacToeService.gameResult(board)
      expect(result == Right(Winner(O)))
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









}
