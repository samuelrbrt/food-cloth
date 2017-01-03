package cosmoscoders.com.test.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import cosmoscoders.com.test.R;
import cosmoscoders.com.test.listener.OnConfirmListener;
import cosmoscoders.com.test.model.AbstractProduct;

public class ConfirmDialog extends DialogFragment {
	public static final String ARG_PRODUCT = "arg_product";

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AbstractProduct product = (AbstractProduct) getArguments().getSerializable(ARG_PRODUCT);
		String message = product.isBookMarked() ? getString(R.string.message_remove_bookmark, product.getTitle())
		                     : getString(R.string.message_add_bookmark, product.getTitle());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(message)
		    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
				    ((OnConfirmListener) getActivity()).onDialogPositive(getArguments());
			    }
		    })
		    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
				    dismiss();
			    }
		    });

		return builder.create();
	}

}
