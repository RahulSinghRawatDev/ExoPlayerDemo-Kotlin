package learning.rahul.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import learning.rahul.myapplication.VideoPlayActivity

import java.util.ArrayList

import learning.rahul.myapplication.Model.EntityMediaDetail
import learning.rahul.myapplication.R


/**
 * Created by SUMIT on 29-Jan-18.
 */

class VideoAdapter(private val context: Context, list: ArrayList<EntityMediaDetail>) : RecyclerView.Adapter<VideoAdapter.MyVideoViewHolder>() {

    internal var entityMediaDetails = ArrayList<EntityMediaDetail>()

    init {
        this.entityMediaDetails.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_video_picker, parent, false)
        val holder = MyVideoViewHolder(v)
        return holder
    }

    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        val entityMediaDetail = entityMediaDetails[position]
        holder.videoName.text = entityMediaDetail.title
        Glide.with(holder.videoThumbnail.context).setDefaultRequestOptions(RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.color.exo_edit_mode_background_color).error(R.color.exo_edit_mode_background_color)
                .override(200, 200)).load("file://" + entityMediaDetail.path!!)
                .into(holder.videoThumbnail)
    }

    override fun getItemCount(): Int {
        return entityMediaDetails.size
    }

    inner class MyVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var videoThumbnail: ImageView
        internal var ivChecked: ImageView
        internal var videoName: TextView
        internal var videoLayout: RelativeLayout

        init {
            videoThumbnail = itemView.findViewById<View>(R.id.image_image) as ImageView
            ivChecked = itemView.findViewById(R.id.iv_checked)
            videoName = itemView.findViewById<View>(R.id.title_title) as TextView
            videoLayout = itemView.findViewById(R.id.image_video_grid_layout)
            videoLayout.setOnClickListener {
                val gotoNext = Intent(context, VideoPlayActivity::class.java)
                gotoNext.putExtra("videoUrl",entityMediaDetails.get(adapterPosition).path)
                context.startActivity(gotoNext)
            }
        }
    }
}

