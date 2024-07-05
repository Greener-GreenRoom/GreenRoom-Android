package com.greener.presentation.ui.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB,
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = bindingFactory(inflater, container, false)
        return binding.run {
            lifecycleOwner = viewLifecycleOwner
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initCollector()
    }

    protected open fun initListener() {
    }

    protected open fun initCollector() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun softInputAdjustResize2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.setDecorFitsSystemWindows(false)
            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val topInset = insets.getInsets(WindowInsets.Type.statusBars()).top
                val imeHeight = insets.getInsets(WindowInsets.Type.ime()).bottom
                val navigationHeight = insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                val bottomInset = if (imeHeight == 0) navigationHeight else imeHeight
                binding.root.setPadding(0, topInset, 0, bottomInset)
                insets
            }
        } else {
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}
