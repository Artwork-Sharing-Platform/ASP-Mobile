package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.API.ApiService
import com.example.myapplication.Models.Art
import com.example.myapplication.adapter.ArtAdapter
import com.example.myapplication.event.ArtworkClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), ArtworkClickListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artAdapter: ArtAdapter
    private lateinit var menuButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("MySession", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (!isLoggedIn) {
            // Nếu chưa đăng nhập, chuyển người dùng đến màn hình đăng nhập
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Đóng MainActivity
            return
        }

        recyclerView = findViewById(R.id.recycler_View)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Khởi tạo adapter với MainActivity làm listener
        artAdapter = ArtAdapter(ArrayList(), this)

        // Thiết lập adapter cho RecyclerView
        recyclerView.adapter = artAdapter

        // Gọi phương thức để lấy dữ liệu từ API và cập nhật danh sách hình ảnh
        fetchArtList()

       /* val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { view ->
            logout(view)
        }*/
        menuButton = findViewById(R.id.menuButton)
    }

    fun showPopupMenu(view: View) {
        val menuIcon = findViewById<View>(R.id.menuIcon)
        val popupMenu = PopupMenu(this, menuIcon)
        popupMenu.inflate(R.menu.menu_main)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_profile -> {
                    // Xử lý khi người dùng chọn "View Profile"
                    true
                }
                R.id.action_logout -> {
                    logout(view) // Gọi phương thức logout khi người dùng chọn "Logout"
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    override fun onArtworkClicked(art: Art) {
        // Xử lý khi bức ảnh được click
        // Ví dụ: mở activity hiển thị chi tiết bức ảnh
        val intent = Intent(this, ArtDetailActivity::class.java)
        intent.putExtra("id", art.getId())
        startActivity(intent)
    }

    fun logout(view: View) {
        // Add code here to clear the session and navigate to the login screen
        // For example:
        val sharedPref = getSharedPreferences("MySession", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isLoggedIn", false) // Set isLoggedIn to false
        editor.apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close the MainActivity
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