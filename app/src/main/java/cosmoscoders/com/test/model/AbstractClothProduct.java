package cosmoscoders.com.test.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AbstractClothProduct extends AbstractProduct {
	private ArrayList<String> sizes;

	public AbstractClothProduct(JSONObject object) throws JSONException {
		super(object);

		sizes = new ArrayList<>();
		/*JSONArray sizeArray = object.getJSONArray("sizes");

		for (int i = 0; i < sizeArray.length(); i++) {
			sizes.add(sizeArray.getJSONObject(i).getString("size"));
		}*/
	}

	public ArrayList<String> getSizes() {
		return sizes;
	}
}
