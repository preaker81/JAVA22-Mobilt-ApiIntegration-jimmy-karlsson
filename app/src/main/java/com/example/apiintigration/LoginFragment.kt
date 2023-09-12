package com.example.apiintigration

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apiintigration.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        if (MockDb.isLoggedIn) {
            findNavController().navigate(R.id.DashboardFragment)
            return
        }

        binding.loginButton.setOnClickListener {
            if (isValidCredentials()) {
                Log.d("LoginFragment", "Valid credentials. Navigating to Dashboard.")
                findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
            } else {
                Log.d("LoginFragment", "Invalid credentials.")
                Snackbar.make(view, "Invalid username or password.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun isValidCredentials(): Boolean {
        val username = binding.userNameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        return MockDb.checkLoggIn(username, password, sharedPreferences)
    }
}
