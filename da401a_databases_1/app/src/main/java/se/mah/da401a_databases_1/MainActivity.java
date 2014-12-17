package se.mah.da401a_databases_1;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getFragmentManager().beginTransaction().replace(R.id.container, PeopleFragment.newInstance("", ""), "people").commit();
  }

}
