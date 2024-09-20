package org.thomlink.tictactoe

import com.sun.rowset.internal
import org.thomlink.tictactoe.model.{Board, Column, Draw, Free, GameResult, Incomplete, Move, Occupied, Player, Position, PositionState, Winner, Row}


sealed trait TTTServiceError extends Throwable

case class PositionUnavailable(position: Position) extends TTTServiceError

sealed trait InvalidBoardError extends TTTServiceError

case class MultipleWinners(description: String) extends InvalidBoardError

case class MovedAfterLoss(description: String) extends InvalidBoardError

case class IncorrectMoveCount(description: String) extends InvalidBoardError





object TicTacToeService {

  private def resultOfThreeTiles(a: PositionState, b: PositionState, c: PositionState): Option[Player] =
    if (a == b && b == c) {
      a match {
        case Occupied(player) => Some(player)
        case model.Free => None
      }
    } else
      None

  private def topRowWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.a1, board.b1, board.c1)

  private def middleRowWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.a2, board.b2, board.c2)

  private def bottomRowWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.a3, board.b3, board.c3)

  private def leftColumnWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.a1, board.a2, board.a3)

  private def middleColumnWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.b1, board.b2, board.b3)

  private def rightColumnWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.c1, board.c2, board.c3)

  private def downDiagonalWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.a1, board.b2, board.c3)

  private def upDiaonalWin(board: Board): Option[Player] =
    resultOfThreeTiles(board.a3, board.b2, board.c1)


  private def allPossibleWinners(board: Board): List[Option[Player]] = List(
    topRowWin(board),
    middleRowWin(board),
    bottomRowWin(board),
    leftColumnWin(board),
    middleColumnWin(board),
    rightColumnWin(board),
    downDiagonalWin(board),
    upDiaonalWin(board),
  )

  /**
   *
   * @param board the board state
   * @return Int - the difference between X and O moves. Should always be either 1 or 0, otherwise error
   */
  private def moveCountDiff(board: Board): Int = {
    val positions = board.toList
    val xMoves = positions.count(_ == Occupied(Player.X))
    val oMoves = positions.count(_ == Occupied(Player.O))
    xMoves - oMoves
  }

  private def isBoardFull(board: Board): Boolean =
    board
      .toList
      .count(_ == Free) == 0


  // Either
  // A player has won
  // It is a draw
  // Game is incomplete
  // Game is invalid
  def gameResult(board: Board): Either[InvalidBoardError, GameResult] = {
    // are the (maybe) moves balanced
    //    else -> error (Incorrect move count)
    // calculate winning positions
    // is there more than one
    //    error (multiple winners)
    // is there none
    //    is the board full -> draw
    //        else -> Incomplete
    // is there one exactly
    //    is the winner X
    //        has X moved once more than 0 -> X win
    //            else -> invalid game (moved after loss)
    //    is the winner O
    //        has O moved the same times as X -> O win
    //            else -> invalid game (moved after loss)


    val moveDifference = moveCountDiff(board)


    if (moveDifference < 0 || moveDifference > 1) {
      Left(IncorrectMoveCount(s"Move difference was $moveDifference"))
    } else {
      allPossibleWinners(board).flatten match {
        case winner :: Nil =>
          winner match {
            case Player.X =>
              moveDifference match {
                case 1 => Right(Winner(Player.X))
                case x if x > 1 => Left(IncorrectMoveCount("foo"))
                case _ => Left(MovedAfterLoss("O moved after losing"))
              }

            case Player.O =>
              moveDifference match {
                case 0 => Right(Winner(Player.O))
                case x if x < 0 => Left(IncorrectMoveCount("foo"))
                case _ => Left(MovedAfterLoss("X moved after losing"))
              }
          }

        case Nil => if (isBoardFull(board)) {
          Right(Draw)
        } else {
          Right(Incomplete)
        }
        case xs =>
          Left(MultipleWinners(s"${xs.size} winning positions found"))
      }

    }
  }





  /**
   *
   * @param board
   * @param move
   * @return New board if the position was empty, None if not
   */
  def boardAfterMove(board: Board, move: Move): Either[TTTServiceError, Board] = {
    val positionStateAtMovePosition = move.position match {
      case Position(Column.A, Row.One) => board.a1
      case Position(Column.B, Row.One) => board.b1
      case Position(Column.C, Row.One) => board.c1
      case Position(Column.A, Row.Two) => board.a2
      case Position(Column.B, Row.Two) => board.b2
      case Position(Column.C, Row.Two) => board.c2
      case Position(Column.A, Row.Three) => board.a3
      case Position(Column.B, Row.Three) => board.b3
      case Position(Column.C, Row.Three) => board.c3
    }


    // check if the pisition is empty
    // insert at posiiton
    //

    if (positionStateAtMovePosition == Free) {
      move.position match {
        case Position(Column.A, Row.One) => Right(board.copy(a1  = Occupied(move.player)))
        case Position(Column.B, Row.One) => Right(board.copy(b1  = Occupied(move.player)))
        case Position(Column.C, Row.One) => Right(board.copy(c1  = Occupied(move.player)))
        case Position(Column.A, Row.Two) => Right(board.copy(a2  = Occupied(move.player)))
        case Position(Column.B, Row.Two) => Right(board.copy(b2  = Occupied(move.player)))
        case Position(Column.C, Row.Two) => Right(board.copy(c2  = Occupied(move.player)))
        case Position(Column.A, Row.Three) => Right(board.copy(a3  = Occupied(move.player)))
        case Position(Column.B, Row.Three) => Right(board.copy(b3  = Occupied(move.player)))
        case Position(Column.C, Row.Three) => Right(board.copy(c3  = Occupied(move.player)))
      }
    } else Left(PositionUnavailable(move.position))




  }




  def makeMove(board: Board, move: Move): Either[TTTServiceError, (Board, GameResult)] = {

    for {
      newBoard <- boardAfterMove(board, move)
      gameResult <- gameResult(newBoard)
    } yield (newBoard, gameResult)

  }


}
