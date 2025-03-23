package eg.iti.mad.sharedandstateflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


suspend fun main(): Unit = coroutineScope {
    val sharedFlow = MutableSharedFlow<Int>()

    launch {
        sharedFlow.collect{
            println("First: $it")
        }
    }

    launch {
        sharedFlow.collect{
            println("Second: $it")
        }
    }

    sharedFlow.emit(3)
    sharedFlow.emit(5)

    launch {
        sharedFlow.collect{
            println("Third: $it")
        }
    }

    println("Done")


}


//suspend fun main(): Unit = coroutineScope {
//
//    val coroutineScope = CoroutineScope(Dispatchers.Default)
//    val sharedFlow = flow<Int> {
//        emit(5)
//        emit(7)
//        emit(9)
//    }.shareIn(coroutineScope, SharingStarted.Lazily)
//
//    launch {
//        sharedFlow.collect{
//            println("I'm converted Flow: $it")
//        }
//    }
//
//}