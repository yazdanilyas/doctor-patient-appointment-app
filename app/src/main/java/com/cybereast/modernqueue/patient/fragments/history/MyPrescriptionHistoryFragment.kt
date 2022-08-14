package com.cybereast.modernqueue.patient.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cybereast.modernqueue.adapters.PrescriptionsAdapter
import com.cybereast.modernqueue.constants.Constants
import com.cybereast.modernqueue.databinding.FragmentMyPrescriptionHistoryBinding
import com.cybereast.modernqueue.models.Booking
import com.cybereast.modernqueue.models.Prescription
import com.cybereast.modernqueue.utils.AppUtils
import com.cybereast.modernqueue.utils.CommonKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class MyPrescriptionHistoryFragment : Fragment() {

    private lateinit var mBinding: FragmentMyPrescriptionHistoryBinding
    private lateinit var mBooking: Booking
    private var fireStoreDbRef = FirebaseFirestore.getInstance()
    private lateinit var doctorDocumentRef: DocumentReference
    private val mAuth = FirebaseAuth.getInstance()
    private var prescriptionList: ArrayList<Prescription> = ArrayList()
    private lateinit var mAdapter: PrescriptionsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMyPrescriptionHistoryBinding.inflate(inflater, container, false)
        getBundleData()
        getPrescriptionHistory()
        return mBinding.root
    }

    private fun getBundleData() {
        arguments.let {
            mBooking = it?.getSerializable(CommonKeys.KEY_DATA) as Booking
        }

    }

    private fun getPrescriptionHistory() {
        AppUtils.showHideProgressBar(mBinding.progressBar, View.VISIBLE)

        if (mAuth.currentUser?.uid != null) {
            fireStoreDbRef.collection(Constants.COLLECTION_PATIENT_BOOKING)
                .document(mBooking.bookingId.toString())
                .collection(Constants.COLLECTION_PRESCRIPTION).get().addOnSuccessListener {
                    if (!it.isEmpty) {
                        val prescription = it.toObjects(Prescription::class.java)
                        setPrescriptionData(prescription)
                        AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                    } else {
                        AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                    }

                }.addOnFailureListener {
                    AppUtils.showHideProgressBar(mBinding.progressBar, View.VISIBLE)
                    AppUtils.showToast(requireContext(), it.message.toString())
                }
        } else {
            AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
            AppUtils.showToast(requireContext(), "No records found")
        }
    }

    private fun setPrescriptionData(prescription: List<Prescription>) {
        mAdapter = PrescriptionsAdapter(prescription as ArrayList, null)
        mBinding.prescriptionRecycler.layoutManager =
            LinearLayoutManager(activity)
        mBinding.prescriptionRecycler.setHasFixedSize(true)
        mBinding.prescriptionRecycler.adapter = mAdapter
    }

    companion object {
        fun newInstance() = MyPrescriptionHistoryFragment()
        fun newInstance(mBundle: Bundle) = MyPrescriptionHistoryFragment().apply {
            arguments = mBundle
        }
    }
}