package xyz.deseteral.pudeuko

import android.content.Context
import android.widget.Toast
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import xyz.deseteral.pudeuko.domain.PudeukoObject
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
                        Toast.makeText(context, "Added to pudeuko!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Exception) {
                        Toast.makeText(context, "Upload to pudeuko failed!", Toast.LENGTH_SHORT).show()
                    }
                }).execute(result)
            }

            override fun onError(e: Exception) {
                Toast.makeText(context, "Fetching data from pudeuko failed!", Toast.LENGTH_SHORT).show()
            }
        }).execute()
    }
}