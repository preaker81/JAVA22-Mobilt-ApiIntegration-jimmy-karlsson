package com.example.apiintigration

// Android and Jetpack libraries
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apiintigration.data.MockDb
import com.example.apiintigration.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    // Declare a variable for the View Binding of this fragment
    private lateinit var binding: FragmentLoginBinding

    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Perform any further actions once the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if the user is already logged in, if so navigate to Dashboard
        if (MockDb.isLoggedIn) {
            findNavController().navigate(R.id.DashboardFragment)
            return
        }

        // Handle the click event for the login button
        binding.loginButton.setOnClickListener {
            if (isValidCredentials()) {
                // Log successful login and navigate to Dashboard
                Log.d("LoginFragment", "Valid credentials. Navigating to Dashboard.")
                findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
            } else {
                // Log unsuccessful login and show Snackbar with error message
                Log.d("LoginFragment", "Invalid credentials.")
                Snackbar.make(view, "Invalid username or password.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    // Validate the user's login credentials
    private fun isValidCredentials(): Boolean {
        val username = binding.userNameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Check login using MockDb
        return MockDb.checkLoggIn(username, password, sharedPreferences)
    }
}
