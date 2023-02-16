package com.pr777.kotlinfirebaseuploadimage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.pr777.kotlinfirebaseuploadimage.databinding.ActivityMainBinding
import com.pr777.kotlinfirebaseuploadimage.view.UserAdapter
import com.pr777.kotlinfirebaseuploadimage.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
   var imageuri:Uri?=null
    lateinit var binding: ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Glide.with(this).load("https://i.pinimg.com/originals/5b/2f/08/5b2f08b748adc4708d04ef39b55b8933.jpg").into(binding.imageviewopengallery)


        userViewModel=ViewModelProvider(this@MainActivity).get(UserViewModel::class.java)

        binding.imageviewopengallery.setOnClickListener {
            openFileChooser()
        }
        binding.buttonuploadimage.setOnClickListener {
            userViewModel.uploadimagetofirebase(uri = imageuri!!,binding.edittextimagename.text.toString())
        }

        //Show progressbar and Hide progressbar
        userViewModel.showprogresssuccesfully(false).observe(this@MainActivity,{
            if (it){
                showprogressbar()
            }else{
                hideprogressbar()
            }
        })

        binding.recyclerview1.layoutManager=GridLayoutManager(this@MainActivity,3)
        userViewModel.readalldata().observe(this@MainActivity,{
            userAdapter= UserAdapter(this@MainActivity,it)
            binding.recyclerview1.adapter=userAdapter
        })
        userViewModel.showprogresuploadresult().observe(this@MainActivity,{
            binding.apply {
                textviewprogress.text="${it.toInt()} %"
                progressBarhorizontal.progress= it.toInt()
            }
        })

    }

    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        binding.imageviewopengallery.setImageURI(uri)
        if (uri!=null){
            imageuri=uri
        }
    }

    fun showprogressbar(){
        binding.progressbaruploadimage.visibility=View.VISIBLE
        binding.progressBarhorizontal.visibility=View.VISIBLE
        binding.textviewprogress.visibility=View.VISIBLE
    }
    fun hideprogressbar(){
        binding.progressbaruploadimage.visibility=View.GONE
        binding.progressBarhorizontal.visibility=View.GONE
        binding.textviewprogress.visibility=View.GONE
    }





}