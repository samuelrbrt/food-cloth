package cosmoscoders.com.test.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cosmoscoders.com.test.R;
import cosmoscoders.com.test.model.AbstractProduct;

public class ProductListingAdapter extends RecyclerView.Adapter<ProductListingAdapter.ViewHolder> {
	private ArrayList<AbstractProduct> list;

	public ProductListingAdapter() {
		list = new ArrayList<>();
	}

	@Override
	public ProductListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ProductListingAdapter.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public void addProduct(AbstractProduct product) {
		list.add(product);
		notifyItemInserted(getItemCount() - 1);
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		ImageView thumbNailIV, bookmarkIV;
		TextView titleTV, brandTV, salePriceTV, basePriceTV;

		ViewHolder(View itemView) {
			super(itemView);

			thumbNailIV = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
			bookmarkIV = (ImageView) itemView.findViewById(R.id.iv_bookmark);
			titleTV = (TextView) itemView.findViewById(R.id.tv_title);
			brandTV = (TextView) itemView.findViewById(R.id.tv_brand);
			salePriceTV = (TextView) itemView.findViewById(R.id.tv_sale_price);
			basePriceTV = (TextView) itemView.findViewById(R.id.tv_base_price);
		}
	}
}
