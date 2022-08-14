package com.cybereast.modernqueue.doctor.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.constants.Constants
import com.cybereast.modernqueue.databinding.ActivityForgotPasswordBinding
import com.cybereast.modernqueue.listeners.ValidationListener
import com.cybereast.modernqueue.models.Doctor
import com.cybereast.modernqueue.utils.AppUtils
import com.cybereast.modernqueue.utils.InputValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class ForgotPasswordActivity : AppCompatActivity(), ValidationListener {
    private lateinit var mBinding: ActivityForgotPasswordBinding
    private var fireStoreDbRef = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setListeners()
        setContentView(mBinding.root)
    }

    private fun setListeners() {
        mBinding.verifyEmailBtn.setOnClickListener {
            validateFields(mBinding.userEmailEt)
        }
        mBinding.upDatePasswordBtn.setOnClickListener {
            updatePassword()
        }
    }

    private fun updatePassword() {
        mBinding.progressBar.visibility = View.VISIBLE
        val auth=FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(mBinding.userEmailEt.text.toString()).addOnCompleteListener {
            if (it.isSuccessful){
                mBinding.progressBar.visibility = View.GONE
                AppUtils.showToast(this@ForgotPasswordActivity,"Reset Password email sent to your email")
            }
        }

    }


    override fun onError(editText: AppCompatEditText) {
        editText.error = getString(R.string.required_field)
    }

    override fun onSuccess() {
        if (InputValidation.isValidEmail(this, mBinding.userEmailEt)) {
            verifyEmail()
        }
    }

    private fun verifyEmail() {
        mBinding.progressBar.visibility = View.VISIBLE
        fireStoreDbRef.collection(Constants.COLLECTION_DOCTORS).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    searchEmail(document, mBinding.userEmailEt.text.toString())
                    Log.d("TAGG", "${document.id} => ${document.data}")
                }
            }.addOnFailureListener { exception ->
                AppUtils.showToast(this@ForgotPasswordActivity, exception.message.toString())
            }

    }

    private fun searchEmail(document: QueryDocumentSnapshot?, searchQuery: String) {
        fireStoreDbRef.collection(Constants.COLLECTION_DOCTORS).document(document?.id.toString())
            .collection(Constants.COLLECTION_PROFILE).get().addOnSuccessListener {
                val docRefId = it.documents.get(0).id

                val documentRef = fireStoreDbRef.collection(Constants.COLLECTION_DOCTORS)
                    .document(document?.id.toString()).collection(Constants.COLLECTION_PROFILE)
                    .document(docRefId)
                documentRef.get().addOnSuccessListener { values ->
                    mBinding.progressBar.visibility = View.GONE
                    val doc = values.toObject(Doctor::class.java)
                    if (doc?.email.equals(searchQuery)) {
                        mBinding.upDatePasswordBtn.visibility = View.VISIBLE
                        mBinding.verifyEmailBtn.visibility = View.GONE
//                        mBinding.userEmailEt.isEnabled = false
                    }
//                    else {
//                        Toast.makeText(
//                            this@ForgotPasswordActivity,
//                            "Email is not registered with us",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
                    Log.d("TAGG", "$searchQuery,searchEmail: ${doc?.email}")
                }
            }

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