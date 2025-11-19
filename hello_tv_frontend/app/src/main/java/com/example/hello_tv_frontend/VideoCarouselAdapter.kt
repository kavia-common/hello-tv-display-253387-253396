package com.example.hello_tv_frontend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/** PUBLIC_INTERFACE
 * Horizontal carousel rail adapter for TV video items.
 */
class VideoCarouselAdapter(
    private val context: Context,
    private val videos: List<VideoItem>
) : RecyclerView.Adapter<VideoCarouselAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumb: ImageView = view.findViewById(R.id.thumb)
        val title: TextView = view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_card, parent, false)
        return VideoViewHolder(v)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = videos[position]
        holder.thumb.setImageDrawable(ContextCompat.getDrawable(context, item.thumbnailResId))
        holder.title.text = item.title

        // DPAD focus UI cue: elevate + border when focused
        holder.itemView.setOnFocusChangeListener { v, hasFocus ->
            v.isSelected = hasFocus
            v.elevation = if (hasFocus) 22f else 10f
            v.background = if (hasFocus)
                ContextCompat.getDrawable(context, R.drawable.focus_border)
            else null
        }
    }

    override fun getItemCount(): Int = videos.size
}
