package cosmoscoders.com.test.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AbstractFoodProduct extends AbstractProduct {
	private ArrayList<String> tags;

	public AbstractFoodProduct(JSONObject object) throws JSONException {
		super(object);
		tags = new ArrayList<>();

		JSONArray tagsArray = object.getJSONArray("tags");

		for (int i = 0; i < tagsArray.length(); i++) {
			tags.add(tagsArray.getString(i));
		}
	}

	public ArrayList<String> getTags() {
		return tags;
	}
}
