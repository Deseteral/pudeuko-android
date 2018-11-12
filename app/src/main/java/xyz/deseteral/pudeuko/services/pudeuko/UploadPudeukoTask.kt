package xyz.deseteral.pudeuko.services.pudeuko

import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.WriteMode
import xyz.deseteral.pudeuko.domain.PudeukoObject

internal class UploadPudeukoTask(
    private val dbxClient: DbxClientV2,
    private val callback: Callback
) : AsyncTask<List<PudeukoObject>, Unit, Unit>() {

    private var exception: Exception? = null

    interface Callback {
        fun onComplete()
        fun onError(e: Exception)
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        if (exception != null) {
            callback.onError(exception!!)
        } else {
            callback.onComplete()
        }
    }

    override fun doInBackground(vararg params: List<PudeukoObject>?) {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger()

        try {
            val json = Klaxon().toJsonString(params[0]!!)

            dbxClient.files().uploadBuilder(PudeukoService.DBX_FILE_PATH)
                .withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(json.byteInputStream())
        } catch (exception: Exception) {
            this.exception = exception
        }
    }
}
