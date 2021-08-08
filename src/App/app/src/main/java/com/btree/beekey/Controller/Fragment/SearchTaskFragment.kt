package com.btree.beekey.Controller.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.btree.beekey.Controller.Adapter.Category
import com.btree.beekey.Controller.Adapter.Task
import com.btree.beekey.Controller.Adapter.TaskAdapter
import com.btree.beekey.Model.CategoryResponse
import com.btree.beekey.Model.ListTaskResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.FragmentSearchTaskBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchTaskFragment : Fragment(R.layout.fragment_search_task) {

    private var _binding: FragmentSearchTaskBinding? = null
    private var categoryAdapter: ArrayAdapter<String>? = null
    private var categoryList: List<Category>? = null
    private var taskList: List<Task>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTaskBinding.inflate(inflater, container, false)
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
                if (categoryList!=null) {
                    Toast.makeText(context, "Loading... "+categoryList!![position].categoryName, Toast.LENGTH_SHORT).show()
                    getTaskList(categoryList!![position])
                } else {
                    Toast.makeText(context, "Some error happened", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun loadTaskAdapter(){
        if (taskList == null) {
            return
        }
        binding.listTask.adapter = TaskAdapter(taskList!!)
    }

    private fun getTaskList(category: Category) {
        val token = context?.let { Cache.getToken(it).toString() }
        val response = token?.let { MyAPI.getAPI().postTaskByCategory(it,category) }

        response?.enqueue(object : Callback<ListTaskResponse> {
            override fun onResponse(
                call: Call<ListTaskResponse>,
                response: Response<ListTaskResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data?.exitcode == 0) {
                        taskList = data.tasks
                        loadTaskAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<ListTaskResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loadCategoryAdapter() {
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
                        loadCategoryAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}