package br.com.tedeschi.diapersgo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.foursquare.api.types.Venue;
import com.foursquare.placepicker.PlacePicker;

import br.com.tedeschi.diapersgo.R;
import br.com.tedeschi.diapersgo.model.Deals;
import br.com.tedeschi.diapersgo.rest.ApiClient;
import br.com.tedeschi.diapersgo.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Venue mPlace;
    EditText editTextVenueName;
    Spinner spinner_diaper_brand;
    Spinner spinner_diaper_model;
    Spinner spinner_diaper_size;
    private String TAG = "AddDealActivity";
    private EditText mNumberOfDiapers;
    private EditText mDiaperPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);

        editTextVenueName = (EditText) findViewById(R.id.edittext_venue);
        spinner_diaper_brand = (Spinner) findViewById(R.id.spinner_diaper_brand);
         spinner_diaper_model = (Spinner) findViewById(R.id.spinner_diaper_model);
        spinner_diaper_size = (Spinner) findViewById(R.id.spinner_diaper_size);

        mNumberOfDiapers = (EditText) findViewById(R.id.editText2);
        mDiaperPrice = (EditText) findViewById(R.id.editText3);



        spinner_diaper_brand.setOnItemSelectedListener(this);
    }

    private void pickPlace() {
        Intent intent = new Intent(this, PlacePicker.class);
        startActivityForResult(intent, 9001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PlacePicker.PLACE_PICKED_RESULT_CODE) {
             mPlace = data.getParcelableExtra(PlacePicker.EXTRA_PLACE);
            editTextVenueName.setText(mPlace.getName());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    public void onButtonSearch(View view) {
        pickPlace();
    }

    public void selfDestruct(View view) {

        String brand = spinner_diaper_brand.getSelectedItem().toString();
        String model = spinner_diaper_model.getSelectedItem().toString();
        String size = spinner_diaper_size.getSelectedItem().toString();
        String number = mNumberOfDiapers.getText().toString();
        String skuTemp = brand+model+size+number;
        String sku = Integer.toString(skuTemp.hashCode());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Deals> call = apiService.insert(mPlace.getId(), mPlace.getName(), mPlace.getLocation().getAddress(), mPlace.getLocation().getCity(), mPlace.getLocation().getState(), mPlace.getLocation().getCountry(), Float.toString(mPlace.getLocation().getLat()), Float.toString(mPlace.getLocation().getLng()), "CustomerFirstName", "CustomerLastName", "CustomerEmail", "CustomerCountry", sku, brand, model, "ProductModel", size, number, mDiaperPrice.getText().toString(), "DealType", "DealComment");
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
