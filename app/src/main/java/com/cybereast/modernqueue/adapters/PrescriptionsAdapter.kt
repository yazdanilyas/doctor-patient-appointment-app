package com.cybereast.modernqueue.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cybereast.modernqueue.R
import com.cybereast.modernqueue.databinding.ItemPrescriptionHistoryBinding
import com.cybereast.modernqueue.listeners.RecyclerItemClickListener
import com.cybereast.modernqueue.models.Prescription

class PrescriptionsAdapter(
    private val prescriptionList: ArrayList<Prescription>,
    private val mRecyclerListener: RecyclerItemClickListener?
) :
        RecyclerView.Adapter<PrescriptionsAdapter.PrescriptionViewHolder>() {
    private lateinit var mBinding: ItemPrescriptionHistoryBinding

    class PrescriptionViewHolder(val binding: ItemPrescriptionHistoryBinding) :
            RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_prescription_history, parent, false)
        return PrescriptionViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        holder.binding.obj = prescriptionList[position]
        holder.binding.sessionItem.setOnClickListener {
            mRecyclerListener?.onClick(prescriptionList[position], position)
        }



    }

    override fun getItemCount(): Int {
        return prescriptionList.size
    }
}