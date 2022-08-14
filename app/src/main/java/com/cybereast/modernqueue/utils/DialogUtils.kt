package com.cybereast.modernqueue.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.models.Patient
import java.util.*


object DialogUtils {


    fun bookingDialog(activity: Activity, buttonListener: DialogButtonListener) {
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_booking_layout, null)
        val builder = AlertDialog.Builder(activity)
            .setTitle("Booking")
            .setCancelable(false)
            .setView(view)

        val patientName = view.findViewById<AppCompatEditText>(R.id.patientNameEt)
        val patientPhone = view.findViewById<AppCompatEditText>(R.id.patientPhoneEt)

        setDialogButtons(activity, builder, buttonListener)
        val alertDialog = builder.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (!TextUtils.isEmpty(patientName.text.toString()) && !TextUtils.isEmpty(patientName.text.toString())) {
                val patient = Patient(patientName.text.toString(), patientPhone.text.toString())
                buttonListener.onPositiveClick(patient)
                alertDialog.dismiss()
            } else {
                if (TextUtils.isEmpty(patientName.text.toString())) {
                    patientName.error = activity.getString(R.string.required_field)
                }
                if (TextUtils.isEmpty(patientPhone.text.toString())) {
                    patientPhone.error = activity.getString(R.string.required_field)
                }
            }
        }

    }

    fun eightEyeSignDialog(activity: Activity, buttonListener: DialogButtonListener?) {
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_layout_8_eye_signs, null)
        val builder = AlertDialog.Builder(activity)
            .setTitle("Eight Signs that shows you need glasses")
            .setCancelable(false)
            .setView(view).setIcon(R.drawable.ic_eye)
        builder.setPositiveButton(activity.getString(R.string.ok)) { dialog, id ->
            dialog.dismiss()
        }
        val alertDialog = builder.show()
    }

    fun eyeTestResultDialog(
        activity: Activity,
        result: String,
        message: String,
        buttonListener: ButtonClickListener?
    ) {
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_eye_test_result, null)
        val builder = AlertDialog.Builder(activity)
            .setCancelable(false)
            .setView(view)
        val finishTestBtn = view.findViewById<AppCompatButton>(R.id.finishTestBtn)
        val headerTv = view.findViewById<TextView>(R.id.headerTv)
        val messageTv = view.findViewById<TextView>(R.id.messageTv)
        headerTv.setText(result)
        messageTv.setText(message)
        val alertDialog = builder.show()
        finishTestBtn.setOnClickListener {
            alertDialog.dismiss()
            buttonListener?.onPositiveClick(alertDialog)
        }
    }

    fun addSessionDialog(
        activity: Activity,
        buttonListener: DialogButtonListener,
    ) {
        var time: String? = null
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_session_layout, null)
        val builder = AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.new_session))
            .setCancelable(false)
            .setView(view)

        val startTimeEt = view.findViewById<AppCompatEditText>(R.id.startTimeEt)
        val endTimeEt = view.findViewById<AppCompatEditText>(R.id.endTimeEt)
        val noOfTokens = view.findViewById<AppCompatEditText>(R.id.noOfTokens)

        setDialogButtons(activity, builder, buttonListener)
        val alertDialog = builder.show()

        startTimeEt.setOnClickListener {
            time = timePicker(activity)
            AppUtils.showToast(activity, "time" + time)
            startTimeEt.setText(time)
        }

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (!TextUtils.isEmpty(startTimeEt.text.toString())
                && !TextUtils.isEmpty(endTimeEt.text.toString())
                &&
                !TextUtils.isEmpty(
                    noOfTokens.text.toString()
                )
            ) {
//                val session = Session(
//                    startTimeEt.text.toString(),
//                    endTimeEt.text.toString(),
//                    Integer.parseInt(noOfTokens.text.toString())
//                )

                alertDialog.dismiss()
            } else {
                AppUtils.showToast(activity, activity.getString(R.string.missing_fields_message))
            }
        }

    }

    private fun setDialogButtons(
        context: Context,
        builder: AlertDialog.Builder,
        buttonListener: DialogButtonListener,

        ) {
        builder.setPositiveButton(context.getString(R.string.save_booking)) { dialog, id ->
        }
        builder.setNegativeButton(context.getString(R.string.cancel_str)) { dialog, id ->
            buttonListener.onNegativeClick(dialog)
            dialog.dismiss()
        }

    }

    fun timePicker(activity: Activity): String? {
        var time: String? = "10:00 AM"
        val c: Calendar = Calendar.getInstance()
        var mHour = c.get(Calendar.HOUR_OF_DAY)
        var mMinute = c.get(Calendar.MINUTE)

        // Launch Time Picker Dialog

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            activity, { view, hourOfDay, minute ->
                time = setTime12Hour(hourOfDay, minute)
            },
            mHour,
            mMinute,
            false
        )
        timePickerDialog.show()
        return time
    }

    private fun setTime12Hour(hoursOfDay: Int, minute: Int): String {
        var time: String? = null
        var amPm: String? = null
        var hourOfDay = hoursOfDay
        var AM_PM = "AM"
        var minPrecede = ""
        if (hourOfDay >= 12) {
            AM_PM = "PM"
            if (hourOfDay >= 13 && hourOfDay < 24) {
                hourOfDay -= 12
            } else {
                hourOfDay = 12
            }
        } else if (hourOfDay == 0) {
            hourOfDay = 12
        }

        if (minute < 10) {
            minPrecede = "0"
        }
        time = "$hourOfDay:$minPrecede$minute $amPm"
        amPm = AM_PM
        return time
    }


    interface DialogButtonListener {
        fun onPositiveClick(data: Any)
        fun onNegativeClick(dialog: DialogInterface)
    }

    interface ButtonClickListener {
        fun onPositiveClick(dialog: AlertDialog)
        fun onNegativeClick(dialog: AlertDialog)
    }

}