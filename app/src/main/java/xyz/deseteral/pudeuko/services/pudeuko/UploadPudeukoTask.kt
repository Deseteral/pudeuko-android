package xyz.deseteral.pudeuko.services.pudeuko

import android.os.AsyncTask
import xyz.deseteral.pudeuko.clients.PudeukoClient
import xyz.deseteral.pudeuko.domain.ContentDTO

internal class UploadPudeukoTask(
    private val pudeukoClient: PudeukoClient,
    private val callback: Callback
) : AsyncTask<ContentDTO, Unit, Unit>() {

    private var success: Boolean = true

    interface Callback {
        fun onComplete()
        fun onError()
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        if (success) {
            callback.onComplete()
        } else {
            callback.onError()
        }
    }

    override fun doInBackground(vararg params: ContentDTO?) {
        try {
            val content = params[0]!!
            val response = pudeukoClient.postItem(content).execute()
            this.success = response.isSuccessful
        } catch (exception: Exception) {
            this.success = false
        }
    }
}
