package com.example.kucingku.view.userpref

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kucingku.LoadingDialog
import com.example.kucingku.data.User
import com.example.kucingku.databinding.ActivityUserPreferanceBinding
import com.example.kucingku.databinding.ItemInterestBinding
import com.example.kucingku.utils.PrefsHelper
import com.example.kucingku.view.profile.EditProfileActivity
import com.example.kucingku.view.welcome.WelcomeActivity
import com.google.android.flexbox.FlexboxLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserPrefActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPreferanceBinding
    private val breedInterest = arrayListOf<String>()
    private val ageInterest = arrayListOf<String>()
    private val genderInterest = arrayListOf<String>()
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPreferanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUser()

        val extra = intent.extras
        isEdit = extra?.getBoolean("edit", false) ?: false

        binding.btnNext.setOnClickListener {
            addInterestToDatabase()

            if (isEdit) {
                finish()
            } else {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun getUser() {
        val loading = LoadingDialog(this)
        loading.show()
        val uid = PrefsHelper.getString(PrefsHelper.UID, "")
        val ref = FirebaseDatabase.getInstance().reference.child("user").child(uid)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    bindUser(user)
                } else {
                    setupBreedAction()
                    setupAgeAction()
                    setupGenderAction()
                }
                loading.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, error.message)


                setupBreedAction()
                setupAgeAction()
                setupGenderAction()
                loading.dismiss()
            }
        })
    }

    private fun bindUser(user: User) {
        with(binding) {
            breedInterest.addAll(user.breedsInterest.orEmpty().split(","))

            for (breed in breedInterest) {
                when (breed) {
                    btnPersian.text.toString() -> {
                        btnPersian.isChecked = true
                    }
                    btnCoon.text.toString() -> {
                        btnCoon.isChecked = true
                    }
                    btnSiamase.text.toString() -> {
                        btnSiamase.isChecked = true
                    }
                    btnMaine.text.toString() -> {
                        btnMaine.isChecked = true
                    }
                    btnBritish.text.toString() -> {
                        btnBritish.isChecked = true
                    }
                    btnRussian.text.toString() -> {
                        btnRussian.isChecked = true
                    }
                }
            }

            genderInterest.addAll(user.genderInterest.orEmpty().split(","))
            for (gender in genderInterest){
                when (gender) {
                    btnMale.text.toString() -> {
                        btnMale.isChecked = true
                    }
                    btnFemale.text.toString() -> {
                        btnCoon.isChecked = true
                    }
                }
            }

            ageInterest.addAll(user.ageInterest.orEmpty().split(","))
            for (age in ageInterest) {
                when (age) {
                    btnBaby.text.toString() -> {
                        btnBaby.isChecked = true
                    }
                    btnYoung.text.toString() -> {
                        btnYoung.isChecked = true
                    }
                    btnAdult.text.toString() -> {
                        btnAdult.isChecked = true
                    }
                    btnSenior.text.toString() -> {
                        btnSenior.isChecked = true
                    }
                }
            }
        }

        setupBreedAction()
        setupAgeAction()
        setupGenderAction()
    }

    private fun addInterestToDatabase() {
        val uid = PrefsHelper.getString(PrefsHelper.UID, "")
        if (uid.isNotEmpty()) {
            val ref = FirebaseDatabase.getInstance().reference.child("user").child(uid)
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        Log.d(TAG, user.toString())
                        val newUser = user.copy(
                            genderInterest = genderInterest.joinToString(",").ifEmpty { null },
                            ageInterest = ageInterest.joinToString(",").ifEmpty { null },
                            breedsInterest = breedInterest.joinToString(",").ifEmpty { null }
                        )
                        Log.d(TAG, newUser.toString())
                        ref.setValue(newUser).addOnFailureListener {
                            Log.e(TAG, it.localizedMessage)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, error.message)
                }
            })
        }
    }

    private fun setupGenderAction() {
        with(binding) {
            btnMale.setOnCheckedChangeListener(onCheckedChangeGender)
            btnFemale.setOnCheckedChangeListener(onCheckedChangeGender)
        }
    }

    private fun setupBreedAction() {
        with(binding) {
            btnPersian.setOnCheckedChangeListener(onCheckedChangeBreed)
            btnCoon.setOnCheckedChangeListener(onCheckedChangeBreed)
            btnSiamase.setOnCheckedChangeListener(onCheckedChangeBreed)
            btnMaine.setOnCheckedChangeListener(onCheckedChangeBreed)
            btnBritish.setOnCheckedChangeListener(onCheckedChangeBreed)
            btnRussian.setOnCheckedChangeListener(onCheckedChangeBreed)
        }
    }

    private fun setupAgeAction() {
        with(binding) {
            btnBaby.setOnCheckedChangeListener(onCheckedChangeAge)
            btnYoung.setOnCheckedChangeListener(onCheckedChangeAge)
            btnAdult.setOnCheckedChangeListener(onCheckedChangeAge)
            btnSenior.setOnCheckedChangeListener(onCheckedChangeAge)
        }
    }

    private val onCheckedChangeBreed = OnCheckedChangeListener { button, isChecked ->
        val breed = button.text.toString()

        if (isChecked) {
            val breedInterestCount = breedInterest.size

            if (breedInterestCount < 3) {
                breedInterest.add(breed)
            } else {
                Toast.makeText(this@UserPrefActivity, "Already choose 3 breeds", Toast.LENGTH_SHORT)
                    .show()
                button.isChecked = false
            }
        } else {
            breedInterest.remove(breed)
        }
    }

    private val onCheckedChangeAge = OnCheckedChangeListener { button, isChecked ->
        val age = button.text.toString()

        if (isChecked) {
            val ageInterestCount = ageInterest.size

            if (ageInterestCount < 3) {
                ageInterest.add(age)
            } else {
                Toast.makeText(this@UserPrefActivity, "Already choose 3 ages", Toast.LENGTH_SHORT)
                    .show()
                button.isChecked = false
            }
        } else {
            ageInterest.remove(age)
        }
    }

    private val onCheckedChangeGender = OnCheckedChangeListener { button, isChecked ->
        val gender = button.text.toString()

        if (isChecked) {
            genderInterest.add(gender)
        } else {
            genderInterest.remove(gender)
        }
    }

    companion object {
        private const val TAG = "UserPrefActivity"
    }
}
