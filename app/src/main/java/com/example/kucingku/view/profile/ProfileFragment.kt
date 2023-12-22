package com.example.kucingku.view.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kucingku.R
import com.example.kucingku.data.User
import com.example.kucingku.databinding.FragmentProfileBinding
import com.example.kucingku.databinding.ItemInterestBinding
import com.example.kucingku.databinding.LayoutDrawerBinding
import com.example.kucingku.databinding.LayoutSettingBinding
import com.example.kucingku.utils.PrefsHelper
import com.example.kucingku.view.login.LoginActivity
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()

        with(binding) {
            btnSetting.setOnClickListener { showDialogSetting() }
            btnMenu.setOnClickListener { showSidebar() }
        }
    }

    private fun getUser() {
        val uid = PrefsHelper.getString(PrefsHelper.UID, "")
        val ref = FirebaseDatabase.getInstance().reference.child("user").child(uid)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    bindUser(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, error.message)
            }
        })
    }

    private fun bindUser(user: User) {
        with(binding) {
            tvUsername.text = user.username ?: "-"
            tvBio.text = user.bio ?: "-"

            if (user.photoUrl.isNullOrEmpty().not()) {
                Glide.with(requireContext())
                    .load(user.photoUrl)
                    .into(ivProfile)
            }

            for (breed in user.breedsInterest.orEmpty().split(",")) {
                val layoutParams = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val itemInterestBinding = ItemInterestBinding.inflate(layoutInflater)
                itemInterestBinding.tvInterest.text = breed
                layoutParams.apply {
                    setMargins(0, 0, 32, 32)
                }
                itemInterestBinding.root.layoutParams = layoutParams
                layoutInterest.addView(itemInterestBinding.root)
            }

            for (gender in user.genderInterest.orEmpty().split(",")){
                val layoutParams = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val itemInterestBinding = ItemInterestBinding.inflate(layoutInflater)
                itemInterestBinding.tvInterest.text = gender
                layoutParams.apply {
                    setMargins(0, 0, 32, 32)
                }
                itemInterestBinding.root.layoutParams = layoutParams
                layoutInterest.addView(itemInterestBinding.root)
            }

            for (age in user.ageInterest.orEmpty().split(",")) {
                val layoutParams = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val itemInterestBinding = ItemInterestBinding.inflate(layoutInflater)
                itemInterestBinding.tvInterest.text = age
                layoutParams.apply {
                    setMargins(0, 0, 32, 32)
                }
                itemInterestBinding.root.layoutParams = layoutParams
                layoutInterest.addView(itemInterestBinding.root)
            }
        }
    }

    private fun showDialogSetting() {
        val settingBinding = LayoutSettingBinding.inflate(layoutInflater)
        val settingDialog = BottomSheetDialog(requireContext())
        settingDialog.setContentView(settingBinding.root)

        settingDialog.setOnShowListener {
            with(settingBinding) {
                btnEditProfile.setOnClickListener {
                    requireContext().startActivity(Intent(requireContext(), EditProfileActivity::class.java))
                }

                btnLogout.setOnClickListener {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    FirebaseAuth.getInstance().signOut()
                    PrefsHelper.clear()
                    requireActivity().finish()
                }
            }
        }

        settingDialog.show()
    }

    private fun showSidebar() {
        val sidebarBinding = LayoutDrawerBinding.inflate(layoutInflater)
        val sidebarDialog = Dialog(requireContext())
        sidebarDialog.setContentView(sidebarBinding.root)
        sidebarDialog.window?.let {
            val wlp = it.attributes
            wlp.gravity = Gravity.START
            it.apply {
                setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setWindowAnimations(R.style.DialogAnimation)
            }
        }
        sidebarBinding
        sidebarDialog.show()
    }

    companion object {
        private const val TAG = "Profile Fragment"
    }
}
