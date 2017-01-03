package cosmoscoders.com.test.model;

import org.json.JSONException;
import org.json.JSONObject;

public class AbstractProduct {
	private boolean isBookMarked;
	private String thumbUrl, title, brandName, basePrice, salePrice, productId;

	AbstractProduct(JSONObject object) throws JSONException {
		productId = object.getString("id");
		thumbUrl = object.optString("logo_thumb");
		title = object.getString("title");
		brandName = object.optString("brand_name");
		basePrice = object.getString("base_price");
		salePrice = object.getString("sale_price");
		isBookMarked = object.optBoolean("is_bookmarked");
	}

	public boolean isBookMarked() {
		return isBookMarked;
	}

	public void setBookMarked(boolean bookMarked) {
		isBookMarked = bookMarked;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getBasePrice() {
		return basePrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public String getProductId() {
		return productId;
	}
}
