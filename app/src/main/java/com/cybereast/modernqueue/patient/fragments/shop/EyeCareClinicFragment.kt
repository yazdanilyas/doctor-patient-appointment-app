package com.cybereast.modernqueue.patient.fragments.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cybereast.modernqueue.databinding.FragmentEyeCareClinicBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EyeCareClinicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EyeCareClinicFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mBinding: FragmentEyeCareClinicBinding

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
        mBinding = FragmentEyeCareClinicBinding.inflate(layoutInflater, container, false)
        setListeners()
        return mBinding.root
    }

    private fun setListeners() {
        mBinding.adairBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
        mBinding.saxonBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
        mBinding.seanBtn.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Order Placed successfully",
                Toast.LENGTH_LONG
            ).show()
        }
        mBinding.virgoBtn.setOnClickListener {
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
            EyeCareClinicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}