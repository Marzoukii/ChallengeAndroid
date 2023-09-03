package com.example.challengeandroid.ui.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.challengeandroid.R
import com.example.challengeandroid.data.Login.model.UserDataModel
import com.example.challengeandroid.data.Resource
import com.example.challengeandroid.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match

/**
 * A [LoginFragment] Fragment responsible for handling user login.
 * * This fragment allows users to enter their username and proceed with the login process.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val username = binding.TextviewUsername.text.toString()

            if (username.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_field_message),
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                lifecycleScope.launch {
                    val response = viewModel.fetchUserData(username)
                    handleResponse(response)
                }
            }
        }
    }

    private fun handleResponse(response: Resource<UserDataModel>) {
        when (response) {
            is Resource.Success -> {
                val login = response.data.login
                val bundle = Bundle().apply {
                    putString("Login", login)
                }
                findNavController().navigate(
                    R.id.action_loginFragment_to_listeDepotsFragment,
                    bundle,
                )
            }

            is Resource.Error -> {
                val errorMessage = response.message ?: getString(R.string.unknown_error_message)
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
