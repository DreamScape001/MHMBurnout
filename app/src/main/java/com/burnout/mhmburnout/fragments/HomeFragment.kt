package com.burnout.mhmburnout.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.burnout.mhmburnout.R
import com.burnout.mhmburnout.adapter.RecyclerListAdapter
import com.burnout.mhmburnout.databinding.FragmentHomeBinding
import com.burnout.mhmburnout.model.HomeViewModel
import com.burnout.mhmburnout.sharedpreference.prefs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var currentMood: String = "happy"
    private var _binding: FragmentHomeBinding?  = null
    private val binding
        get() = _binding!!
    private val maxDay = 14
    private val viewModel: HomeViewModel by activityViewModels()

    val action = HomeFragmentDirections.actionHomeFragmentToMoodChecker()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            currentMood = it.getString(MOOD).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = prefs(requireContext())
        val day = pref.getDay()
        val date = pref.getDate()
        val cal = Calendar.getInstance()
        cal.clear(Calendar.HOUR)
        cal.clear(Calendar.HOUR_OF_DAY)
        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)
        val now = cal.timeInMillis
        val diffInTime = now - date
        if(!pref.getBool()){
            pref.saveDate(now)
            update()
        }else{
            if(diffInTime >= (3600000 * 24)) {
                pref.saveDay(day+1)
                pref.saveDate(now)
                update()
            } else {
                update()
            }
            binding.imageButton.setOnClickListener {
                findNavController().navigate(action)
            }
        }
    }

    private fun update(){
        val pref = prefs(requireContext())
        val loadTasks = viewModel.load(pref.getDay())
        if(pref.getDay() > maxDay){
            binding.finished.isVisible = true
            binding.restartButton.setOnClickListener {
                restart()
            }
        } else {
            binding.finished.isVisible = false
            val recyclerListAdapter = RecyclerListAdapter(requireContext(), loadTasks) {
                MaterialAlertDialogBuilder(requireContext())
                    .setCancelable(true)
                    .setTitle(it.name)
                    .setMessage(it.description)
                    .show()
            }
            binding.myAdapter = recyclerListAdapter
        }
    }

    fun restart() {
        val cal = Calendar.getInstance()
        cal.clear(Calendar.HOUR)
        cal.clear(Calendar.HOUR_OF_DAY)
        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)
        val now = cal.timeInMillis
        val pref = prefs(requireContext())
        pref.saveDay(1)
        pref.saveDate(now)
        binding.finished.isVisible = false
        viewModel.load(pref.getDay())
        update()
    }

    override fun onResume() {
        super.onResume()
        when(currentMood){
            "happy" -> binding.imageButton.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
            "sad" -> binding.imageButton.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
            "just okay" -> binding.imageButton.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
            "a bit happy" -> binding.imageButton.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
            "a bit sad" -> binding.imageButton.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
            "mid" -> binding.imageButton.setImageResource(R.drawable.ic_baseline_mood_24)
        }
        val pref = prefs(requireContext())
        if(!pref.getBool()){
            var cal = Calendar.getInstance()
            cal.clear(Calendar.HOUR)
            cal.clear(Calendar.HOUR_OF_DAY)
            cal.clear(Calendar.MINUTE)
            cal.clear(Calendar.SECOND)
            cal.clear(Calendar.MILLISECOND)
            val now = cal.timeInMillis
            pref.saveDate(now)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Important")
                .setMessage("Full disclaimer. I am not a mental health professional and if you are experiencing any mental health issues, I strongly suggest you pay a visit to a professional. There are 5 little tasks displayed everyday for the total of 2weeks. Note that you will not miss any day as the app does not count days that it hasn't been opened.")
                .setPositiveButton("Continue"){ _,_ ->
                    pref.saveBool(true)
                }
                .setCancelable(false)
                .show()
        }
    }

    companion object {
        const val MOOD = "mood"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}