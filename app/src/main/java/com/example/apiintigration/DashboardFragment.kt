package com.example.apiintigration

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
import com.example.apiintigration.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        // Logout Button Click Listener
        binding.dashLoggoutBtn.setOnClickListener {
            MockDb.handleLogout(sharedPreferences)

            // Create NavOptions to clear back stack
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.LoginFragment, true)
                .build()

            findNavController().navigate(R.id.LoginFragment, null, navOptions)
        }

        // New Random Joke Button Click Listener
        binding.randomJokeButton.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_RandomJokeFragment)
        }

        // Random MTG Card Button Click Listener
        binding.randomMtgButton.setOnClickListener {
            findNavController().navigate(R.id.action_DashboardFragment_to_RandomMtgCardFragment)
        }

        // Handle back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "You are already logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
