package xyz.deseteral.pudeuko.services.pudeuko

import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import xyz.deseteral.pudeuko.domain.ContentDTO

internal class UploadPudeukoTask(private val callback: Callback) : AsyncTask<ContentDTO, Unit, Unit>() {

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

    override fun doInBackground(vararg params: ContentDTO?) {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger()

        try {
            val json = Klaxon().toJsonString(params[0]!!)

            // TODO: HTTP POST to pudeuko-service here
        } catch (exception: Exception) {
            this.exception = exception
        }
    }
}
