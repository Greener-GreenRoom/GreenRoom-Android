package com.greener.presentation.ui.home.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greener.domain.model.ActionTodo
import com.greener.domain.model.ApiState
import com.greener.domain.model.ExampleModel
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.domain.usecase.greenroom.CompleteTodoUseCase
import com.greener.domain.usecase.greenroom.GetUserGreenRoomListUseCase
import com.greener.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserGreenRoomListUseCase: GetUserGreenRoomListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val _myGreenRooms = MutableStateFlow<List<GreenRoomTotalInfo>>(listOf())
    val myGreenRooms: MutableStateFlow<List<GreenRoomTotalInfo>> get() = _myGreenRooms


    private val _currentGreenRoom = MutableStateFlow<GreenRoomTotalInfo?>(null)
    val currentGreenRoom: StateFlow<GreenRoomTotalInfo?> get() = _currentGreenRoom


    private val _isFabOpen = MutableStateFlow(false)
    val isFabOpen: MutableStateFlow<Boolean> get() = _isFabOpen

    fun changeTodo(position: Int, actionTodo: ActionTodo) {
        if(actionTodo == ActionTodo.COMPLETE_ALL) {
            _myGreenRooms.value[position].greenRoomTodos.clear()
            return
        }
        _myGreenRooms.value[position].greenRoomTodos.removeIf {
            it.actionTodo == actionTodo
        }
    }
    fun getUserGreenRoomsInfo() {
        viewModelScope.launch {
            val result = getUserGreenRoomListUseCase()
            when (result) {
                is ApiState.Success -> {
                    _uiState.update { UiState.Success }
                    _currentGreenRoom.update { result.result?.greenRoomsTotalInfo?.get(0) }
                    _myGreenRooms.value = result.result?.greenRoomsTotalInfo ?: emptyList()
                    Log.d("확인", result.result.toString())
                }

                is ApiState.Fail -> {
                    _uiState.update { UiState.Fail }
                    Log.d("확인", result.result.toString())
                }

                is ApiState.Exception -> {
                    _uiState.update { UiState.Error(result.checkException()) }
                    Log.d("확인", "init에서 " + result.checkException())
                }
            }
        }
    }

    fun getCountsToString(): String {
        return _myGreenRooms.value.size.toString()
    }

    fun isAnyGreenRooms(): Boolean {
        return _myGreenRooms.value.isNotEmpty()
    }

    fun getCounts(): Int {
        return _myGreenRooms.value.size
    }

    fun setIsFabOpen() {
        isFabOpen.value = !isFabOpen.value
    }

    fun initFab() {
        isFabOpen.value = false
    }

    fun updateCurrentGreenRoom(position: Int) {
        _currentGreenRoom.update { myGreenRooms.value[position] }
    }
}