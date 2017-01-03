package cosmoscoders.com.test.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cosmoscoders.com.test.model.AbstractClothProduct;
import cosmoscoders.com.test.model.AbstractFoodProduct;
import cosmoscoders.com.test.util.SettingStore;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
@SuppressWarnings("ConstantConditions")
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
			JSONObject cloth = new JSONObject(getRequest(URL_CLOTHS));
			JSONObject food = new JSONObject(getRequest(URL_FOOD));
			JSONArray clothProducts = cloth.getJSONArray("products");
			JSONArray foodProducts = food.getJSONArray("products");

			DatabaseReference reference = FirebaseDatabase
			                                  .getInstance(FirebaseApp.getInstance()).getReference("abstract_cloths");

			for (int i = 0; i < clothProducts.length(); i++) {
				reference.push().setValue(new AbstractClothProduct(clothProducts.getJSONObject(i)));
			}

			reference = FirebaseDatabase.getInstance(FirebaseApp.getInstance()).getReference("abstract_food");

			for (int i = 0; i < foodProducts.length(); i++) {
				reference.push().setValue(new AbstractFoodProduct(foodProducts.getJSONObject(i)));
			}

			new SettingStore(this).setSynced(true);
		} catch (IOException | JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}

		Intent broadcast = new Intent(ACTION_SERVER_RESPONSE);
		sendBroadcast(broadcast);
	}

	private String getRequest(String url) throws IOException {
		Request request = new Request.Builder()
		                      .url(url)
		                      .get()
		                      .build();
		OkHttpClient client = new OkHttpClient();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
