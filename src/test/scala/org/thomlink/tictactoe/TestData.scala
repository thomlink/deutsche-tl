package org.thomlink.tictactoe

import cats.Show
import org.scalacheck.Gen
import org.thomlink.tictactoe.model.{Board, Free, O, Occupied, Player, X}

object TestData {

  def topRowWinner(winner: Player): Board = {
    winner match {
      case model.X => Board(
        a1 = Occupied(X), b1 = Occupied(X), c1 = Occupied(X), a2 = Occupied(O), b2 = Free, c2 = Occupied(O), a3 = Free, b3 = Free, c3 = Free
      )
      case model.O => Board(
        a1 = Occupied(O), b1 = Occupied(O), c1 = Occupied(O), a2 = Occupied(X), b2 = Free, c2 = Occupied(X), a3 = Occupied(X), b3 = Free, c3 = Free
      )
    }
  }

  def middleRowWinner(winner: Player): Board = {
    winner match {
      case model.X => Board(
        a1 = Free, b1 = Free, c1 = Occupied(O), a2 = Occupied(X), b2 = Occupied(X), c2 = Occupied(X), a3 = Occupied(O), b3 = Free, c3 = Free
      )
      case model.O => Board(
        a1 = Occupied(O), b1 = Occupied(O), c1 = Occupied(O), a2 = Occupied(X), b2 = Free, c2 = Occupied(X), a3 = Occupied(X), b3 = Free, c3 = Free
      )
    }
  }

  def bottomRowWinner(winner: Player): Board = {
    winner match {
      case model.X => Board(
        a1 = Free, b1 = Free, c1 = Free, a2 = Free, b2 = Occupied(O), c2 = Occupied(O), a3 = Occupied(X), b3 = Occupied(X), c3 = Occupied(X)
      )
      case model.O => Board(
        a1 = Free, b1 = Free, c1 = Occupied(X), a2 = Free, b2 = Occupied(X), c2 = Occupied(X), a3 = Occupied(O), b3 = Occupied(O), c3 = Occupied(O))
    }
  }

  def upwardDiagonalWinner(player: model.Player): Board = player match {
    case model.X => Board(
      a1 = Occupied(X), b1 = Free, c1 = Free, a2 = Free, b2 = Occupied(X), c2 = Occupied(O), a3 = Occupied(O), b3 = Free, c3 = Occupied(X)
    )
    case model.O => Board(
      a1 = Occupied(O), b1 = Occupied(X), c1 = Free, a2 = Free, b2 = Occupied(O), c2 = Occupied(X), a3 = Occupied(X), b3 = Free, c3 = Occupied(O)
    )
  }


  val rotateBoardClockwise90: Board => Board = { oldBoard =>
    Board(
      a1 = oldBoard.a3, b1 = oldBoard.a2, c1 = oldBoard.a1, a2 = oldBoard.b3, b2 = oldBoard.b2, c2 = oldBoard.b1, a3 = oldBoard.c3, b3 = oldBoard.c2, c3 = oldBoard.c1
    )
  }

  def leftColumnWinner(winner: Player): Board = rotateBoardClockwise90(bottomRowWinner(winner))

  def middleColumnWinner(winner: Player): Board = rotateBoardClockwise90(middleRowWinner(winner))

  def rightColumnWinner(winner: Player): Board = rotateBoardClockwise90(topRowWinner(winner))

  def downwardDiagonalWinner(winner: model.Player): Board = rotateBoardClockwise90(upwardDiagonalWinner(winner))

  def winningBoardGen(winner: Player): Gen[Board] = Gen.oneOf(
    topRowWinner(winner),
    middleRowWinner(winner),
    bottomRowWinner(winner),
    leftColumnWinner(winner),
    middleColumnWinner(winner),
    rightColumnWinner(winner),
    downwardDiagonalWinner(winner),
    upwardDiagonalWinner(winner)
  )


  implicit val showBoard: Show[Board] = Show.fromToString


  //XOX  XOX  OXX  XXO
  //OOX  XOO  XOO  OOX
  //XXO  OXX  XOX  XOX
  val drawGame: Board = Board(
    a1 = Occupied(X), b1 = Occupied(O), c1 = Occupied(X), a2 = Occupied(O), b2 = Occupied(O), c2 = Occupied(X), a3 = Occupied(X), b3 = Occupied(X), c3 = Occupied(O)
  )

  val incompleteGame: Board = Board(
    a1 = Occupied(X), b1 = Occupied(O), c1 = Occupied(X), a2 = Occupied(O), b2 = Occupied(O), c2 = Occupied(X), a3 = Occupied(X), b3 = Free, c3 = Free
  )

  val incompleteGame2: Board = Board(
    a1 = Occupied(X), b1 = Occupied(O), c1 = Occupied(X), a2 = Free, b2 = Free, c2 = Free, a3 = Free, b3 = Free, c3 = Free
  )
  //---

  val multipleWinners: Board = Board(
    a1 = Occupied(X), b1 = Occupied(X), c1 = Occupied(X), a2 = Occupied(O), b2 = Occupied(O), c2 = Occupied(O), a3 = Free, b3 = Free, c3 = Free
  )


  val invalidBoardXs: Board = Board(
    a1 = Occupied(X), b1 = Occupied(X), c1 = Occupied(X), a2 = Occupied(O), b2 = Free, c2 = Free, a3 = Free, b3 = Free, c3 = Free
  )

  val invalidBoardOs: Board = Board(
    a1 = Occupied(O), b1 = Occupied(O), c1 = Occupied(X), a2 = Occupied(O), b2 = Free, c2 = Free, a3 = Free, b3 = Free, c3 = Free
  )

  val invalidBoardOMovesAfterLoss: Board = Board(
    a1 = Occupied(X), b1 = Occupied(X), c1 = Occupied(X), a2 = Occupied(O), b2 = Occupied(O), c2 = Free, a3 = Occupied(O), b3 = Free, c3 = Free
  )

  val invalidBoardXMovesAfterLoss: Board = Board(
    a1 = Occupied(X), b1 = Occupied(X), c1 = Occupied(O), a2 = Occupied(X), b2 = Occupied(X), c2 = Occupied(O), a3 = Free, b3 = Free, c3 = Occupied(O)
  )

  val unbalancedMovesGen = Gen.oneOf(
    invalidBoardXs,
    invalidBoardOs
  )

  val movedAfterLossGen = Gen.oneOf(
    invalidBoardXMovesAfterLoss,
    invalidBoardOMovesAfterLoss
  )


}
