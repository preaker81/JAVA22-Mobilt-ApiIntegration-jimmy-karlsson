package com.example.apiintigration

// Android and Jetpack libraries
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.apiintigration.data.MockDb
import com.example.apiintigration.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    // Declare a variable for FragmentDashboardBinding
    private lateinit var binding: FragmentDashboardBinding

    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Perform further initialization after the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get shared preferences
        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Handle Logout button click
        binding.dashLoggoutBtn.setOnClickListener {
            // Logout using MockDb
            MockDb.handleLogout(sharedPreferences)

            // Create NavOptions to clear the navigation back stack
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.LoginFragment, true)
                .build()

            // Navigate to LoginFragment
            findNavController().navigate(R.id.LoginFragment, null, navOptions)
        }

        // Handle click for new Random Joke button
        binding.randomJokeButton.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_RandomJokeFragment)
        }

        // Handle click for Random MTG Card button
        binding.randomMtgButton.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_RandomMtgCardFragment)
        }

        // Handle back press to display Toast message
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "You are already logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
