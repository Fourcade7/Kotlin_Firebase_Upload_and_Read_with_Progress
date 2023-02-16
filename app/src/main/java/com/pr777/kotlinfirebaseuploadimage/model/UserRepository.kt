package com.pr777.kotlinfirebaseuploadimage.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserRepository constructor(
     val databaseReference:DatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users"),
     val storageReference: StorageReference = FirebaseStorage.getInstance().getReference().child("images")
) {

    //buyerda elon qildim va yangi livedata ishlataman deb aytdim
    val mlivedata=MutableLiveData<Boolean>()
    val livedata=MutableLiveData<ArrayList<User>>()
    val arraylist=ArrayList<User>()
    var livedataprogress=MutableLiveData<Double>()

    fun uploadimage(uri: Uri,name:String){
        if (uri != null) {
            succesfull(true)
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + System.currentTimeMillis().toString()
            )
            filereference.putFile(uri)
                .addOnSuccessListener { filereference.downloadUrl.addOnSuccessListener {
                    var pushkey=databaseReference.push().key.toString()
                    val user=User(name,it.toString(),pushkey)
                    databaseReference.child(pushkey).setValue(user).addOnCompleteListener {
                        succesfull(false)
                    }

                } }
                .addOnProgressListener {
                    val progress: Double = 100.0 * it.getBytesTransferred() / it.getTotalByteCount()
                    Log.d("PR77777", "uploadimage: $progress")
                    livedataprogress.value=progress
                }
        }
    }

    fun readfromfirebase():MutableLiveData<ArrayList<User>>{
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arraylist.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val user=datasnapshot.getValue(User::class.java)
                    arraylist.add(user!!)
                }
                livedata.value=arraylist
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return livedata
    }

    fun succesfull(loaded:Boolean):MutableLiveData<Boolean>{
        mlivedata.value=loaded
        return mlivedata
    }








}