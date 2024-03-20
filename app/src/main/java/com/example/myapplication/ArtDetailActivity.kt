package com.example.myapplication

import android.os.Bundle
import android.telecom.Call
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.ApiService
import com.example.myapplication.Models.Art
import com.squareup.picasso.Picasso
import retrofit2.Response
import javax.security.auth.callback.Callback

class ArtDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_art)

        val imageUrl = intent.getStringExtra("url")

        // Tìm ImageView trong layout
        val imageView: ImageView = findViewById(R.id.imageView)

        // Sử dụng Picasso để tải hình ảnh từ URL và hiển thị vào ImageView
        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(imageView)
        }

        val titleTextView: TextView = findViewById(R.id.titleTextView)

        // Nhận id của tấm hình từ Intent
        val artId = intent.getStringExtra("id")

        // Gọi API để lấy thông tin của tấm hình
        val apiService = ApiService.apiService
        val call = apiService.getArtWorkById(artId)
        call.enqueue(object : retrofit2.Callback<Art> {
            override fun onResponse(call: retrofit2.Call<Art>, response: Response<Art>) {
                if (response.isSuccessful) {
                    val art = response.body()
                    // Hiển thị thông tin tấm hình trên giao diện
                    // Sử dụng thông tin từ đối tượng Art để hiển thị
                    if (art != null) {
                        Picasso.get().load(art.url).into(imageView)
                        titleTextView.text = art.title
                    }
                } else {
                    // Xử lý khi không thành công
                }
            }

            override fun onFailure(call: retrofit2.Call<Art>, t: Throwable) {
                // Xử lý khi gặp lỗi
            }
        })
    }
}