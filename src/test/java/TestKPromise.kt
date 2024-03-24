import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import pub.telephone.javapromise.async.kpromise.job
import pub.telephone.javapromise.async.kpromise.promise
import java.util.*
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class TestKPromise {
    val time get() = Calendar.getInstance().time

    @Test
    fun test() {
        job {
            println("【${time}】hello world")
            //
            promise {
                delay(2.seconds)
                rsv(666)
            }.next {
                try {
                    withTimeout(2.seconds) {
                        coroutineContext
                        try {
                            delay(10.seconds)
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: Throwable) {
                    throw e
                }
                rsv("echo $value to the world")
            }.next {
                println("【${time}】$value")
                rsv(Unit)
            }.next {
                delay(4.seconds)
                throw Throwable()
                rsv("【${time}】say hello to the world")
            }.catch<Any?> {
                try {
                    delay(10.seconds)
                } catch (e: Throwable) {
//                    println("$e")
                    e.printStackTrace()
                }
                println("【${time}】caught $reason")
                throw IllegalStateException()
            }.forCancel<Any?> {
                try {
//                    delay(1.seconds)
                } catch (e: Throwable) {
//                    println("$e")
                    e.printStackTrace()
                }
                println("【${time}】cancelled here 1")
            }.catch<Any?> {
                println("【${time}】caught $reason")
                throw reason
            }.finally {
                try {
                    delay(10.seconds)
                } catch (e: Throwable) {
//                    println("$e")
                    e.printStackTrace()
                }
                println("【${time}】finally here $isActive")
                rsp(promise {
                    delay(1.seconds)
                    rsp(promise {
                        println("调用A")
                        delay(1.seconds)
                        println("调用B")
                        rej(IndexOutOfBoundsException())
                    })
                })
            }.then {
                rsv(value as String)
            }.next {
                println("【${time}】say $value to the world ${value.length}")
                rsv(null)
            }.catch<String> {
                println("【${time}】caught $reason")
                rsv("bbb")
            }.next {
                println("【${time}】say $value to the world ${value.length}")
                rsv(null)
            }.forCancel<Any?> {
                try {
                    delay(1.seconds)
                } catch (e: Throwable) {
//                    println("$e")
                    e.printStackTrace()
                }
                println("【${time}】cancelled here 2")
            }
            GlobalScope.launch {
                delay(8500.milliseconds)
                this@job.cancel()
            }
        }
        runBlocking { delay(60.seconds) }
    }

    suspend fun aa() {
        coroutineContext
    }
}