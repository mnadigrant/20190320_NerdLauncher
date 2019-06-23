package com.bignerdranch.android.nerdlauncher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    private int SETTINGS_ACTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);
        String themeName = pref.getString("theme", "Default");
        if (themeName.equals("Default")) {
            setTheme(R.style.AppTheme);
        } else if (themeName.equals("Purple Theme")) {
            Toast.makeText(this, "set theme", Toast.LENGTH_SHORT).show();
            setTheme(R.style.NewAppTheme);
        } else if (themeName.equals("Light Theme")) {
            Toast.makeText(this, "set theme", Toast.LENGTH_SHORT).show();
            setTheme(R.style.AppThemeLight);
            Toast.makeText(this, "Theme has been reset to " + themeName,
                    Toast.LENGTH_SHORT).show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.settings, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {

                case R.id.action_settings:
                    startActivityForResult(new Intent(this,
                            SettingsActivity.class), SETTINGS_ACTION);
            }
            return super.onOptionsItemSelected(item);
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == SETTINGS_ACTION) {
                if (resultCode == SettingsActivity.RESULT_CODE_THEME_UPDATED) {
                    finish();
                    startActivity(getIntent());
                    return;
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

