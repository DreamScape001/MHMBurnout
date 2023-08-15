package com.burnout.mhmburnout.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.burnout.mhmburnout.R
import com.burnout.mhmburnout.databinding.FragmentMoodCheckerBinding
import com.burnout.mhmburnout.model.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoodCheckerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoodCheckerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMoodCheckerBinding? = null
    private val binding get() = _binding!!
    private var action = MoodCheckerFragmentDirections.actionMoodCheckerToHomeFragment(mood = "mid")
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mood_checker, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
             val positivenessChipIds = binding.positiveChipGroup.checkedChipIds.size
             val negativenessChipIds = binding.negativeChipGroup.checkedChipIds.size

             if(positivenessChipIds - negativenessChipIds > 1){
                 action = MoodCheckerFragmentDirections.actionMoodCheckerToHomeFragment(mood = "happy")
                 MaterialAlertDialogBuilder(requireContext())
                     .setTitle("Stay Positive....")
                     .setMessage(viewModel.getPositiveQuote())
                     .setCancelable(true)
                     .show()
             }else if(positivenessChipIds - negativenessChipIds == 1){
                 action = MoodCheckerFragmentDirections.actionMoodCheckerToHomeFragment(mood = "a bit happy")
                 MaterialAlertDialogBuilder(requireContext())
                     .setTitle("You'll be alright....")
                     .setMessage(viewModel.getNegativeQuote())
                     .setCancelable(true)
                     .show()
             }
             else if(negativenessChipIds - positivenessChipIds > 1){
                 action = MoodCheckerFragmentDirections.actionMoodCheckerToHomeFragment(mood = "sad")
                 MaterialAlertDialogBuilder(requireContext())
                     .setTitle("Don't give up....")
                     .setMessage(viewModel.getNegativeQuote())
                     .setCancelable(true)
                     .show()
             }else if(negativenessChipIds - positivenessChipIds == 1){
                 action = MoodCheckerFragmentDirections.actionMoodCheckerToHomeFragment(mood = "a bit sad")

                 MaterialAlertDialogBuilder(requireContext())
                     .setTitle("You'll be alright....")
                     .setMessage(viewModel.getNegativeQuote())
                     .setCancelable(true)
                     .show()
             } else if(negativenessChipIds == positivenessChipIds){
                 action = MoodCheckerFragmentDirections.actionMoodCheckerToHomeFragment(mood = "just okay")
                 MaterialAlertDialogBuilder(requireContext())
                     .setTitle("You'll be alright....")
                     .setMessage(viewModel.getNegativeQuote())
                     .setCancelable(true)
                     .show()
             }
         }
         binding.floatingActionButton.setOnClickListener {
             findNavController().navigate(action)
         }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoodCheckerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoodCheckerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}