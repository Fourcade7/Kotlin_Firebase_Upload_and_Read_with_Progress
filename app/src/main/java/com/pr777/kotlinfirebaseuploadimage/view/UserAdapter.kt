package com.pr777.kotlinfirebaseuploadimage.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pr777.kotlinfirebaseuploadimage.databinding.RecyclerviewItemBinding
import com.pr777.kotlinfirebaseuploadimage.model.User

class UserAdapter constructor(
    val context: Context,
    val arraylist:ArrayList<User>
):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view=RecyclerviewItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.apply {
            textviewread.text=arraylist.get(position).name
            //Glide
            Glide.with(imageviewread)
                .load(arraylist.get(position).imageurl)
                //.placeholder(com.pr777.kotlinfirebaseuploadimage.R.drawable.noimage)
                .centerCrop()
                .listener(object : RequestListener<Drawable>{
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbarimageread.visibility= View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbarimageread.visibility= View.GONE
                        return false
                    }
                })
                .into(imageviewread)
            //Glide

            linearlayoutread.setOnLongClickListener {
                val alertdialog=AlertDialog.Builder(context)
                alertdialog.setTitle("Delete")
                alertdialog.setMessage(arraylist.get(position).name)
                alertdialog.setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        databaseReference.child(arraylist.get(position).pushkey).removeValue()
                    }
                })
                alertdialog.setNegativeButton("No",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })

                alertdialog.create().show()
                return@setOnLongClickListener true
            }
            linearlayoutread.setOnClickListener {
                val intent=Intent(context,MainActivity2::class.java)
                intent.putExtra("url",arraylist.get(position).imageurl)
                intent.putExtra("name",arraylist.get(position).name)
                context.startActivity(intent)

            }







        }
    }
    override fun getItemCount(): Int =arraylist.size


    class UserViewHolder(val binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root)

}