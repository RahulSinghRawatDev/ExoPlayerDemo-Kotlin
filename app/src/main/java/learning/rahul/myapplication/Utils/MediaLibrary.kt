package learning.rahul.myapplication.Utils

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.widget.Toast
import java.io.File
import java.util.ArrayList

import learning.rahul.myapplication.Model.EntityMediaDetail

/********************************
 * Created by Rahul on 29-Nov-18.
 ********************************/

class MediaLibrary private constructor() {

    fun getAllVideoMediaDetails(context: Context): ArrayList<EntityMediaDetail> {

        val fileModelList = ArrayList<EntityMediaDetail>()

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(context, "Please provide storage permission", Toast.LENGTH_SHORT).show();
            return fileModelList
        }

        val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

        val selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO


        val queryUri = MediaStore.Files.getContentUri("external")

        val cr = context.contentResolver

        val cursor = cr.query(queryUri, projection, selection, null, MediaStore.Files.FileColumns.DATE_ADDED + " DESC")

        if (cursor != null) {

            for (i in 0 until cursor.count) {

                cursor.moveToPosition(i)
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val path = cursor.getString(dataColumnIndex)

                val file = File(path)
                val file_size = java.lang.Long.parseLong((file.length() / 1024).toString())

                val mediaDetail = EntityMediaDetail()
                //                FileModel fileModel = new FileModel();
                mediaDetail.title = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE))

                val videoDuration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                mediaDetail.duration = videoDuration
                mediaDetail.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID))
                mediaDetail.path = path
                mediaDetail.type = "Video"
                mediaDetail.actualSize = file_size
                if ((path.toUpperCase().contains(".MP4") || path.toUpperCase().contains(".M4A") || path.toUpperCase().contains(".FMP4")
                                || path.toUpperCase().contains(".WEBM") || path.toUpperCase().contains(".MATROSKA") || path.toUpperCase().contains(".MP3")
                                || path.toUpperCase().contains(".OGG") || path.toUpperCase().contains(".WAV") || path.toUpperCase().contains(".MPEG")
                                || path.toUpperCase().contains(".FLV") || path.toUpperCase().contains(".ADTS") || path.toUpperCase().contains(".FLAC")
                                || path.toUpperCase().contains(".AMR")) && file_size < 50 * 1024) {
                    fileModelList.add(mediaDetail)
                }
            }
            cursor.close()
        }
        return fileModelList
    }

    companion object {
        val instance = MediaLibrary()
    }
}