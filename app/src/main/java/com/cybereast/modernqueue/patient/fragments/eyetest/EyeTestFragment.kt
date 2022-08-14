package com.cybereast.modernqueue.patient.fragments.eyetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.FragmentEyeTestBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EyeTestFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mBinding: FragmentEyeTestBinding

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
        mBinding = FragmentEyeTestBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setListeners()
        return mBinding.root
    }

    private fun setListeners() {
        mBinding.leftBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_eyeTestFragment_to_acuityTestFragment)

        }
        mBinding.rightBtn.setOnClickListener {
           Navigation.findNavController(it).navigate(R.id.action_eyeTestFragment_to_acuityTestFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EyeTestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}