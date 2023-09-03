package com.example.challengeandroid.ui.ListeDepots // ktlint-disable package-name

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroid.R
import com.example.challengeandroid.data.ListeDepots.adapter.ListDepotsAdapter
import com.example.challengeandroid.data.Resource
import com.example.challengeandroid.databinding.FragmentListeDepotsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
/**
 * A simple [ListeDepotsFragment] Fragment responsible for displaying a list
 * of user repositories.
 */
@AndroidEntryPoint
class ListeDepotsFragment : Fragment() {
    private lateinit var binding: FragmentListeDepotsBinding
    private val viewModel: ListDepotsViewModel by viewModels()
    private lateinit var listDepotsAdapter: ListDepotsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListeDepotsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listDepotsAdapter = ListDepotsAdapter()
        binding.RecylerViewRepos.adapter = listDepotsAdapter
        val userName = arguments?.getString("Login") ?: ""
        loadUserRepositories(userName)
        observeRepositories()
        setupSearch()
        setupItemClickListener()
    }

    private fun loadUserRepositories(userName: String) {
        viewModel.fetchUserRepositories(userName)
    }

    private fun observeRepositories() {
        viewModel.reposLiveData.observe(
            viewLifecycleOwner,
            Observer { resource ->
                when (resource) {
                    is Resource.Success -> {
                        listDepotsAdapter.submitList(resource.data)
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

    private fun setupSearch() {
        binding.EditTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                viewModel.searchRepositories(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        viewModel.filteredRepos.observe(
            viewLifecycleOwner,
            Observer { filteredRepos ->
                listDepotsAdapter.submitList(filteredRepos)
            },
        )
    }

    private fun setupItemClickListener() {
        binding.RecylerViewRepos.setOnItemClickListener { position, clickedRepoView ->
            handleItemClick(position)
        }
    }

    private fun handleItemClick(position: Int) {
        val clickedRepo = listDepotsAdapter.currentList.getOrNull(position)
        clickedRepo?.let { repo ->
            val userName = repo.owner.login
            val repoName = repo.name
            val bundle = Bundle().apply {
                putString("repoName", repoName)
                putString("userName", userName)
            }
            findNavController().navigate(
                R.id.action_listeDepotsFragment_to_detaisDepotFragment,
                bundle,
            )
        }
    }

    fun RecyclerView.setOnItemClickListener(itemClickListener: (position: Int, view: View) -> Unit) {
        val gestureDetector =
            GestureDetectorCompat(
                context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        val childView = findChildViewUnder(e.x, e.y)
                        if (childView != null) {
                            val position = getChildAdapterPosition(childView)
                            if (position != RecyclerView.NO_POSITION) {
                                itemClickListener(position, childView)
                                return true
                            }
                        }
                        return false
                    }
                },
            )

        addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(e)
            }
        })
    }
}
