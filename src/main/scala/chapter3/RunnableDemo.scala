package chapter3


import scala.actors.threadpool.{Callable, ExecutorService, Executors, FutureTask}

/**
	* Runnable/Callable
	* 区别：Runnable无返回值，Callable线程执行完有返回值
	*/
object RunnableDemo {
	def main(args: Array[String]) {
		//创建线程池
		val threadPool: ExecutorService = Executors.newFixedThreadPool(5)
		try {
			//提交5个线程
			for (i <- 1 to 5) {
				//threadPool.submit(new ThreadDemo("thread"+i))
				threadPool.execute(new ThreadDemo("thread" + i))
			}
		} finally {
			threadPool.shutdown()
		}
	}

	//定义线程类，每打印一次睡眠100毫秒
	class ThreadDemo(threadName: String) extends Runnable {
		override def run() {
			for (i <- 1 to 10) {
				println(threadName + "|" + i)
				Thread.sleep(100)
			}
		}
	}

}

object CallableDemo {
	def main(args: Array[String]) {
		val threadPool: ExecutorService = Executors.newFixedThreadPool(3)
		try {
			val future = new FutureTask(new Callable[String] {
				override def call(): String = {
					Thread.sleep(100)
					return "im result"
				}
			})
			threadPool.execute(future)
			println(future.get())
		} finally {
			threadPool.shutdown()
		}
	}
}