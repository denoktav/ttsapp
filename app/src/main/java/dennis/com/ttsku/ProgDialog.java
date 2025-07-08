package dennis.com.ttsku;

import android.content.Context;
import android.app.AlertDialog;
public class ProgDialog {
    private Context context;
    private AlertDialog dialog;

    public ProgDialog(Context context) {
        this.context = context;
    }

    public void showProgDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.activity_download_data);

        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

    }

    public void closeProgDialog() {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
