package xyz.deseteral.pudeuko

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    fun currentDateIso(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US)
        df.timeZone = tz
        return df.format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                        val accessToken = getString(R.string.dropbox_access_token)
                        val requestConfig = DbxRequestConfig.newBuilder("pudeuko").build()
                        val client = DbxClientV2(requestConfig, accessToken)

                        val pudeukoObject = PudeukoObject(
                            it, Link(it), null, currentDateIso()
                        )

                        AddPudeukoObjectTask(client, object : AddPudeukoObjectTask.Callback {
                            override fun onComplete() {
                                Toast.makeText(
                                    applicationContext,
                                    "Added to pudeuko",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onError(e: Exception) {
                                Toast.makeText(
                                    applicationContext,
                                    "Failed adding to pudeuko",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }).execute(pudeukoObject)
                    }
                }
            }
        }

        setContentView(R.layout.activity_main)
    }
}
