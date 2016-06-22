package contacts.sqlite.app.jsmtech.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.ContactAdapter;
import model.Contact;
import sqlite.DBAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recList;
    private ArrayList<Contact> mContactsList = new ArrayList<>();
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBAdapter db = new DBAdapter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddNewContact.class);
                startActivity(intent);

            }
        });

        recList = (RecyclerView) findViewById(R.id.list_of_contacts_rv);

        final LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        mAdapter = new ContactAdapter(MainActivity.this, mContactsList);
        recList.setAdapter(mAdapter);

        // Get All The Contacts From the Sqlite Database!
        // First step always is to oPen the data base and the last step is to close the datbase.

        db.open();

        Cursor c = db.getAllContacts();

        if(c.moveToFirst()) {

            do {
                // Id, name, age , phone, email
                Contact contact = new Contact();
                contact.setId(c.getLong(0)); // first column first row
                contact.setName(c.getString(1));
                contact.setAge(c.getString(2));
                contact.setPhone(c.getString(3));
                contact.setEmail(c.getString(4));
                mContactsList.add(contact);
            } while (c.moveToNext());
        }
        db.close();

        Toast.makeText(MainActivity.this, "Contacts Added", Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
