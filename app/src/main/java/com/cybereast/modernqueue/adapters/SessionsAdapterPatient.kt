package com.cybereast.modernqueue.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.ItemSessionRecyclerDoctorBinding
import com.cybereast.modernqueue.listeners.RecyclerItemClickListener
import com.cybereast.modernqueue.models.Session

class SessionsAdapterPatient(
    private val sessionList: ArrayList<Session>,
    private val mRecyclerListener: RecyclerItemClickListener
) :
        RecyclerView.Adapter<SessionsAdapterPatient.SessionViewHolder>() {
    private lateinit var mBinding: ItemSessionRecyclerDoctorBinding

    class SessionViewHolder(val binding: ItemSessionRecyclerDoctorBinding) :
            RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_session_recycler_doctor, parent, false)
        return SessionViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.binding.obj = sessionList[position]
        holder.binding.sessionItem.setOnClickListener {
            mRecyclerListener.onClick(sessionList[position], position)
        }
        holder.binding.bookingButton.setOnClickListener {
            mRecyclerListener.onItemChildClick(it, sessionList[position])
        }


    }

    override fun getItemCount(): Int {
        return sessionList.size
    }
}