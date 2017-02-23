package br.com.tedeschi.diapersgo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import br.com.tedeschi.diapersgo.R;

public class AddDealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner1;
    Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);

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
}
