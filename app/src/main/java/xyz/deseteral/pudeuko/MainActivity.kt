package xyz.deseteral.pudeuko

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import xyz.deseteral.pudeuko.domain.Link
import xyz.deseteral.pudeuko.domain.PudeukoObject
import xyz.deseteral.pudeuko.services.pudeuko.PudeukoService
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private fun currentDateIso(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        df.timeZone = tz
        return df.format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                        val pudeukoObject = PudeukoObject(
                            it, Link(it), null, currentDateIso()
                        )

                        PudeukoService()
                            .addObjectToPudeuko(pudeukoObject, applicationContext)
                    }
                }
            }
        }

        setContentView(R.layout.activity_main)
    }

    fun debugAdd(v: View) {
        val pudeukoObject = PudeukoObject(
            "test",
            Link("https://allegro.pl"), null, currentDateIso()
        )
        PudeukoService().addObjectToPudeuko(pudeukoObject, applicationContext)
    }
}
