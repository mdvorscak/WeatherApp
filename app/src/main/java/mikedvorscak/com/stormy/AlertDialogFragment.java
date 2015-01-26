package mikedvorscak.com.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by mike on 1/25/15.
 */
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                          .setTitle(context.getString(R.string.error_title))
                                          .setMessage(message)
                                          .setPositiveButton(context.getString(R.string.error_positive_button), null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
