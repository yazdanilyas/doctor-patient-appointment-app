package com.cybereast.modernqueue.utils

import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("setCheckedStatus")
fun setCheckedStatus(switch: SwitchCompat, isChecked: Boolean) {
    switch.isChecked = isChecked

}

@BindingAdapter("setPresText")
fun setPresText(textView: TextView, value: String) {
    textView.text = value.toString()
}