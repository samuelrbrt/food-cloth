package cosmoscoders.com.test.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class ListDialog extends DialogFragment {
	public static final String ARG_TITLE = "arg_title";
	public static final String ARG_ITEM_LIST = "arg_item_list";

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString(ARG_TITLE);
		String[] items = getArguments().getStringArray(ARG_ITEM_LIST);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title).setItems(items, null);
		return builder.create();
	}
}
