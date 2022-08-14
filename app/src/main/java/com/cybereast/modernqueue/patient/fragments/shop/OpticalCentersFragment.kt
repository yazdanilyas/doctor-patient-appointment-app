package com.cybereast.modernqueue.patient.fragments.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.FragmentOpticalCentersBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OpticalCentersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding:FragmentOpticalCentersBinding

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
        mBinding=FragmentOpticalCentersBinding.inflate(inflater,container,false)

        mBinding.noorClinicCV.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_opticalCentersFragment_to_noorClinicFragment)
        }
        mBinding.eyeCareClinicCV.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_opticalCentersFragment_to_eyeCareClinicFragment)
        }
        return mBinding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OpticalCentersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}