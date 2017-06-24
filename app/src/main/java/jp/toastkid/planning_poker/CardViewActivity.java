package jp.toastkid.planning_poker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * @author toastkidjp
 */
public class CardViewActivity extends AppCompatActivity {

    private static final String EXTRA_KEY_CARD_TEXT = "card_text";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card);
        final TextView textView = (TextView) findViewById(R.id.card_activity_text);
        final String text = getIntent().getStringExtra(EXTRA_KEY_CARD_TEXT);
        textView.setText(text);
        if (text.length() >= 3) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120);
        }
    }

    public static Intent makeIntent(
            @NonNull final Context context,
            @NonNull final String text
            ) {
        final Intent intent = new Intent(context, CardViewActivity.class);
        intent.putExtra(EXTRA_KEY_CARD_TEXT, text);
        return intent;
    }
}
