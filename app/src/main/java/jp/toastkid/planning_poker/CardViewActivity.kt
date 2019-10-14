package jp.toastkid.planning_poker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.widget.TextView

/**
 * @author toastkidjp
 */
class CardViewActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_card)
        val textView = findViewById(R.id.card_text) as TextView
        val text = intent.getStringExtra(EXTRA_KEY_CARD_TEXT)
        textView.text = text
        if (text.length >= 3) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120f)
        }
    }

    companion object {

        private val EXTRA_KEY_CARD_TEXT = "card_text"

        fun makeIntent(
                context: Context,
                text: String
        ): Intent {
            val intent = Intent(context, CardViewActivity::class.java)
            intent.putExtra(EXTRA_KEY_CARD_TEXT, text)
            return intent
        }
    }
}
