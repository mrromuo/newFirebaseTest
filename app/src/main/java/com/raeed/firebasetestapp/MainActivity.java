/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raeed.firebasetestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public int ID;
    public String newId;
    public String userSex;
    Button update;
    EditText EditName,Email,Age;
    RadioButton Female,sex;

    RadioGroup radioGroup;
    TextView tvname,tvemail,tvage,tvsex;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    public DatabaseReference myRef_Name= database.getReference("name");
    public DatabaseReference myRef_Message = database.getReference("bessage");
    public DatabaseReference myRef_users = database.getReference("message");
    public DatabaseReference myRef_last_Id = database.getReference("Idlast");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditName = findViewById(R.id.PersonName);
        Email = findViewById(R.id.EmailAddress);
        Age = findViewById(R.id.editAge);
        radioGroup=findViewById(R.id.radioGroup);
        tvname = findViewById(R.id.tvName);
        tvage = findViewById(R.id.tvAge);
        tvemail = findViewById(R.id.tvEmail);
        tvsex = findViewById(R.id.tvSex);
        update = findViewById(R.id.send);
        Female =findViewById(R.id.female);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastId();
                ID++;
                newId="Test"+ID;
                String userNm=EditName.getText().toString();
                String userEm=Email.getText().toString();
                String userAge=Age.getText().toString();
                write_message(userNm,userSex,userEm,userAge,newId);
            }
        });

        // Write a message to the database


        /*User user= new User("Raeed Al Gassab","Mail",48,"exsamble@smble.com");
        String userId="Test500";
        myRef_Name.setValue("Hello, World!");
        myRef_Message.child("users").child(userId).setValue(user);
        // [END write_message]*/

        // [START read_message]
        // Read from the database
        myRef_Message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        // [END read_message]
    }

    int getLastId(){
        myRef_last_Id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id =snapshot.getValue(String.class);
                ID = Integer.valueOf(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return 500;
    }


    void write_message(String username, String usersex, String userEmail, String Age , String userId){
        User user= new User(username,usersex,Age,userEmail);
        myRef_Message.setValue("Hello, World!");
        myRef_last_Id.setValue(ID);
        myRef_Name.child("users").child(userId).setValue(user);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.female:
                if (checked) userSex ="Female";
                    break;
            case R.id.male:
                if (checked) userSex = "Male";
                    break;
            case R.id.other:
                if (checked) userSex="Other";
            break;
        }
    }

}