package br.com.tedeschi.diapersgo.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import br.com.tedeschi.diapersgo.R;
import br.com.tedeschi.diapersgo.model.Deals;
import br.com.tedeschi.diapersgo.rest.ApiClient;
import br.com.tedeschi.diapersgo.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText venueName;
    Spinner spinner1;
    Spinner spinner2;
    private String TAG = "AddDealActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);

        venueName = (EditText) findViewById(R.id.editTextVenue);
        spinner1 = (Spinner) findViewById(R.id.spinner_diaper_brand);
         spinner2 = (Spinner) findViewById(R.id.spinner_diaper_model);

        spinner1.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinner1.getSelectedItem().equals("Pampers")) {

            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.diaper_brand_pampers_array, android.R.layout.simple_list_item_1);
            spinner2.setAdapter(adapter2);
        } else if (spinner1.getSelectedItem().equals("Huggies")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.diaper_brand_huggies_array, android.R.layout.simple_list_item_1);
            spinner2.setAdapter(adapter2);
        } else if (spinner1.getSelectedItem().equals("Mamypoko")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.diaper_brand_mamypoko_array, android.R.layout.simple_list_item_1);
            spinner2.setAdapter(adapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void selfDestruct(View view) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Deals> call = apiService.insert("VenueFoursquareId", venueName.getText().toString(), "VenueAddress", "VenueCity", "SP", "VenueCountry", "-22.852876134654547", "-47.02767489301763", "CustomerFirstName", "CustomerLastName", "CustomerEmail", "CustomerCountry", "ProductName", "ProductBrand", "ProductModel", "G", "64", "55.55", "DealType", "DealComment");
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
