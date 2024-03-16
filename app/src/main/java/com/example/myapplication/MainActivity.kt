package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.API.ApiService
import com.example.myapplication.Models.Art
import com.example.myapplication.adapter.ArtAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artAdapter: ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_View);
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = LinearLayoutManager(this)

        artAdapter = ArtAdapter(ArrayList())

        // Thiết lập adapter cho RecyclerView
        recyclerView.adapter = artAdapter

        // Gọi phương thức để lấy dữ liệu từ API và cập nhật danh sách hình ảnh
        fetchArtList()

    }

    private fun fetchArtList() {
        val apiService = ApiService.apiService
        apiService.getAllArt().enqueue(object : Callback<List<Art>> {
            override fun onResponse(call: Call<List<Art>>, response: Response<List<Art>>) {
                if (response.isSuccessful) {
                    val artList = response.body()
                    if (artList != null) {
                        // Cập nhật danh sách hình ảnh trong adapter
                        artAdapter.updateData(artList)
                    }
                } else {
                    // Xử lý khi không thành công
                }
            }

            override fun onFailure(call: Call<List<Art>>, t: Throwable) {
                // Xử lý khi gặp lỗi
            }
        })
    }

}