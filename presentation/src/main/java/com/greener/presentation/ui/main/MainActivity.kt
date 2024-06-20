package com.greener.presentation.ui.main

import android.os.Bundle
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.greener.domain.usecase.image.PickImageUseCase
import com.greener.domain.usecase.image.TakePictureUseCase
import com.greener.presentation.R
import com.greener.presentation.databinding.ActivityMainBinding
import com.greener.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate,
) {

    @Inject
    lateinit var pickImageUseCase: PickImageUseCase

    @Inject
    lateinit var takePictureUseCase: TakePictureUseCase

    private lateinit var navController: NavController
    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            setBottomNavigationVisibility(destination)
        }

    // bottom navigation이 표시되지 않는 fragment들 모음
    // todo 함수화 하기
    private val exceptBottomNavigationSet = listOf(
        R.id.registrationSearchFragment,
        R.id.registrationNicknameImageFragment,
        R.id.registrationWaterFragment,
        R.id.registrationCharacterFragment,
        R.id.registrationCompleteFragment,
        R.id.myPageMainFragment,
        R.id.myPageLevelFragment,
        R.id.editProfileFragment,
        R.id.userWithdrawReasonFragment,
        R.id.userWithdrawFinalFragment,
        R.id.editPushFragment,
        R.id.decorationFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }

    private fun setUpBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    private fun setBottomNavigationVisibility(destination: NavDestination) {
        binding.bottomNavigationViewMain.isGone = exceptBottomNavigationSet.contains(destination.id)
    }
}
