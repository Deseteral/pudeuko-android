package xyz.deseteral.pudeuko

import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.WriteMode

internal class AddPudeukoObjectTask(
    private val dbxClient: DbxClientV2,
    private val callback: Callback
) : AsyncTask<PudeukoObject, Unit, Unit>() {

    private val PUDEUKO_DBX_FILE_PATH = "pudeuko/data.json"
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

    override fun doInBackground(vararg params: PudeukoObject?) {
        val pudeukoObject = params[0]

        GetPudeukoTask(dbxClient, object : GetPudeukoTask.Callback {
            override fun onError(e: Exception) { }

            override fun onComplete(result: ArrayList<PudeukoObject>) {
                result.add(0, pudeukoObject!!)

                val json = Klaxon().toJsonString(result)

                try {
                    dbxClient.files().uploadBuilder(PUDEUKO_DBX_FILE_PATH)
                        .withMode(WriteMode.OVERWRITE)
                        .uploadAndFinish(json.byteInputStream())
                } catch (exception: Exception) {
                    this.
                }
            }
        })
    }
}
