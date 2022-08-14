package com.cybereast.modernqueue.patient.fragments.moreFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.FragmentMorePBinding
import com.cybereast.modernqueue.doctor.ui.activities.LoginActivity
import com.cybereast.modernqueue.utils.ActivityUtils
import com.cybereast.modernqueue.utils.AppUtils
import com.cybereast.modernqueue.utils.DialogUtils
import com.google.firebase.auth.FirebaseAuth

class MoreFragmentP : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = MoreFragmentP()
        fun newInstance(pBundle: Bundle) = MoreFragmentP().apply {
            arguments = pBundle
        }
    }

    private lateinit var mBinding: FragmentMorePBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMorePBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        mBinding.signsCv.setOnClickListener(this)
        mBinding.glassyFyCv.setOnClickListener(this)
        mBinding.testCv.setOnClickListener(this)
        mBinding.shareAppCv.setOnClickListener(this)
        mBinding.rateAppCv.setOnClickListener(this)
        mBinding.logoutCv.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signsCv -> {
                DialogUtils.eightEyeSignDialog(requireActivity(), null)
            }
            R.id.testCv -> {
                Navigation.findNavController(view).navigate(R.id.action_moreFragmentP_to_eyeTestFragment)
            }
            R.id.glassyFyCv -> {
                Navigation.findNavController(view).navigate(R.id.action_menu_more_p_to_opticalCentersFragment)
            }
            R.id.shareAppCv -> {
                AppUtils.shareApp(requireActivity())
            }
            R.id.rateAppCv -> {
                AppUtils.rateApp(requireActivity())
            }
            R.id.logoutCv -> {
                logOut()
            }
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        ActivityUtils.startActivity(requireActivity(), LoginActivity::class.java)
        activity?.finish()
    }


}