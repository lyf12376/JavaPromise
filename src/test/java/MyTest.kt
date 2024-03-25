import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import pub.telephone.javapromise.async.kpromise.Promise
import pub.telephone.javapromise.async.kpromise.job
import pub.telephone.javapromise.async.kpromise.process
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds

class MyTest {

    @Test
    fun test() {
        runBlocking {
            val promise = Promise {
                delay(1000) // 模拟一个异步任务
                rsv(123) // 任务完成，返回结果
            }

            val promise1 = Promise {
                delay(1000) // 模拟一个异步任务
                rsv(123) // 任务完成，返回结果
            }
            val a = measureTimeMillis {
                val sum = promise.result().toString().toInt() + promise1.result().toString().toInt()
            }

            println(a)

//            val job = process {
//                promise1.next{
//                    println(value)
//                }
//            }.awaitSettled()


//            val job = process {
////                val x = async<String> {
////
////                }.await()
//
//                promise.then {
//                    delay(1 .seconds)
//                    rsv(value)
//                }.next {
//                    println(value)
//                }
//
//                race(promise1, promise, promise1).next {
//                    println(value)
//                }.recover {
//                    delay(4 .seconds)
//                    reason.printStackTrace()
//                }.forCancel<Any> {
//                    println("cancelled here")
//                }
//            }.awaitSettled()
//
////            job.cancel()
//
////            process {
////                race(promise, promise1)
////            }
//
//        } }
        }
    }
}
