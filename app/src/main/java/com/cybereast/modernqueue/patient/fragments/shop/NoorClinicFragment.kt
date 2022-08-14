package com.cybereast.modernqueue.patient.fragments.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cybereast.modernqueue.databinding.FragmentNooClinicBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NoorClinicFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentNooClinicBinding

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
        mBinding = FragmentNooClinicBinding.inflate(inflater, container, false)
        setListeners()
        return mBinding.root
    }

    private fun setListeners() {
        mBinding.amesButton.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
        mBinding.candanceBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
        mBinding.groveBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
        mBinding.kitchenerBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoorClinicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}