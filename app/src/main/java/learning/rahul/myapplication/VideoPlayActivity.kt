package learning.rahul.myapplication

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*

class VideoPlayActivity : AppCompatActivity(){

    private lateinit var player: SimpleExoPlayer
    private lateinit var trackSelector: DefaultTrackSelector
    private var shouldAutoPlay: Boolean = false
    private var playUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url : String = intent.getStringExtra("videoUrl")
        playUrl = url
    }


    fun releasePlayer(){
      player.release()
        shouldAutoPlay = player.playWhenReady
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    override fun onStart() {
        initializePlayer()
        super.onStart()
    }


    private fun initializePlayer() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        shouldAutoPlay = true
        val bandwidthMeter = DefaultBandwidthMeter()
        val extractorsFactory = DefaultExtractorsFactory()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter as TransferListener<in DataSource>)
        val mediaSource = ExtractorMediaSource(Uri.parse(playUrl),
                mediaDataSourceFactory, extractorsFactory, null, null)
        playerView?.requestFocus()
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
        playerView?.player = player
        player.playWhenReady = shouldAutoPlay;
        player.prepare(mediaSource)

    }
}
