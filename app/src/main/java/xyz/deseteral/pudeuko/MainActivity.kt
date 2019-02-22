package xyz.deseteral.pudeuko

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.deseteral.pudeuko.services.pudeuko.PudeukoService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type == "text/plain") {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                        PudeukoService().addObjectToPudeuko(it, applicationContext)
                    }
                }
            }
        }

        setContentView(R.layout.activity_main)
    }
}
