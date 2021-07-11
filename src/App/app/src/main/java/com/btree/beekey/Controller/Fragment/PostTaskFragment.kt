package com.btree.beekey.Controller.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.btree.beekey.R
import com.btree.beekey.databinding.FragmentPostTaskBinding

class PostTaskFragment : Fragment(R.layout.fragment_post_task) {

    private var _binding: FragmentPostTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPostTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spinner:Spinner = view.findViewById(R.id.spinnerCategory)
        val category = resources.getStringArray(R.array.Category)
        val adapter = context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_spinner_item, category
            ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        }
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
//                Toast.makeText(
//                    context,
//                    category[position], Toast.LENGTH_LONG
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}
