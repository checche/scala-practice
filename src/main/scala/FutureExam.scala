/**
 * 待ち時間が無いとFutureの実行が完了しない場合がある。
 */

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object FutureExam extends App {

  val s = "Hello"
  val f: Future[String] = Future {
    println("f start")
    Thread.sleep(1000)
    println("1000ms past")
    s + " future!"
  }

  f.foreach { case s: String =>
    println(s)
  }

  println(f.isCompleted) // false

  //Thread.sleep(5000) // Hello future!

  println(f.isCompleted) // true

  val f2: Future[String] = Future {
    println("f2 start")
    Thread.sleep(1000)
    throw new RuntimeException("わざと失敗")
  }

  f2.failed.foreach { case e: Throwable =>
    println(e.getMessage)
  }

  println(f2.isCompleted) // false

  //Thread.sleep(5000) // わざと失敗

  println(f2.isCompleted) // true
}