package com.example.contacts.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.data.model.contacts
import com.example.contacts.databinding.RvSampleBinding

class rv_adapter(var onLongItemClicked: OnLongItemClicked) : RecyclerView.Adapter<rv_adapter.myViewHolder>(){

    var contacts = ArrayList<contacts>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class myViewHolder(var binding: RvSampleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(mContact: contacts){
            binding.tvName.text = mContact.name
            binding.tvPhone.text = mContact.phone
            binding.cardviewRvSample.setOnLongClickListener(View.OnLongClickListener {
                onLongItemClicked.OnLong(mContact)
                return@OnLongClickListener true
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(RvSampleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.bind(contacts[position])

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    interface OnLongItemClicked{
        fun OnLong(contacts: contacts)
    }
}