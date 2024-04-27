package com.greener.presentation.ui.home.greenroom

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.ActionTodo
import com.greener.domain.model.ApiState
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.domain.usecase.greenroom.CompleteTodoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GreenRoomViewModel @AssistedInject constructor(
    @Assisted private val greenRoom: GreenRoomTotalInfo,
    private val completeTodoUseCase: CompleteTodoUseCase
) : ViewModel() {

    private val _myGreenRoom = MutableStateFlow<GreenRoomTotalInfo?>(null)
    val myGreenRoom: StateFlow<GreenRoomTotalInfo?> get() = _myGreenRoom


    private val _increasingPoint = MutableStateFlow(0)
    val increasingPoint: StateFlow<Int> get() = _increasingPoint

    private val _level = MutableStateFlow(0)
    val level: StateFlow<Int> get() = _level

    init {
        _myGreenRoom.value = greenRoom
    }

    fun completeTodo(actionTodo: ActionTodo) {
        val todoList = mutableListOf<Int>()
        if (actionTodo == ActionTodo.COMPLETE_ALL) {
            _myGreenRoom.value!!.greenRoomTodos.forEach {
                todoList.add(it.actionTodo.actionId)
            }

        } else {
            todoList.add(actionTodo.actionId)
        }

        viewModelScope.launch {
            val response = completeTodoUseCase(
                _myGreenRoom.value!!.greenRoomInfo.greenRoomBaseInfo.greenroomId,
                todoList
            )
            when (response) {
                is ApiState.Success -> {
                    Log.d("확인", "Success: ${response.result}")
                    _increasingPoint.emit(response.result!!.increasingPoint)
                    if (response.result!!.isLevelUpdated) {
                        _level.emit(response.result!!.level)
                    }
                }
                //TODO 에러 처리
                is ApiState.Fail -> {
                    Log.d("확인", "Fail: ${response.result}")
                }

                is ApiState.Exception -> {
                    Log.d("확인", "Exception: ${response.t}")
                }
            }
        }
    }

    fun resetIncreasingPoint() {
        _increasingPoint.value = 0
    }

    fun resetIsLevelUp() {
        _level.value = 0
    }

    @AssistedFactory
    interface GreenRoomViewModelFactory {
        fun create(greenRoom: GreenRoomTotalInfo): GreenRoomViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: GreenRoomViewModelFactory,
            greenRoom: GreenRoomTotalInfo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(greenRoom) as T
            }
        }
    }
}
