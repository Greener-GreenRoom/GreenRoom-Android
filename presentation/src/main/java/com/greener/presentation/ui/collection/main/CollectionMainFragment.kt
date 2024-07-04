package com.greener.presentation.ui.collection.main

import androidx.fragment.app.viewModels
import com.greener.presentation.databinding.FragmentCollectionBinding
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionMainFragment : BaseFragment<FragmentCollectionBinding> (
    FragmentCollectionBinding::inflate,
) {
    private val viewModel: CollectionMainViewModel by viewModels()
}
