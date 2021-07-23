package com.example.android.navigation

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.android.navigation.databinding.FragmentAudioVideoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AudioVideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioVideoFragment : Fragment() {

    lateinit var soundPool: SoundPool
    var applauseID = 0
    var silenceID = 0
    var streamID = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAudioVideoBinding>(
            inflater, R.layout.fragment_audio_video, container, false
        )

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(5).build()
        soundPool.setOnLoadCompleteListener { soundPool: SoundPool, i: Int, i1: Int ->
            this.soundPool.play(silenceID, 1f, 1f, 1, 0, 1.0f)
        }

        applauseID = soundPool.load(context, R.raw.applause, 1)
        silenceID = soundPool.load(context, R.raw.silence, 1)

        // Setup a onCLick Listener for playAudioButton
        binding.playAudioButton.setOnClickListener {
//            Toast.makeText(context, "Play Audio", Toast.LENGTH_LONG).show()

            // Toggle Audio on or off
            if(streamID != 0) {
                soundPool.stop(streamID)
                streamID = 0
            } else {
                streamID = soundPool.play(applauseID, 1f, 1f, 1, 0, 1.0f)
            }
        }

        return binding.root
    }

}
