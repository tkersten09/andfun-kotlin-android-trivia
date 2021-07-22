package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.android.navigation.databinding.FragmentAudioVideoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AudioVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioVideoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAudioVideoBinding>(
            inflater, R.layout.fragment_audio_video, container, false
        )

        // Setup a onCLick Listener for playAudioButton
        binding.playAudioButton.setOnClickListener {
            Toast.makeText(context, "Play Audio", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

}
