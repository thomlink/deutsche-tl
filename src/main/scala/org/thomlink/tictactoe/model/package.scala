package org.thomlink.tictactoe

package object model {

  sealed trait GameResult
  case class Winner(player: Player) extends GameResult
  case object Draw extends GameResult
  case object Incomplete extends GameResult


  sealed trait Player
  object Player {
    case object X extends Player
    case object O extends Player
  }
  sealed trait PositionState
  case class Occupied(player: Player) extends PositionState
  case object Free extends PositionState



  case class Board(
                  a1: PositionState,
                  b1: PositionState,
                  c1: PositionState,
                  a2: PositionState,
                  b2: PositionState,
                  c2: PositionState,
                  a3: PositionState,
                  b3: PositionState,
                  c3: PositionState
                  ) {

    def toList: List[PositionState] = List(a1, b1, c1, a2,b2,c2,a3,b3,c3)
  }

  object Board {
    val initial: Board = Board(
      a1 = Free, b1 = Free, c1 = Free, a2 = Free, b2 = Free, c2 = Free, a3 = Free, b3 = Free, c3 = Free
    )
  }


}
