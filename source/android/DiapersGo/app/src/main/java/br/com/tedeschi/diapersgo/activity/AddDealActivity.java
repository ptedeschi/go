package br.com.tedeschi.diapersgo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import br.com.tedeschi.diapersgo.R;
import br.com.tedeschi.diapersgo.model.Deals;
import br.com.tedeschi.diapersgo.rest.ApiClient;
import br.com.tedeschi.diapersgo.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    PlaceAutocompleteFragment venueName;
    Spinner spinner_diaper_brand;
    Spinner spinner_diaper_model;
    Spinner spinner_diaper_size;
    private String TAG = "AddDealActivity";
    private Place mPlace;
    private EditText mNumberOfDiapers;
    private EditText mDiaperPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);

        venueName = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        spinner_diaper_brand = (Spinner) findViewById(R.id.spinner_diaper_brand);
         spinner_diaper_model = (Spinner) findViewById(R.id.spinner_diaper_model);
        spinner_diaper_size = (Spinner) findViewById(R.id.spinner_diaper_size);

        mNumberOfDiapers = (EditText) findViewById(R.id.editText2);
        mDiaperPrice = (EditText) findViewById(R.id.editText3);

        venueName.setHint("Estabelecimento");

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("BR").setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();

        venueName.setFilter(typeFilter);

        venueName.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());

                mPlace = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        spinner_diaper_brand.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinner_diaper_brand.getSelectedItem().equals("Pampers")) {

            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.diaper_brand_pampers_array, android.R.layout.simple_list_item_1);
            spinner_diaper_model.setAdapter(adapter2);
        } else if (spinner_diaper_brand.getSelectedItem().equals("Huggies")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.diaper_brand_huggies_array, android.R.layout.simple_list_item_1);
            spinner_diaper_model.setAdapter(adapter2);
        } else if (spinner_diaper_brand.getSelectedItem().equals("Mamypoko")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.diaper_brand_mamypoko_array, android.R.layout.simple_list_item_1);
            spinner_diaper_model.setAdapter(adapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void selfDestruct(View view) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Deals> call = apiService.insert(mPlace.getId(), mPlace.getName().toString(), mPlace.getAddress().toString(), "", "", "", Double.toString(mPlace.getLatLng().latitude), Double.toString(mPlace.getLatLng().longitude), "CustomerFirstName", "CustomerLastName", "CustomerEmail", "CustomerCountry", spinner_diaper_brand.getSelectedItem().toString(), spinner_diaper_model.getSelectedItem().toString(), "ProductModel", spinner_diaper_size.getSelectedItem().toString(), mNumberOfDiapers.getText().toString(), mDiaperPrice.getText().toString(), "DealType", "DealComment");
        call.enqueue(new Callback<Deals>() {

            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Deals> call, Response<Deals> response) {
                Log.d("TAG", "onResponse");
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Deals> call, Throwable t) {
                Log.e("TAG", "onFailure", t);
            }
        });
    }
}
