package xyz.deseteral.pudeuko.services.pudeuko

import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import com.dropbox.core.v2.DbxClientV2
import xyz.deseteral.pudeuko.domain.PudeukoObject
import java.io.ByteArrayOutputStream
import java.util.*

internal class DownloadPudeukoTask(
    private val dbxClient: DbxClientV2,
    private val callback: Callback
) : AsyncTask<Unit, Unit, ArrayList<PudeukoObject>>() {

    private var exception: Exception? = null

    interface Callback {
        fun onComplete(result: ArrayList<PudeukoObject>)
        fun onError(e: Exception)
    }

    override fun onPostExecute(result: ArrayList<PudeukoObject>) {
        super.onPostExecute(result)

        if (exception != null) {
            callback.onError(exception!!)
        } else {
            callback.onComplete(result)
        }
    }

    override fun doInBackground(vararg params: Unit?): ArrayList<PudeukoObject> {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger()

        try {
            val outputStream = ByteArrayOutputStream()
            dbxClient.files().download(PudeukoService.DBX_FILE_PATH).download(outputStream)
            val json = outputStream.toString()

            return Klaxon().parseArray<PudeukoObject>(json) as ArrayList<PudeukoObject>
        } catch (exception: Exception) {
            this.exception = exception
        }

        return ArrayList()
    }
}
