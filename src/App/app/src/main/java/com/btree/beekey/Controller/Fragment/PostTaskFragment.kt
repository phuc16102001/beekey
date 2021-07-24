package com.btree.beekey.Controller.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.btree.beekey.Controller.Adapter.Category
import com.btree.beekey.Model.CategoryResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.FragmentPostTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class PostTaskFragment : Fragment(R.layout.fragment_post_task) {

    private var _binding: FragmentPostTaskBinding? = null
    private var categoryAdapter: ArrayAdapter<String>? = null
    private var categoryList: List<Category>? = null
    private var dateString: String? = null
    private var timeString: String? = null

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
        getCategoryList()
        binding.spinnerCategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                Toast.makeText(
                    context,
                    categoryList!![position].categoryId.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.deadline.setOnClickListener { getDeadlineFromUser() }
        binding.fileAttachTxt.setOnClickListener {
            fileChooser()
        }
    }

    private fun loadAdapter() {
        if (categoryList == null) {
            return
        }

        val nameList: MutableList<String> = ArrayList()
        for (category in categoryList!!) {
            nameList.add(category.toString())
        }
        categoryAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                nameList
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCategory.adapter = adapter
            }
        }
    }

    private fun getCategoryList() {
        val token = context?.let { Cache.getToken(it).toString() }
        val response = token?.let { MyAPI.getAPI().getCategoryList(it) }

        response?.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data?.exitcode == 0) {
                        categoryList = data.categories
                        loadAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDeadlineFromUser() {
        getDateFromUser()
    }

    private fun getTimeFromUser() {
        val calendar = Calendar.getInstance()
        val curHour = calendar.get(Calendar.HOUR_OF_DAY)
        val curMinute = calendar.get(Calendar.MINUTE)
        val timePicker = context?.let {
            TimePickerDialog(it, { view, hour, minute ->
                timeString = "$hour:$minute:00"
                binding.deadline.setText("$dateString at $timeString")
            }, curHour, curMinute, true)
        }
        timePicker?.show()

    }

    private fun getDateFromUser() {
        val calendar = Calendar.getInstance()
        val curDay = calendar.get(Calendar.DAY_OF_MONTH)
        var curMonth = calendar.get(Calendar.MONTH)
        val curYear = calendar.get(Calendar.YEAR)
        val datePicker = context?.let {
            DatePickerDialog(
                it,
                { view, year, month, day ->
                    dateString = "$day/${month + 1}/$year"
                    getTimeFromUser()
                },
                curYear, curMonth, curDay
            )
        }
        datePicker?.show()
    }

    private fun fileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.type = "*/*"

        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        super.startActivityForResult(Intent.createChooser(intent, "Select file"),11)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }
}
