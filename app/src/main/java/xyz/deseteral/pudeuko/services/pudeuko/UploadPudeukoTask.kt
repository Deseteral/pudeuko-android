package xyz.deseteral.pudeuko.services.pudeuko

import android.os.AsyncTask
import xyz.deseteral.pudeuko.clients.PudeukoClient
import xyz.deseteral.pudeuko.domain.ContentDTO

internal class UploadPudeukoTask(
    private val pudeukoClient: PudeukoClient,
    private val callback: Callback
) : AsyncTask<ContentDTO, Unit, Unit>() {

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
        try {
            val response = pudeukoClient.postItem(params[0]!!).execute()
        } catch (exception: Exception) {
            this.exception = exception
        }
    }
}
