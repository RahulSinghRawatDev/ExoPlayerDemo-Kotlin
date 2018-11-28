package learning.rahul.myapplication

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import learning.rahul.myapplication.Utils.Utility

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        funInit()
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun funInit() {
        Handler().postDelayed(Runnable {
            if (!Utility.checkStoragePermission(this))
                Utility.requestStoragePermission(this)
            else {
                val gotoNext = Intent(this, VideoListActivity::class.java)
                startActivity(gotoNext)
                finish()
            }
        }, 3000)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            402 -> {
                if (grantResults.size > 0) {
                    val permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (permissionGranted) {
                        val gotoNext = Intent(this, VideoListActivity::class.java)
                        startActivity(gotoNext)
                        finish()
                    } else {
                        Toast.makeText(this, "Permission Denied! Cannot load images.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
