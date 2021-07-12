package com.btree.beekey.Controller.Fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.btree.beekey.Controller.Activity.MainActivity
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
import java.util.Calendar.MONTH
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList

class PostTaskFragment : Fragment(R.layout.fragment_post_task) {

    private var _binding: FragmentPostTaskBinding? = null
    private var categoryAdapter: ArrayAdapter<String>? = null
    private var categoryList: List<Category>? = null

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

        selectDueDate()

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

    private fun selectDueDate(){
        val eText: EditText = binding.deadline;
        eText.inputType = InputType.TYPE_NULL;
        eText.setOnClickListener { eText.setText(selectDate()) }
        }

    private fun selectDate(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)+1
        val year = calendar.get(Calendar.YEAR)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val string: EditText? =null
        // date picker dialog
        val datePicker =
            context?.let { it1 ->
                DatePickerDialog(
                    it1,
                    DatePickerDialog.OnDateSetListener { view, year, month, day ->
                        val monthI = month+1
                        string?.setText(("$day/$monthI/$year").toString())
                    },
                    year,
                    month,
                    day,
                )
            }
        datePicker?.show()
        return string.toString()

    }
}





