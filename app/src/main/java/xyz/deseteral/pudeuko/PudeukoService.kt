package xyz.deseteral.pudeuko

import android.content.Context
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import java.util.ArrayList

class PudeukoService {
    companion object {
        const val DBX_FILE_PATH = "/pudeuko/data.json"
    }

    fun addObjectToPudeuko(pudeukoObject: PudeukoObject, context: Context) {
        val accessToken = context.getString(R.string.dropbox_access_token)
        val requestConfig = DbxRequestConfig.newBuilder("pudeuko").build()
        val client = DbxClientV2(requestConfig, accessToken)

        DownloadPudeukoTask(client, object : DownloadPudeukoTask.Callback {
            override fun onComplete(result: ArrayList<PudeukoObject>) {
                result.add(0, pudeukoObject)

                UploadPudeukoObjectTask(client, object : UploadPudeukoObjectTask.Callback {
                    override fun onComplete() {

                    }

                    override fun onError(e: Exception) {

                    }
                }).execute(result)
            }

            override fun onError(e: Exception) {

            }
        }).execute()
    }
}