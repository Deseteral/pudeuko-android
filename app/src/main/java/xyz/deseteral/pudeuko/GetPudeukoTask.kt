package xyz.deseteral.pudeuko

import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import com.dropbox.core.v2.DbxClientV2
import java.io.ByteArrayOutputStream
import java.util.ArrayList

internal class GetPudeukoTask(
    private val dbxClient: DbxClientV2,
    private val callback: Callback
) : AsyncTask<Unit, Unit, ArrayList<PudeukoObject>>() {

    private val PUDEUKO_DBX_FILE_PATH = "pudeuko/data.json"
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
        try {
            val outputStream = ByteArrayOutputStream()
            dbxClient.files().download(PUDEUKO_DBX_FILE_PATH).download(outputStream)
            val json = outputStream.toString()

            return Klaxon().parse(json)!!
        } catch (exception: Exception) {
            this.exception = exception
        }

        return ArrayList()
    }
}
