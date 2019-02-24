package com.example.health.v7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class submit_data extends AppCompatActivity {

   // private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    EditText patient_name,patient_age,addr,fam,village,block,district,pstate,occupation,aadhar,cont1,cont2;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DatabaseReference mRef;
    //String phoneNo,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_data);

        mRef = FirebaseDatabase.getInstance().getReference("Patient");

        Button button = findViewById(R.id.button);

        patient_name = findViewById(R.id.name);
        patient_age = findViewById(R.id.age);
        radioGroup = findViewById(R.id.radioMarriage);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);

        fam = findViewById(R.id.fam);
        addr = findViewById(R.id.address);
        village = findViewById(R.id.ward);
        block = findViewById(R.id.block);
        district = findViewById(R.id.district);
        pstate = findViewById(R.id.state);
        occupation = findViewById(R.id.occupation);
        aadhar = findViewById(R.id.aadhar);
        cont1 = findViewById(R.id.contact);
        cont2 = findViewById(R.id.contact2);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if(bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(),"data:"+bundle.getString("some"),Toast.LENGTH_SHORT).show();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        getValues();
                        Toast.makeText(submit_data.this, "Patient Details Entered Successfully", Toast.LENGTH_SHORT).show();
                        Intent n1 = new Intent(submit_data.this,submit_symptoms.class);
                        startActivity(n1);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(submit_data.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public void getValues() {

        String patientID = mRef.push().getKey();
        String name = patient_name.getText().toString();
        String age = patient_age.getText().toString();
        String martial = radioButton.getText().toString();
        String family = fam.getText().toString();
        String address = addr.getText().toString();
        String ward = village.getText().toString();
        String taluka = block.getText().toString();
        String dist = district.getText().toString();
        String state_patient = pstate.getText().toString();
        String occu = occupation.getText().toString();
        String aadh = aadhar.getText().toString();
        String contact1 = cont1.getText().toString();
        String contact2 = cont2.getText().toString();

        Patient patient = new Patient(name,age,martial,family,address,ward,taluka,dist,state_patient,occu,aadh,contact1,contact2);
        mRef.child(contact1).setValue(patient);
        //sendsms();
    }

   /* protected void sendsms() {
        phoneNo = cont1.getText().toString();
        message = "test";

    SmsManager smsManager = SmsManager.getDefault();
    smsManager.sendTextMessage(phoneNo, null, message, null, null);
    Toast.makeText(getApplicationContext(), "SMS sent.",
            Toast.LENGTH_LONG).show();
}
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        File c = getCacheDir();
        c.deleteOnExit();
    }
 public static boolean deleteDir(File c)
 {
     if(c != null && c.isDirectory())
     {
         String[] children = c.list();
         for(int i = 0;i<children.length;i++)
         {
             boolean success = deleteDir(new File(c,children[i]));
             if(!success)
             {
                 return false;
             }
         }
         return c.delete();
     }else if(c!= null && c.isFile())
     {
         return c.delete();
     }
     else
     {
         return false;
     }
 }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return ();
                }
            }
        }*/
    }