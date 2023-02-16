package com.pr777.kotlinfirebaseuploadimage.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr777.kotlinfirebaseuploadimage.model.User
import com.pr777.kotlinfirebaseuploadimage.model.UserRepository

class UserViewModel constructor(
    val userRepository: UserRepository=UserRepository()
):ViewModel() {

    fun uploadimagetofirebase(uri: Uri,name:String){
        userRepository.uploadimage(uri,name)
    }

    fun readalldata():MutableLiveData<ArrayList<User>>{
        return userRepository.readfromfirebase()
    }

    fun showprogresssuccesfully(loaded:Boolean):MutableLiveData<Boolean>{
        return userRepository.succesfull(loaded)
    }

    fun showprogresuploadresult():MutableLiveData<Double>{
        return userRepository.livedataprogress
    }

}