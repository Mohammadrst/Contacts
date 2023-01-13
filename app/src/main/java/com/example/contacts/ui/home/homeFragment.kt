package com.example.contacts.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.provider.ContactsContract.RawContacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.data.model.contacts
import com.example.contacts.data.viewmodel.HomeViewModel
import com.example.contacts.databinding.FragmentHomeBinding
import kotlinx.coroutines.awaitAll
import org.koin.androidx.viewmodel.ext.android.viewModel


class homeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter : rv_adapter
    lateinit var addDialog: addDialog
    private val homeViewModel by viewModel<HomeViewModel>()
    val TAG = "Response"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivHomeFragmentLoadData.setOnClickListener(View.OnClickListener {
            getContactsSetup()
        })

        binding.ivHomeFragmentDeleteAll.setOnClickListener(View.OnClickListener {
            deleteAllContacts()
        })

        binding.fabHomeFragmentAdd.setOnClickListener(View.OnClickListener {
            addDialog = addDialog(object : addDialog.OnAddClicked {
                override fun OnClicked(contacts: contacts) {
                    homeViewModel.addContact(contacts)
                    getAllContactsFromDatabase()
                }

            })
            addDialog.show(parentFragmentManager, null)
        })

        longClickHandler()

    }

    private fun deleteAllContacts() {
        homeViewModel.deleteAllContacts()
        mAdapter.contacts = ArrayList()
    }

    private fun getContactsSetup() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.CALL_PHONE
                ),
                100
            )
        } else {
            binding.progrssHomeFragment.visibility = View.VISIBLE
            Thread(Runnable {
                homeViewModel.deleteAllContacts()
                var mList = homeViewModel.getAllContactsFromPhone(requireContext())
                for (item in mList) {
                    homeViewModel.addContact(item)
                }
                requireActivity().runOnUiThread {
                    getAllContactsFromDatabase()
                    binding.progrssHomeFragment.visibility = View.GONE
                }
            }).start()

        }
    }

    private fun getAllContactsFromDatabase() {
        mAdapter.contacts = homeViewModel.getAllContacts() as ArrayList<contacts>
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvHomeFragment.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun longClickHandler(){
        mAdapter = rv_adapter(object : rv_adapter.OnLongItemClicked{
            @SuppressLint("NotifyDataSetChanged")
            override fun OnLong(contacts: contacts) {
                homeViewModel.deleteContact(contacts)
                getAllContactsFromDatabase()
            }
        })
    }

    private fun importContacts() {
        Thread(Runnable {
            for (i in 1..700) {
                var name = ""
                var phone = ""
                if (i < 10) {
                    name = "Contact 0${i}"
                    phone = "0" + i.toString()
                } else {
                    name = "Contact ${i}"
                    phone = i.toString()
                }
                AddtoContact(name, phone)
            }
        }).start()
    }

    private fun AddtoContact(name: String, phone: String) {
        val p = ContentValues()
        p.put(RawContacts.ACCOUNT_TYPE, "com.google")
        p.put(RawContacts.ACCOUNT_NAME, "email")
        val rowcontect: Uri? = requireContext().contentResolver.insert(RawContacts.CONTENT_URI, p)
        val rawcontectid = ContentUris.parseId(rowcontect!!)

        val value = ContentValues()
        value.put(RawContacts.Data.RAW_CONTACT_ID, rawcontectid)
        value.put(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
        value.put(StructuredName.DISPLAY_NAME, name)
        requireContext().contentResolver.insert(ContactsContract.Data.CONTENT_URI, value)

        //adding the contents to the data

        //adding the contents to the data
        val ppv = ContentValues()
        ppv.put(ContactsContract.Data.RAW_CONTACT_ID, rawcontectid)
        ppv.put(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
        ppv.put(Phone.NUMBER, "975657789")
        ppv.put(Phone.TYPE, Phone.TYPE_MOBILE)
        requireContext().contentResolver.insert(ContactsContract.Data.CONTENT_URI, ppv)
    }

    @SuppressLint("Range")
    private fun deleteAll() {
        var contentResolver = requireContext().contentResolver
        var cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor!!.moveToNext()) {
            var lookupKey =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            var uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
            contentResolver.delete(uri, null, null);
        }
    }

}