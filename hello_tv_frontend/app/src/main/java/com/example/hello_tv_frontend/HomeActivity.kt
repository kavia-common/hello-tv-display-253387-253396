package com.example.hello_tv_frontend

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.hello_tv_frontend.R

/**
 * PUBLIC_INTERFACE
 * Home screen for Android TV. Shows a horizontal carousel (video rail) styled to Ocean Professional theme.
 * TV DPAD navigation, safe padding, focus states. Populated with sample data.
 */
class HomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.video_carousel)
        val titleView: TextView = findViewById(R.id.home_title)

        val sampleVideos = listOf(
            VideoItem(R.drawable.ic_launcher_fallback, "The Ocean Explorer"),
            VideoItem(R.drawable.ic_launcher_fallback, "Amber Horizons"),
            VideoItem(R.drawable.ic_launcher_fallback, "Blue Depths"),
            VideoItem(R.drawable.ic_launcher_fallback, "Coral Reef World"),
            VideoItem(R.drawable.ic_launcher_fallback, "Waves of Change"),
            VideoItem(R.drawable.ic_launcher_fallback, "Future Frontiers")
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = VideoCarouselAdapter(this, sampleVideos)

        // For TV: Ensure focus starts on first card
        recyclerView.post {
            recyclerView.getChildAt(0)?.requestFocus()
        }
    }
}
