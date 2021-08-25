package com.btree.beekey.Controller.Fragment

import ProfileFragment
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
import com.btree.beekey.Controller.Activity.MainActivity
import com.btree.beekey.Controller.Adapter.Category
import com.btree.beekey.Model.*
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
    val profileFragment=ProfileFragment()

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
        binding.deadlineTxt.setOnClickListener {
            getDeadlineFromUser()
        }
        binding.fileAttachTxt.setOnClickListener {
            fileChooser()
        }
        binding.postButton.setOnClickListener{
            postTask()
        }
        getCategoryList()
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

        response?.enqueue(object : Callback<GetCategoriesResponse> {
            override fun onResponse(
                call: Call<GetCategoriesResponse>,
                response: Response<GetCategoriesResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data?.exitcode == 0) {
                        categoryList = data.categories
                        loadAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<GetCategoriesResponse>, t: Throwable) {
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
                var hourStr = hour.toString()
                var minuteStr= minute.toString()
                if (hour.toString().length == 1){
                    hourStr= "0$hour"
                }
                if (minute.toString().length == 1 ){
                    minuteStr= "0$minute"
                }

                timeString = "$hourStr:$minuteStr:00"
                binding.deadlineTxt.setText("$dateString at $timeString")
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
                    val realMonth = month+1
                    var dayStr = day.toString()
                    var monthStr= realMonth.toString()
                    if (dayStr.length ==1){
                        dayStr= "0" + dayStr
                    }
                    if (monthStr.length ==1){
                        monthStr= "0"+monthStr
                    }
                    dateString = year.toString()+"-" + monthStr+"-" + dayStr
//                    Log.d("testahihi")
                    getTimeFromUser()
                },
                curYear, curMonth, curDay
            )
        }
        datePicker!!.datePicker.minDate = System.currentTimeMillis()-1000
        datePicker.show()
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

    private fun postTask() {
        val title = binding.titleTxt.text.toString()
        val deadline = "$dateString $timeString"
        val offer=binding.price.text.toString().toInt()
        val description = binding.descriptionTxt.text.toString()
        val spinner=binding.spinnerCategory
        val index=spinner.selectedItemPosition
        val category_id = categoryList?.get(index)?.categoryId.toString()

        val token = context?.let { Cache.getToken(it).toString() }
        val response = token?.let {
            MyAPI.getAPI().postPostTask(it, PostTaskBody(title, deadline, offer, description, category_id))
        }

        response?.enqueue(object : Callback<PostTaskResponse> {
            override fun onResponse(
                call: Call<PostTaskResponse>,
                response: Response<PostTaskResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
                        (activity as MainActivity?)?.setCurrentFragment(profileFragment)
                    }
                }
            }

            override fun onFailure(call: Call<PostTaskResponse>, t: Throwable) {
                Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}