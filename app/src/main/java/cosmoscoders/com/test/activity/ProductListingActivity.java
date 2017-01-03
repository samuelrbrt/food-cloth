package cosmoscoders.com.test.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cosmoscoders.com.test.R;
import cosmoscoders.com.test.adapter.ProductListingAdapter;
import cosmoscoders.com.test.model.AbstractProduct;
import cosmoscoders.com.test.service.SyncIntentService;
import cosmoscoders.com.test.util.SettingStore;

@SuppressWarnings("ConstantConditions")
public class ProductListingActivity extends AppCompatActivity implements ChildEventListener {
	private RecyclerView productListRV;
	private ProductListingAdapter adapter;
	private SettingStore setting;
	private View loaderView, refreshView;
	private DatabaseReference reference;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Database synced, populate the Recycler View
			if (setting.isSynced()) {
				syncCompleted();
			} else {
				syncError();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_listing);

		// Toolbar init
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		loaderView = findViewById(R.id.pb_loader);
		refreshView = findViewById(R.id.tv_no_internet);

		// RecyclerView and adapter init
		productListRV = (RecyclerView) findViewById(R.id.rv_product_list);
		productListRV.setHasFixedSize(true);
		productListRV.setLayoutManager(new LinearLayoutManager(this));
		ProductListingAdapter adapter = new ProductListingAdapter();
		productListRV.setAdapter(adapter);

		setting = new SettingStore(this);
		// Load the list if firebase is already synced, start the service otherwise
		if (!setting.isSynced()) {
			startSync();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		reference = FirebaseDatabase.getInstance().getReference("abstract_product");
		reference.addChildEventListener(this);
	}

	private void startSync() {
		loaderView.setVisibility(View.VISIBLE);
		refreshView.setVisibility(View.GONE);
		startService(new Intent(this, SyncIntentService.class));
	}

	private void syncCompleted() {
		loaderView.setVisibility(View.GONE);
	}

	private void syncError() {
		// No internet connection, show reload button
		refreshView.setVisibility(View.VISIBLE);
		refreshView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshView.setVisibility(View.GONE);
				loaderView.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();

		try {
			unregisterReceiver(receiver);
		} catch (IllegalArgumentException e) {
			// Unregister without register.
			// Receiver no longer needed if the database is already synced
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		//Register receiver only if the database is not synced
		if (!new SettingStore(this).isSynced()) {
			registerReceiver(receiver,
			    new IntentFilter(SyncIntentService.ACTION_SERVER_RESPONSE));
		}
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String s) {
		AbstractProduct product = dataSnapshot.getValue(AbstractProduct.class);
		adapter.addProduct(product);
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String s) {

	}

	@Override
	public void onChildRemoved(DataSnapshot dataSnapshot) {

	}

	@Override
	public void onChildMoved(DataSnapshot dataSnapshot, String s) {

	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}
}
