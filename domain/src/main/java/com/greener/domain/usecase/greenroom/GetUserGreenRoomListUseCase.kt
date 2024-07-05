package com.greener.domain.usecase.greenroom

import com.greener.domain.model.greenroom.UserGreenRoomsInfo
import com.greener.domain.repository.HomeGreenRoomRepository
import javax.inject.Inject

class GetUserGreenRoomListUseCase @Inject constructor(
    private val repository: HomeGreenRoomRepository,
) {
    suspend operator fun invoke(): Result<UserGreenRoomsInfo?> {
        return repository.getUserGreenRoomList()
    }
}
