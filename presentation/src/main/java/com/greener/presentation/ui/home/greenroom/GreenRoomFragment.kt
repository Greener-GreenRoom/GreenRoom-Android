package com.greener.presentation.ui.home.greenroom

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.greener.domain.model.ActionTodo
import com.greener.domain.model.greenroom.GreenRoomTotalInfo
import com.greener.presentation.databinding.FragmentGreenRoomBinding
import com.greener.presentation.model.UiState
import com.greener.presentation.ui.base.BaseFragment
import com.greener.presentation.ui.home.dialog.ActionDialog
import com.greener.presentation.ui.home.dialog.LevelUpDialog
import com.greener.presentation.ui.home.toast.CompleteTodoToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GreenRoomFragment constructor(
    private val myGreenRoom: GreenRoomTotalInfo,
    private val position: Int,
    val onChangedTodo: (Int, ActionTodo) -> Unit,
) : BaseFragment<FragmentGreenRoomBinding>(
    FragmentGreenRoomBinding::inflate,
) {

    @Inject
    lateinit var greenRoomViewModelFactory: GreenRoomViewModel.GreenRoomViewModelFactory

    private val viewModel: GreenRoomViewModel by viewModels {
        GreenRoomViewModel.providesFactory(
            assistedFactory = greenRoomViewModelFactory,
            greenRoom = myGreenRoom,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.greenRoom = viewModel.myGreenRoom.value
        binding.todoNum = viewModel.myGreenRoom.value!!.greenRoomTodos.size
        binding.lifecycleOwner = this

        binding.includeGreenRoomBalloon1.cardTextBalloon.setOnClickListener {
            val actionTodo = binding.includeGreenRoomBalloon1.actionTodo as ActionTodo
            showActionDialog(actionTodo, binding.includeGreenRoomBalloon1.root)
        }
        binding.includeGreenRoomBalloon2.cardTextBalloon.setOnClickListener {
            val actionTodo = binding.includeGreenRoomBalloon2.actionTodo as ActionTodo
            showActionDialog(actionTodo, binding.includeGreenRoomBalloon2.root)
        }

        binding.includeGreenRoomBalloon3.cardTextBalloon.setOnClickListener {
            val actionTodo = binding.includeGreenRoomBalloon3.actionTodo as ActionTodo
            showActionDialog(actionTodo, binding.includeGreenRoomBalloon3.root)
        }

        binding.includeGreenRoomBalloon4.cardTextBalloon.setOnClickListener {
            val actionTodo = binding.includeGreenRoomBalloon4.actionTodo as ActionTodo
            showActionDialog(actionTodo, binding.includeGreenRoomBalloon4.root)
        }

        binding.includeGreenRoomBalloon5.cardTextBalloon.setOnClickListener {
            val actionTodo = binding.includeGreenRoomBalloon5.actionTodo as ActionTodo
            showActionDialog(actionTodo, binding.includeGreenRoomBalloon5.root)
        }
        observeIncreasingPoint()
        observeIsLevelUp()
        observeUiState()
    }

    private fun observeIncreasingPoint() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.increasingPoint.collect {
                    if (it > 0) {
                        CompleteTodoToast.createToast(requireActivity(), it)!!.show()
                        viewModel.resetIncreasingPoint()
                    }
                }
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it is UiState.Error) {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun completeAllTodo() {
        showActionDialog(ActionTodo.COMPLETE_ALL, null)
    }

    private fun observeIsLevelUp() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.level.collect {
                if (it > 0) {
                    showLevelUpDialog(it)
                }
            }
        }
    }

    private fun showActionDialog(actionTodo: ActionTodo, view: View?) {
        val dialog = ActionDialog(requireActivity(), actionTodo)
        dialog.setItemClickListener(object : ActionDialog.ClickListener {
            override fun onClick() {
                viewModel.completeTodo(actionTodo)
                observeCompleteTodo(actionTodo, view)
            }
        })
        dialog.show()
    }

    private fun observeCompleteTodo(actionTodo: ActionTodo, view: View?) {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it == UiState.Success) {
                    hideTextBalloon(view)
                    onChangedTodo(position, actionTodo)
                    setPlantFace(binding.ivGreenRoomPlantFace, myGreenRoom.greenRoomTodos.size)
                    this.cancel()
                } else if (it is UiState.Error) {
                    this.cancel()
                }
            }
        }
    }

    private fun hideTextBalloon(view: View?) {
        if (view == null) {
            binding.includeGreenRoomBalloon1.root.visibility = View.GONE
            binding.includeGreenRoomBalloon2.root.visibility = View.GONE
            binding.includeGreenRoomBalloon3.root.visibility = View.GONE
            binding.includeGreenRoomBalloon4.root.visibility = View.GONE
            binding.includeGreenRoomBalloon5.root.visibility = View.GONE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    private fun showLevelUpDialog(level: Int) {
        val dialog = LevelUpDialog(requireActivity(), level) {
            viewModel.resetIsLevelUp()
        }
        dialog.show()
    }
}
