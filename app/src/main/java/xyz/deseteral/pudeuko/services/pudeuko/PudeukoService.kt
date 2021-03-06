package xyz.deseteral.pudeuko.services.pudeuko

import android.content.Context
import android.widget.Toast
import xyz.deseteral.pudeuko.clients.buildPudeukoClient
import xyz.deseteral.pudeuko.domain.ContentDTO

class PudeukoService {
    fun addObjectToPudeuko(content: String, context: Context) {
        val contentDTO = ContentDTO(content)
        val pudeukoClient = buildPudeukoClient(context)

        UploadPudeukoTask(pudeukoClient, object : UploadPudeukoTask.Callback {
            override fun onComplete() {
                Toast.makeText(context, "Added to pudeuko!", Toast.LENGTH_SHORT).show()
            }

            override fun onError() {
                Toast.makeText(context, "Upload to pudeuko failed!", Toast.LENGTH_SHORT).show()
            }
        }).execute(contentDTO)
    }
}
