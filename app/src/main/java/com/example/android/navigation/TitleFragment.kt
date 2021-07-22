package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//      Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )

//        Navigation can create a function for the OnClickListener for convenience
//        binding.playButton.setOnClickListener{view: View ->
//        view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment) }
        binding.playButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        )

        setHasOptionsMenu(true)
        return binding.root
//        This is how the boiler plate code inflates and binds the layout
//        return inflater.inflate(R.layout.fragment_title, container, false)
    }

//    inflate the overflow_menu we created as the OptionsMenu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, this.requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}