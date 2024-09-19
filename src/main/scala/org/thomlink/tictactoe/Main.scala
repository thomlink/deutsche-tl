package org.thomlink.tictactoe
import cats.effect.{ExitCode, IO, IOApp}


/*
Assumptions Made:

The board will never extend beyond 3x3
There will only ever be two players
Draw can only occur when the board is full
  ... otherwise it is incomplete


 */


object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    IO.println("Hello, World!").map(_ => ExitCode.Success)
}