package com.cybereast.modernqueue.doctor.ui.fragments.prescription

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.constants.Constants
import com.cybereast.modernqueue.databinding.FragmentSuggestPrescriptionBinding
import com.cybereast.modernqueue.listeners.ValidationListener
import com.cybereast.modernqueue.models.Booking
import com.cybereast.modernqueue.models.Prescription
import com.cybereast.modernqueue.utils.AppUtils
import com.cybereast.modernqueue.utils.CommonKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class SuggestPrescriptionFragment : Fragment(), ValidationListener {
    private lateinit var mBinding: FragmentSuggestPrescriptionBinding
    private lateinit var mBooking: Booking
    private var fireStoreDbRef = FirebaseFirestore.getInstance()
    private lateinit var doctorDocumentRef: DocumentReference
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSuggestPrescriptionBinding.inflate(inflater, container, false)
        getBundleData()
        setListeners()
        return mBinding.root
    }

    private fun setListeners() {
        mBinding.suggestPrescriptionBtn.setOnClickListener {
            validateFields(
                mBinding.sphLeftET,
                mBinding.sphRightET,
                mBinding.cylLeftET,
                mBinding.cylRightET,
                mBinding.axisLeftET,
                mBinding.axisRightET,
                mBinding.addLeftET,
                mBinding.addRightET,
                mBinding.prescriptionEt
            )
        }

        mBinding.sphPlusImg.setOnClickListener {
            val textStr = mBinding.sphLeftET.text.toString()
            mBinding.sphLeftET.setText("+$textStr")
        }
        mBinding.sphMinusImg.setOnClickListener {
            val textStr = mBinding.sphLeftET.text.toString()
            mBinding.sphLeftET.setText("-$textStr")

        }
        mBinding.sphRightMinusImg.setOnClickListener {
            val textStr = mBinding.sphRightET.text.toString()
            mBinding.sphRightET.setText("-$textStr")
        }
        mBinding.sphRightPlusImg.setOnClickListener {
            val textStr = mBinding.sphRightET.text.toString()
            mBinding.sphRightET.setText("+$textStr")
        }


        mBinding.cylPlusImg.setOnClickListener {
            val textStr = mBinding.cylLeftET.text.toString()
            mBinding.cylLeftET.setText("+$textStr")
        }
        mBinding.cylMinusImg.setOnClickListener {
            val textStr = mBinding.cylLeftET.text.toString()
            mBinding.cylLeftET.setText("-$textStr")

        }
        mBinding.cylRightMinusImg.setOnClickListener {
            val textStr = mBinding.cylRightET.text.toString()
            mBinding.cylRightET.setText("-$textStr")
        }
        mBinding.cylRightPlusImg.setOnClickListener {
            val textStr = mBinding.cylRightET.text.toString()
            mBinding.cylRightET.setText("+$textStr")
        }


        mBinding.axisLeftMinusImg.setOnClickListener {
            val textStr = mBinding.axisLeftET.text.toString()
            mBinding.axisLeftET.setText("-$textStr")
        }
        mBinding.axisLeftPlusImg.setOnClickListener {
            val textStr = mBinding.axisLeftET.text.toString()
            mBinding.axisLeftET.setText("+$textStr")
        }
        mBinding.axisRightMinusImg.setOnClickListener {
            val textStr = mBinding.axisRightET.text.toString()
            mBinding.axisRightET.setText("-$textStr")
        }
        mBinding.axisRightPlusImg.setOnClickListener {
            val textStr = mBinding.axisRightET.text.toString()
            mBinding.axisRightET.setText("+$textStr")
        }


        mBinding.addLeftMinusImg.setOnClickListener {
            val textStr = mBinding.addLeftET.text.toString()
            mBinding.addLeftET.setText("-$textStr")
        }
        mBinding.addLeftPlusImg.setOnClickListener {
            val textStr = mBinding.addLeftET.text.toString()
            mBinding.addLeftET.setText("+$textStr")
        }
        mBinding.addRightPlusImg.setOnClickListener {
            val textStr = mBinding.addRightET.text.toString()
            mBinding.addRightET.setText("+$textStr")
        }
        mBinding.addRightMinusImg.setOnClickListener {
            val textStr = mBinding.addRightET.text.toString()
            mBinding.addRightET.setText("-$textStr")
        }
    }

    private fun suggestPrescription() {
        val sphLeft = mBinding.sphLeftET.text.toString()
        val sphRight = mBinding.sphRightET.text.toString()
        val cylLeft = mBinding.cylLeftET.text.toString()
        val cylRight = mBinding.cylRightET.text.toString()
        val axisLeft = mBinding.axisLeftET.text.toString()
        val axisRight = mBinding.axisRightET.text.toString()
        val addLeft = mBinding.addLeftET.text.toString()
        val addRight = mBinding.addRightET.text.toString()
        val suggestPrescription = mBinding.prescriptionEt.text.toString()
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())

        val prescription = Prescription(
            sphLeft,
            sphRight,
            cylLeft,
            cylRight,
            axisLeft,
            axisRight,
            addLeft,
            addRight,
            suggestPrescription,
            currentDate.toString(),
            mBooking.bookingId.toString()
        )


        if (mBooking.bookingId != null) {

            doctorDocumentRef =
                fireStoreDbRef.collection(Constants.COLLECTION_PATIENT_BOOKING)
                    .document(mBooking.bookingId.toString()).collection(
                        Constants.COLLECTION_PRESCRIPTION
                    ).document()
            doctorDocumentRef.set(prescription).addOnSuccessListener {
                AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)

//                    fireStoreDbRef.collection(Constants.COLLECTION_DOCTORS).document(uId)
//                        .set(dummyMap)
                AppUtils.showToast(
                    requireContext(),
                    "Prescription Added"
                )
            }.addOnFailureListener {
                AppUtils.showHideProgressBar(mBinding.progressBar, View.GONE)
                AppUtils.showToast(requireContext(), it.message.toString())
                Log.d("TAG", "suggest prescription: ${it.message}")
            }
        }
    }

    private fun getBundleData() {
        arguments.let {
            mBooking = it?.getSerializable(CommonKeys.KEY_DATA) as Booking
        }

    }

    companion object {
        fun newInstance() = SuggestPrescriptionFragment()
        fun newInstance(mBundle: Bundle) = SuggestPrescriptionFragment().apply {
            arguments = mBundle
        }
    }

    override fun onError(editText: AppCompatEditText) {
        editText.error = getString(R.string.required_field)
    }

    override fun onSuccess() {
        suggestPrescription()
    }

    private fun validateFields(vararg editText: AppCompatEditText) {
        var isValid = true
        for (et in editText) {
            if (TextUtils.isEmpty(et.text)) {
                isValid = false
                onError(et)
            }
        }
        if (isValid) {
            onSuccess()
        }
    }

}