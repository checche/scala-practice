import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FutureAwait extends App {

  val s = "Hello"
  val f: Future[String] = Future {
    Thread.sleep(1000)
    println(s"[ThreadName] In Future: ${Thread.currentThread.getName}")
    s + " future!"
  }

  f.foreach { case s: String =>
    println(s"[ThreadName] In Success: ${Thread.currentThread.getName}")
    println(s)
  }

  println(f.isCompleted) // false

  Await.ready(f, 5000.millisecond) // Hello future!
  //Thread.sleep(5000)
  println(f.isCompleted) // true
  /**
   * 1秒後にfの実行完了
   * その後にf.foreachとprintln(InApp)が実行されるが
   * Await.ready から戻ってきた直後の処理が、 foreach の中身より（ふつうは）先に実行されるから
   * 今回はprintlnの方が先に実行される
   */
  println(s"[ThreadName] In App: ${Thread.currentThread.getName}")
  println(f.isCompleted) // true
}