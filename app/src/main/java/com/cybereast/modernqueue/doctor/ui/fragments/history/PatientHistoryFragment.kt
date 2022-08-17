package com.cybereast.modernqueue.doctor.ui.fragments.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.adapters.BookingAdapter
import com.cybereast.modernqueue.constants.Constants
import com.cybereast.modernqueue.databinding.FragmentPatientHistoryBinding
import com.cybereast.modernqueue.listeners.RecyclerItemClickListener
import com.cybereast.modernqueue.models.Booking
import com.cybereast.modernqueue.utils.AppUtils
import com.cybereast.modernqueue.utils.CommonKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PatientHistoryFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var fireStoreDbRef = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var mAdapter: BookingAdapter
    private lateinit var mBinding: FragmentPatientHistoryBinding
    private lateinit var mViewModel: PatientHistoryViewModel

    private var mRecyclerListener = object : RecyclerItemClickListener {
        override fun onClick(data: Any?, position: Int) {
//            val session = data as Session
//            val bundle = Bundle()
//            Log.d("TAG", "onClick: ${session.sessionId}")
        }

        override fun onItemChildClick(view: View, data: Any?) {
            val booking = data as Booking
            openPrescriptionFragment(view, booking)
        }

        override fun onSeeProfile(data: Any?, position: Int) {

        }

    }

    private fun openPrescriptionFragment(view: View, booking: Booking) {
        val popUp = PopupMenu(requireActivity(), view)
        popUp.menuInflater.inflate(R.menu.menu_prescription, popUp.menu)
        popUp.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_suggest_prescription -> {
                    val bundle = Bundle()
                    bundle.putSerializable(CommonKeys.KEY_DATA, booking)
                    findNavController().navigate(
                        R.id.action_navigation_history_to_suggestPrescriptionFragment,
                        bundle
                    )
                }
                R.id.menu_see_pres_history -> {
                    val bundle = Bundle()
                    bundle.putSerializable(CommonKeys.KEY_DATA, booking)
                    findNavController().navigate(
                        R.id.action_navigation_history_to_prescriptionHistoryFragment2,
                        bundle
                    )
                }
            }
            true
        }
        popUp.show()

    }

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
        mBinding = FragmentPatientHistoryBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(PatientHistoryViewModel::class.java)
        getMyBookings()
        return mBinding.root
    }

    private fun getMyBookings() {
        AppUtils.showHideProgressBar(mBinding.progressBar, View.VISIBLE)
        fireStoreDbRef.collection(Constants.COLLECTION_PATIENT_BOOKING)
            .whereEqualTo("uid", mAuth.currentUser?.uid)
//            .whereEqualTo("sessionId", mViewModel.sessionId)
            .whereEqualTo("bookingStatus", "CONSULTED")
            .get()
            .addOnSuccessListener { snapshot ->
                Log.d("TAG", "getMyBookings: " + snapshot.size())
                if (snapshot.size() > 0) {
                    for (snap in snapshot) {
                        val booking = snap.toObject(Booking::class.java)
                        if (mViewModel.bookingList.size > 0)
                            mViewModel.bookingList.clear()
                        mViewModel.bookingList.add(booking)
                        Log.d("TAG", "getMyBookings: $booking")
                    }
                    mBinding.noDataTv.visibility = View.GONE
                    AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                    setUpRecycler()
                } else {
                    mBinding.noDataTv.visibility = View.VISIBLE
                    AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                }
            }
    }

    private fun setUpRecycler() {
        mAdapter =
            BookingAdapter(mViewModel.bookingList, mRecyclerListener)
        mBinding.historyRecycler.layoutManager =
            LinearLayoutManager(activity)
        mBinding.historyRecycler.setHasFixedSize(true)
        mBinding.historyRecycler.adapter = mAdapter
    }

    /*  private fun getBundleData() {
          arguments.let {
              mViewModel.sessionId = it?.getString(CommonKeys.KEY_SESSION_ID)
  //            Log.d(TAG, "getBundleData: ")
          }
      }*/
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}