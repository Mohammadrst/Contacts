package com.example.contacts.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.contacts.data.model.contacts
import com.example.contacts.databinding.AddDialogLayoutBinding

class addDialog(var onAddClicked: OnAddClicked) : DialogFragment() {
    private var _binding: AddDialogLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
        _binding = AddDialogLayoutBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        dialog.setView(binding.root)

        binding.addBtn.setOnClickListener(View.OnClickListener {
            var name = binding.edtName.text.trim()
            var phone = binding.edtPhone.text.trim()

                if (name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(requireContext(),"Name or Phone is Empty",Toast.LENGTH_LONG).show()
                }else{
                    var newContact = contacts(name.toString(), phone.toString())
                    onAddClicked.OnClicked(newContact)
                    dismiss()
                }
            })

        binding.cancelBtn.setOnClickListener(View.OnClickListener {
            dismiss()
        })

        return dialog.create()
    }

    interface OnAddClicked{
        fun OnClicked(contacts: contacts)
    }
}