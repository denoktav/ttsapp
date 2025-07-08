package dennis.com.ttsku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class about extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.img).setOnClickListener(v -> finish());

        findViewById(R.id.rly).setOnClickListener(v -> finish());

        findViewById(R.id.button).setOnClickListener((View) -> {
            String url = "https://play.google.com/store/apps/details?id=com.dennis.rpss";
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
            startActivity(intent);
        });

    }

    public void onBackPressed() {
        return;
    }
}