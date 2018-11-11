package xyz.deseteral.pudeuko

import android.content.Context
import android.os.AsyncTask
import com.dropbox.core.v2.DbxClientV2

internal class AddPudeukoObjectTask(context: Context, dbxClient: DbxClientV2, callback: Callback)
    : AsyncTask<PudeukoObject, Unit, Unit>() {

    interface Callback {
        fun onComplete()
        fun onError(e: Exception)
    }

    override fun doInBackground(vararg params: PudeukoObject?) {
        val pudeukoObject = params[0]
    }
}
