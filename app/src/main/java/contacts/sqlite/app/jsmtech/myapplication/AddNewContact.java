package contacts.sqlite.app.jsmtech.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import model.Contact;
import sqlite.DBAdapter;

/**
 * Created by Sukriti on 5/25/16.
 */
public class AddNewContact extends AppCompatActivity {
    private Button submitButton;
    private EditText contactName;
    private EditText contactAge;
    private EditText contactPhone;
    private EditText contactEmail;
    DBAdapter dbAdapter;

    public AddNewContact() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_contact);

        dbAdapter = new DBAdapter(AddNewContact.this);


        submitButton = (Button) findViewById(R.id.submit);
        contactName = (EditText) findViewById(R.id.name);
        contactAge = (EditText) findViewById(R.id.age);
        contactEmail = (EditText) findViewById(R.id.email);
        contactPhone = (EditText) findViewById(R.id.phone);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cName = contactName.getText().toString().trim();
                String cAge = contactAge.getText().toString().trim();
                String cPhone = contactAge.getText().toString().trim();
                String cEmail = contactEmail.getText().toString().trim();

                if (cName.isEmpty() || cAge.isEmpty() || cPhone.isEmpty() || cEmail.isEmpty())
                    Toast.makeText(AddNewContact.this, "Empty Fields ", Toast.LENGTH_SHORT).show();
                else {


                    submitButton.setEnabled(false);

                    Contact newContact = new Contact();

                    newContact.setName(cName);
                    newContact.setAge(cAge);
                    newContact.setPhone(cPhone);
                    newContact.setEmail(cPhone);

                    dbAdapter.open();
                     dbAdapter.createContact(newContact);
                    //newContact.setId(id);
                    dbAdapter.close();

                    submitButton.setEnabled(true);
                    contactEmail.getText().clear();
                    contactPhone.getText().clear();
                    contactAge.getText().clear();
                    contactName.getText().clear();


                }

            }
        });


    }

}