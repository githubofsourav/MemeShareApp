package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private val url = "https://meme-api.herokuapp.com/gimme"
    private lateinit var  imageView: ImageView
    private var currentImageUrl:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        loadImageFromApi()
    }

    private fun loadImageFromApi() {
        //Get data using API

        //Volley api: Allows us to fetch data from servers

        //Step 1: Create Request queue
        val queue= Volley.newRequestQueue(this)

        //Step 2: Create Request
        val request = JsonObjectRequest(Request.Method.GET,this.url,null,
            { response->
                Log.d("Result",response.toString())
                currentImageUrl = response.get("url").toString()
                Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loading).into(imageView)
            },

            {
                Log.e("Error",it.toString())
                Toast.makeText(applicationContext,"Error in loading the meme from server",Toast.LENGTH_LONG).show()
            }
            )
        //Step 3: Add request to request_queue
        queue.add(request)

        //Picasso : Allows us to put image from url to imageView
    }

    fun changeImage(view: View) {
        this.loadImageFromApi()
    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout this cool meme I got from Reddit: $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this meme using: ")
        startActivity(chooser)
    }
}