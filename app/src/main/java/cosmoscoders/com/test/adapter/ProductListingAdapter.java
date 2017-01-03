package cosmoscoders.com.test.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cosmoscoders.com.test.R;
import cosmoscoders.com.test.listener.OnProductListItemListener;
import cosmoscoders.com.test.model.AbstractProduct;

public class ProductListingAdapter extends RecyclerView.Adapter<ProductListingAdapter.ViewHolder> {
	private ArrayList<AbstractProduct> list;
	private Picasso picasso;
	private OnProductListItemListener onProductListItemListener;

	public ProductListingAdapter(OnProductListItemListener onProductListItemListener) {
		list = new ArrayList<>();
		this.onProductListItemListener = onProductListItemListener;
	}

	@Override
	public ProductListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		picasso = Picasso.with(parent.getContext());
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final ProductListingAdapter.ViewHolder holder, final int position) {
		AbstractProduct product = list.get(position);

		picasso.load(product.getThumbUrl()).resize(80, 100).into(holder.thumbNailIV);

		if (product.isBookMarked()) {
			holder.bookmarkIV.setImageResource(R.drawable.ic_bookmark_amber_400_24dp);
		} else {
			holder.bookmarkIV.setImageResource(R.drawable.ic_bookmark_grey_400_24dp);
		}

		holder.titleTV.setText(product.getTitle());
		holder.brandTV.setText(product.getBrandName());
		holder.salePriceTV.setText(product.getSalePrice());
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
		TextView titleTV, brandTV, salePriceTV;

		ViewHolder(View itemView) {
			super(itemView);

			thumbNailIV = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
			bookmarkIV = (ImageView) itemView.findViewById(R.id.iv_bookmark);
			titleTV = (TextView) itemView.findViewById(R.id.tv_title);
			brandTV = (TextView) itemView.findViewById(R.id.tv_brand);
			salePriceTV = (TextView) itemView.findViewById(R.id.tv_sale_price);

			bookmarkIV.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onProductListItemListener.onBookmark(list.get(getAdapterPosition()), getAdapterPosition());
				}
			});
		}
	}
}
