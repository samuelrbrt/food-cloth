package cosmoscoders.com.test.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class SyncIntentService extends IntentService {
	//Broadcast action
	public static final String ACTION_SERVER_RESPONSE = "cosmoscoders.com.test.service.action.SERVER_RESPONSE";
	private static final String TAG = "SyncIntentService";
	//API URLS
	private static final String URL_CLOTHS = "https://sky-firebase.firebaseapp.com/mobmerry/cloths.json";
	private static final String URL_FOOD = "https://sky-firebase.firebaseapp.com/mobmerry/food.json";

	public SyncIntentService() {
		super("SyncIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			String cloths = getRequest(URL_CLOTHS);
			String food = getRequest(URL_FOOD);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	private String getRequest(String url) throws IOException {
		Request request = new Request.Builder()
		                      .url(url)
		                      .get()
		                      .build();
		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();

		String responseStr = response.body().string();

		Log.e("Response", responseStr);

		return responseStr;
	}
}
