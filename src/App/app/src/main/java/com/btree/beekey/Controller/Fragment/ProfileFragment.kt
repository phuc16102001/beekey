import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.btree.beekey.Controller.Activity.MyListRequestActivity
import com.btree.beekey.Controller.Activity.MyListTaskActivity
import com.btree.beekey.Controller.Adapter.Feedback
import com.btree.beekey.Controller.Adapter.FeedbackAdapter
import com.btree.beekey.Model.Account
import com.btree.beekey.Model.GetInformationResponse
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.Utils.MyAPI
import com.btree.beekey.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadFeedback()
        requestInformation()

        binding.requestIc.setOnClickListener {
            Intent(getActivity(), MyListRequestActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.taskIc.setOnClickListener {
            Intent(getActivity(), MyListTaskActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun loadInformation(account: Account) {
        binding.txtDisplayName.setText(account.displayName)
        binding.txtEmail.setText(account.email)
        binding.txtCoin.setText(account.coin.toString())
        binding.txtPhone.setText(account.phone)
    }

    private fun requestInformation() {
        val token = context?.let { Cache.getToken(it) }
        val request = token?.let { MyAPI.getAPI().getInformation(it) }
        request?.enqueue(object : Callback<GetInformationResponse> {
            override fun onResponse(
                call: Call<GetInformationResponse>,
                response: Response<GetInformationResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data?.exitcode == 0) {
                        loadInformation(
                            Account(
                                data.username,
                                data.displayName,
                                data.phone,
                                data.email,
                                data.coin
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<GetInformationResponse>, t: Throwable) {
                Toast.makeText(context, "Fail to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadFeedback() {
        val userList = mutableListOf<Feedback>()
        userList.add(Feedback("Good", "Description: Test test test", "Thanh"))
        userList.add(Feedback("Good", "Description: Test test test", "Phuc"))
        userList.add(Feedback("Good", "Description: Test test test", "Khanh"))
        userList.add(Feedback("Good", "Description: Test test test", "Khoa"))
        userList.add(Feedback("Good", "Description: Test test test", "Khoa1"))
        userList.add(Feedback("Good", "Description: Test test test", "Khoa2"))
        userList.add(Feedback("Good", "Description: Test test test", "Khoa3"))
        Log.d("user", userList.size.toString())

        val recycler_view = binding.recycler
        recycler_view.adapter = FeedbackAdapter(userList)
    }
}
