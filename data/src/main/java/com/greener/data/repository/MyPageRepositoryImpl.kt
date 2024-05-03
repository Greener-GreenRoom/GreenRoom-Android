package com.greener.data.repository

import com.greener.data.source.remote.MyPageDataSource
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val dataSource: MyPageDataSource
) {

}
