package cosmoscoders.com.test.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingStore {
	private static final String PREF_FILE_NAME = "preference";
	private static final String PREF_IS_SYNCED = "is_synced";

	private SharedPreferences mPref;

	public SettingStore(Context context) {
		mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
	}

	public boolean isSynced() {
		return mPref.getBoolean(PREF_IS_SYNCED, false);
	}

	public void setSynced(boolean synced) {
		mPref.edit().putBoolean(PREF_IS_SYNCED, synced).apply();
	}
}
