package com.greener.domain.usecase.greenroom

import com.greener.domain.model.ApiState
import com.greener.domain.model.greenroom.TodoCompleteInfo
import com.greener.domain.repository.HomeGreenRoomRepository
import javax.inject.Inject

class CompleteTodoUseCase @Inject constructor(
    private val repository: HomeGreenRoomRepository
) {
    suspend operator fun invoke(id:Int, todoList:List<Int>): ApiState<TodoCompleteInfo> {
        return repository.completeTodo(id,todoList)
    }
}