package com.cybereast.modernqueue.patient.fragments.eyetest

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.FragmentAcuityTestBinding
import com.cybereast.modernqueue.utils.DialogUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AcuityTestFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentAcuityTestBinding
    private val testImages = arrayOf(
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,
        R.drawable.img_6,
        R.drawable.img_7,
        R.drawable.img_8,
        R.drawable.img_9,
        R.drawable.img_10
    )
    private var count = 0
    private var points = 0
    private lateinit var message: String

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
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentAcuityTestBinding.inflate(inflater, container, false)
        setListeners()
        return mBinding.root
    }

    private fun setListeners() {
        mBinding.stopBtn.setOnClickListener {
            val result = "Your result is $points/20"
            if (points >= 18) {
                message = activity?.getString(R.string.positive_result).toString()
            } else {
                message = activity?.getString(R.string.negative_result).toString()
            }
            DialogUtils.eyeTestResultDialog(requireActivity(), result, message,
                object : DialogUtils.ButtonClickListener {
                    override fun onPositiveClick(dialog: AlertDialog) {
                        val navController =
                            requireActivity().findNavController(R.id.fragmentContainer)
                        navController.popBackStack()
                    }

                    override fun onNegativeClick(dialog: AlertDialog) {
                    }


                })
        }
        mBinding.nextBtn.setOnClickListener {
            count++
            points += 2
            if (count < testImages.size)
                mBinding.testImg.setImageResource(testImages[count])
            else {
                val result = "Your result is $points/20"
                if (points >= 18) {
                    message = activity?.getString(R.string.positive_result).toString()
                } else {
                    message = activity?.getString(R.string.negative_result).toString()
                }
                DialogUtils.eyeTestResultDialog(requireActivity(), result, message,
                    object : DialogUtils.ButtonClickListener {
                        override fun onPositiveClick(dialog: AlertDialog) {
                            val navController =
                                requireActivity().findNavController(R.id.fragmentContainer)
                            navController.popBackStack()
                        }

                        override fun onNegativeClick(dialog: AlertDialog) {
                        }


                    })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AcuityTestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}