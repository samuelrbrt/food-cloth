package cosmoscoders.com.test.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cosmoscoders.com.test.R;
import cosmoscoders.com.test.service.SyncIntentService;
import cosmoscoders.com.test.util.SettingStore;

@SuppressWarnings("ConstantConditions")
public class ProductListingActivity extends AppCompatActivity {
	private RecyclerView mProductsRV;

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			//Database synced, populate the Recycler View
			loadList();

			//Hide progress bar
			findViewById(R.id.pb_loader).setVisibility(View.GONE);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_listing);

		// Toolbar setup
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

		// Load the list if firebase is already synced, start the service otherwise
		if (new SettingStore(this).isSynced()) {
			loadList();
		} else {
			startService(new Intent(this, SyncIntentService.class));

			// show progress bar
			findViewById(R.id.pb_loader).setVisibility(View.VISIBLE);
		}
	}

	private void loadList() {

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
}
