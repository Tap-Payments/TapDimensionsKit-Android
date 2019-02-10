package com.tap.tapdimensionskit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Button btn = this.findViewById(R.id.btn);
        final int btnHeight = sharedPreferences.getInt(this.getString(R.string.preference_dimension_key), 70);

        btn.setHeight(btnHeight);


    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Button btn = this.findViewById(R.id.btn);
        final int btnHeight = sharedPreferences.getInt(this.getString(R.string.preference_dimension_key), 70);

        btn.setHeight(btnHeight);



    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_settings) {
            this.startActivity(SettingsActivity.start(this));
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

}
