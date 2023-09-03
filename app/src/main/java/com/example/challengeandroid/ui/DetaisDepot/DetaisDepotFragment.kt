package com.example.challengeandroid.ui.DetaisDepot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.challengeandroid.R
import com.example.challengeandroid.data.DetaisDepot.adapter.DetailsDepotAdapter
import com.example.challengeandroid.data.Resource
import com.example.challengeandroid.databinding.FragmentDetaisDepotBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
/**
 * A simple [DetaisDepotFragment] Fragment responsible
 * for displaying detailed information about a specific repository.
 */
@AndroidEntryPoint
class DetaisDepotFragment : Fragment() {
    private lateinit var binding: FragmentDetaisDepotBinding
    private val viewModel: DetaisDepotViewModel by viewModels()
    private lateinit var detaisDepotsAdapter: DetailsDepotAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetaisDepotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val UserName = arguments?.getString("userName") ?: ""
        val UserRopo = arguments?.getString("repoName") ?: ""
        viewModel.fetchRepositoryDetails(UserName, UserRopo)
        detaisDepotsAdapter = DetailsDepotAdapter()
        binding.RecylerViewDetais.adapter = detaisDepotsAdapter

        viewModel.reposDetaisData.observe(
            viewLifecycleOwner,
            Observer { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val repoData = resource.data
                        Timber.d("reposData $repoData")
                        val dataList = listOf(repoData)
                        detaisDepotsAdapter.submitList(dataList)
                    }

                    is Resource.Error -> {
                        val errorMessage =
                            resource.message ?: getString(R.string.unknown_error_message)
                        Timber.e("errorMessage $errorMessage")
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            },
        )
    }
}
