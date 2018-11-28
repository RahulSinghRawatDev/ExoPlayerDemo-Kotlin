package learning.rahul.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_video_list.*
import learning.rahul.myapplication.Adapter.VideoAdapter
import learning.rahul.myapplication.Utils.MediaLibrary

class VideoListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        setAdapter()
    }

    private fun setAdapter() {
        val data =  MediaLibrary.instance.getAllVideoMediaDetails(this)
        val adapter = VideoAdapter(this,data)
        rvVideoList.setLayoutManager(GridLayoutManager(this, 3))
        rvVideoList.setAdapter(adapter)
    }
}
