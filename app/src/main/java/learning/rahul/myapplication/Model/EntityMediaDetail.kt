package learning.rahul.myapplication.Model


/**
 * Created by user on 19-08-2017.
 */
class EntityMediaDetail {

    var path: String? = null
    var duration: Long = 0
    var title: String? = null
    var type: String? = null
    var id: Long = 0
    var savedPath: String? = null
    var downloadId: Long? = null
    var actualSize: Long = 0
    var downloadedSize: Long = -1
    var isPlaying: Boolean = false
    var seekTo: Long? = null
    var json: String? = null
    var isSelected: Boolean = false
    var albumName: String? = null
    var artistName: String? = null
    var albumId: Long = 0

    override fun hashCode(): Int {
        var hashCode = 0
        hashCode = path!!.hashCode()
        return hashCode
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is EntityMediaDetail) {
            val pp = obj as EntityMediaDetail?
            return pp!!.path == this.path
        } else {
            return false
        }
    }
}
