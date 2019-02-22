package xyz.deseteral.pudeuko.services.pudeuko

import android.content.Context
import android.widget.Toast
import xyz.deseteral.pudeuko.domain.ContentDTO

class PudeukoService {
    fun addObjectToPudeuko(content: String, context: Context) {
        val contentDTO = ContentDTO(content)

        UploadPudeukoTask(object : UploadPudeukoTask.Callback {
            override fun onComplete() {
                Toast.makeText(context, "Added to pudeuko!", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Exception) {
                Toast.makeText(context, "Upload to pudeuko failed!", Toast.LENGTH_SHORT).show()
            }
        }).execute(contentDTO)
    }
}