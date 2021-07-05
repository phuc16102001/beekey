import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.btree.beekey.Controller.Activity.ChangeInformationActivity
import com.btree.beekey.Controller.Activity.ChangePasswordActivity
import com.btree.beekey.Controller.Activity.LoginActivity
import com.btree.beekey.R
import com.btree.beekey.Utils.Cache
import com.btree.beekey.databinding.FragmentSettingBinding

class SettingFragment:Fragment(R.layout.fragment_setting) {
    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val personalSetting=binding.layoutSettingPersonal
        val changePassword=binding.layoutSettingPassword
        val logoutButton=binding.logoutButton

        personalSetting.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ChangeInformationActivity::class.java)
                it.startActivity(intent)
            }
        }

        changePassword.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ChangePasswordActivity::class.java)
                it.startActivity(intent)
            }
        }
        logoutButton.setOnClickListener{
            activity?.let{
                Cache.clear(it)
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }
    }
}