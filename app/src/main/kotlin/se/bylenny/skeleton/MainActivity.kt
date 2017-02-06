package se.bylenny.skeleton

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.button

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val intent: Intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
