package dennis.com.ttsku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class good extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);

        findViewById(R.id.img_good).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void onBackPressed() {
        return;
    }
}
