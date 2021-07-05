package com.btree.beekey.Controller.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.btree.beekey.Controller.Adapter.Feedback
import com.btree.beekey.Controller.Adapter.FeedbackAdapter
import com.btree.beekey.R
import com.btree.beekey.databinding.FragmentPostTaskBinding
import com.btree.beekey.databinding.FragmentProfileBinding

class PostTaskFragment:Fragment(R.layout.fragment_post_task){

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
    }
}
