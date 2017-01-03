package cosmoscoders.com.test.listener;

import cosmoscoders.com.test.model.AbstractProduct;

public interface OnProductListItemListener {
	void onBookmark(AbstractProduct product, int position);
	void onItemClick(AbstractProduct product, int position);
	void onTagClick(String[] tags);
	void onSizeClick(String[] sizes);
}
