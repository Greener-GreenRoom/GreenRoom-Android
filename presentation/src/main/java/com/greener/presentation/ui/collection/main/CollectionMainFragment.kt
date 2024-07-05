package com.greener.presentation.ui.collection.main

import androidx.fragment.app.viewModels
import com.greener.presentation.databinding.FragmentCollectionMainBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionMainFragment : BaseFragment<FragmentCollectionMainBinding> (
    FragmentCollectionMainBinding::inflate,
) {
    private val viewModel: CollectionMainViewModel by viewModels()
}
