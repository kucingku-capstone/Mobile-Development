package com.example.kucingku.view.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kucingku.LoadingDialog
import com.example.kucingku.data.User
import com.example.kucingku.databinding.ActivityEditProfileBinding
import com.example.kucingku.databinding.ItemInterestBinding
import com.example.kucingku.utils.PrefsHelper
import com.example.kucingku.view.userpref.UserPrefActivity
import com.google.android.flexbox.FlexboxLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var loadingDialog: LoadingDialog
    private var imageBytes: ByteArray? = null
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        getUser()

        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }

            btnEditInterest.setOnClickListener {
                val intent = Intent(this@EditProfileActivity, UserPrefActivity::class.java)
                intent.putExtra("edit", true)
                startActivity(intent)
            }

            btnChangeImage.setOnClickListener {
                galleryPickerLauncher.launch("image/*")
            }

            btnSave.setOnClickListener {
                if (currentUser != null) {
                    loadingDialog.show()
                    val bio = edtBio.text.toString()
                    val newUser = currentUser!!.copy(bio = bio)

                    if (imageBytes != null) {
                        uploadImage(newUser)
                    } else {
                        saveUser(newUser)
                    }
                }
            }
        }
    }

    private fun uploadImage(user: User) {
        val uniqueImageName = UUID.randomUUID()

        val ref = FirebaseStorage.getInstance().reference.child("user/$uniqueImageName")
        ref.putBytes(imageBytes!!).addOnCompleteListener {
            if (it.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val newUser = user.copy(photoUrl = task.result.toString())
                        saveUser(newUser)
                    }
                }
            } else {
                loadingDialog.dismiss()
            }
        }
    }

    private fun saveUser(user: User) {
        val uid = PrefsHelper.getString(PrefsHelper.UID, "") ?: ""
        val ref = FirebaseDatabase.getInstance().reference.child("user").child(uid)
        ref.setValue(user).addOnCompleteListener {
            loadingDialog.dismiss()
            finish()
        }
    }

    private val galleryPickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { result ->
        val byteArray: ByteArray? = result?.let { uri1 ->
            contentResolver
                .openInputStream(uri1)
                ?.use { it.readBytes() }
        }
        imageBytes = byteArray
        Glide.with(this).load(result).into(binding.ivProfile)
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
        currentUser = user
        with(binding) {
            layoutInterest.removeAllViews()
            if (user.photoUrl.isNullOrEmpty().not()) {
                Glide.with(this@EditProfileActivity)
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

    companion object {
        private const val TAG = "Edit Profile Activity"
    }
}
