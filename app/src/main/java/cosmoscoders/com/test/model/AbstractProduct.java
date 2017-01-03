package cosmoscoders.com.test.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AbstractProduct implements Serializable {
	private boolean isBookMarked;
	private String thumbUrl, title, brandName, salePrice, productId;

	public AbstractProduct() {
	}

	AbstractProduct(JSONObject object) throws JSONException {
		productId = object.getString("id");
		thumbUrl = object.optString("logo_thumb");
		title = object.getString("title");
		brandName = object.optString("brand_name");
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

	public String getSalePrice() {
		return salePrice;
	}

	public String getProductId() {
		return productId;
	}
}
