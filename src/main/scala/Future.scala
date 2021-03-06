import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * 2つのThread.sleepは別で動作している
 * 実行して1秒後Hello future
 * 5秒後に2回めのprintln(f.isCompleted)が実行される
 */
object Future extends App {

  val s = "Hello"
  val f: Future[String] = Future {
    Thread.sleep(1000)
    s + " future!"
  }

  f.foreach { case s: String =>
    println(s)
  }

  println(f.isCompleted) // false

  Thread.sleep(5000) // Hello future!

  println(f.isCompleted) // true

  val f2: Future[String] = Future {
    Thread.sleep(1000)
    throw new RuntimeException("わざと失敗")
  }

  f2.failed.foreach { case e: Throwable =>
    println(e.getMessage)
  }

  println(f2.isCompleted) // false

  Thread.sleep(5000) // わざと失敗

  println(f2.isCompleted) // true
}