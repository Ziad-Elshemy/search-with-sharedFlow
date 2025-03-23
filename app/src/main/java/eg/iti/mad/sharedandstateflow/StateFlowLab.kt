package eg.iti.mad.sharedandstateflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun main():Unit =  coroutineScope{

    val stateFlow = MutableStateFlow<Int>(0)

    stateFlow.emit(1)
    stateFlow.emit(2)
    stateFlow.emit(3)
    stateFlow.emit(4)
    stateFlow.emit(5)
//    stateFlow.value = 6


    launch {
        stateFlow.collect{
            println(it)
        }

//        println(stateFlow.value)
    }


}