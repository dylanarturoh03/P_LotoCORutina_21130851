package com.example.loto.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoteriaViewModels: ViewModel() {
    private val _lotoNumbers = mutableStateOf(emptyList<Int>())
    val lotoNumbers: State<List<Int>> = _lotoNumbers
    var isLoading by mutableStateOf(false)

    private suspend fun generateLotoNumbers()
    {
        //_lotoNumbers.value=(1 .. 60).shuffled().take(6).sorted() genera una lista de seis numeros aleatorios de entre el 1 al 60 y se imprimen de manera ordenada segun su valor
        val generatedNumbers = mutableListOf<Int>()

        for (i in 1..6) {
            val newNumber = (1..60).filter { it !in generatedNumbers }.random()
            generatedNumbers.add(newNumber)
            generatedNumbers.sort()

            // Update the state with the current list of generated numbers
            _lotoNumbers.value = generatedNumbers.toList()

            delay(2000)
        }
    }

    fun llamarAPI(){
        viewModelScope.launch {
            isLoading = true
            try{
                generateLotoNumbers()
            }catch (e:Exception){
                print("Error ${e.message}")
            }finally {
                isLoading = false
            }
        }
    }
}


