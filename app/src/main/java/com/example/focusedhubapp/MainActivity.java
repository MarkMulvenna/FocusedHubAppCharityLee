package com.example.focusedhubapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button logout;
    private FirebaseUser user2;
    private DatabaseReference reference;
    private String userId;
    private boolean showText = false;

    private ImageView imgAssociation;

    public Signup signup = new Signup();

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class AddSignup extends AsyncTask {
        String url = "jdbc:mysql://91.208.99.2:1232/focusedh_db";
        String user = "focusedh_dbusr";
        String password = "dK£T%iqCKv";


        ResultSet rs;
        String result;


        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection myConn = DriverManager.getConnection(url, user, password);
                result = "Connection Established";
                System.out.println("Test Successful");
                Statement myStatement = myConn.createStatement();
                int rs = myStatement.executeUpdate("INSERT INTO `Contact_Signups` (`SignupID`, `First Name`, `Surname`, `Contact Number`, `Mobile Number`, `Address`, `Email`, `Main_Charity`, `Charity Split`, `Day Selection`,`Time of day`, `Notes`) VALUES (NULL, '" + signup.getFirstName() + "', '" + signup.getLastName() + "', '" + signup.getContactTel() + "', '" + signup.getMobile() + "', '" + signup.getAddress() + "', '" + signup.getEmail() + "', '" + signup.getMainCharity() + "', '" + signup.getDonationSelection() + "', '" + signup.getContactDay() + "', '" + signup.getTimeofDay() + "', '" + signup.getNotes() + "');");


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Test Unsuccessful");

            }
            String from = "leemcmaw6@gmail.com";
            String emailPassword = "31Ashleighpark1";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, emailPassword);
                        }
                    });
            try {
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("mmulvenna2019@gmail.com"));
                message.setSubject("Form submitted into PHP MY ADMIN " );
                String timeStamp = new SimpleDateFormat("dd/MM/yyyy_HHmm").format(new Date());
                String text = "Form has been submitted " + signup.getFirstName() + " at " + timeStamp + " DD/MM/YYYY_HHMM format";
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(text);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
                Transport.send(message);


            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            TextView
                    firstName = findViewById(R.id.editTextTextPersonName),
                    surname = findViewById(R.id.editTextTextPersonName2),
                    address = findViewById(R.id.editTextTextPostalAddress),
                    contact = findViewById(R.id.editTextPhone),
                    mobile = findViewById(R.id.editTextPhone2),
                    email = findViewById(R.id.editTextTextEmailAddress),
                    notes = findViewById(R.id.editTextTextMultiLine);

            firstName.setText("");
            surname.setText("");
            address.setText("");
            contact.setText("");
            mobile.setText("");
            email.setText("");
            notes.setText("");



        }
    }


    Button
            SubmitBtn;
    TextView FirstName,Surname,Eaddress,Mobile,Tele,Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgAssociation = findViewById(R.id.imgAssociation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        user2 = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user2.getUid();
        final TextView greetingTextView = (TextView) findViewById(R.id.textView12);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            User1 userProfile = snapshot.getValue(User1.class);
            if(userProfile != null){
                String charity = userProfile.charity;
                greetingTextView.setText(charity);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(MainActivity.this,"Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });


logout = (Button) findViewById(R.id.buttonLogout);
logout.setOnClickListener(v -> {
    FirebaseAuth.getInstance().signOut();
    startActivity(new Intent(MainActivity.this,MainActivity2.class));
});

        Spinner spinner = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        Spinner spinnerContact = findViewById(R.id.spinner5);

        String[] contactMethods = {"Mobile Phone", "Landline", "Email", "Text"};

        ArrayAdapter<String>  contactMethodsAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contactMethods);

        spinnerContact.setAdapter(contactMethodsAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(this);
//time of day
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Times, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

//Button

        TextView
                firstName = findViewById(R.id.editTextTextPersonName),
                surname = findViewById(R.id.editTextTextPersonName2),
                address = findViewById(R.id.editTextTextPostalAddress),
                contact = findViewById(R.id.editTextPhone),
                mobile = findViewById(R.id.editTextPhone2),
                email = findViewById(R.id.editTextTextEmailAddress),
                notes = findViewById(R.id.editTextTextMultiLine);
        TextView main = findViewById(R.id.textView12);
        Spinner
                day = findViewById(R.id.spinner3),

                time = findViewById(R.id.spinner2),
                donation = findViewById(R.id.spinner1);




        Button button = findViewById(R.id.buttonSubmit);
        
        String[] charities = {"Emerge", "Raglan", "St Pauls GAC", "Carrickfergus YMCA", "Lecknagh Neighbourhood Partnership", "Macosquin Community Pre-School",
                "Sea2It", "Harry's Place", "R.A.T.H Community Group", "Turning Point NI", "Ballynahinch Rugby Club" };


        List<User> userList = new ArrayList<>();
        User user1 = new User("Emerge","Suite D, Building, 3 The Sidings, Lisburn BT28 3AJ","ECSLN101");
        userList.add(user1);
        User user2 = new User("Raglan","20-24 Queen St, Ballymena BT42 2BD","RAGBA101");
        userList.add(user2);
        User user3 = new User("St Pauls GAC","3 Belfast Rd, Holywood BT18 9EH","STPBE101");
        userList.add(user3);
        User user4 = new User("Carrickfergus YMCA","30 - 34 Irish Quarter W, Carrickfergus BT38 8AT","YMCCF101");
        userList.add(user4);
        User user5 = new User("Lecknagh Neighbourhood Partnership","Magherafelt","LNPMF101");
        userList.add(user5);
        User user6 = new User("Macosquin Community Pre-School","24 Ramsey Park, Coleraine","MACCO101");
        userList.add(user6);
        User user7 = new User("Sea2It","Coleraine","SE2AN101");
        userList.add(user7);
        User user8 = new User("Harry's Place","25 High St, Ballynahinch BT24 8AB","HAPBN101");
        userList.add(user8);
        User user9 = new User("R.A.T.H Community Group","Room 21 Dunanney Centre, Rathmullan Dr, Newtownabbey BT37 9DQ","RACNA101");
        userList.add(user9);
        User user10 = new User("Turning Point NI","62 Mill St, Ballymena BT43","TNPBA101");
        userList.add(user10);
        User user11 = new User("Ballynahinch Rugby Club","Ballymacarn Park, 6 Mountview Road, Ballynahinch","RUGBH101");
        userList.add(user11);
        User user12 = new User("None","None","None");
        userList.add(user12);


        FirstName = (EditText) findViewById(R.id.editTextTextPersonName);
        Surname = (EditText) findViewById(R.id.editTextTextPersonName2);
        SubmitBtn = (Button) findViewById(R.id.buttonSubmit);
        Eaddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Mobile = (EditText) findViewById(R.id.editTextPhone2);
        Tele = (EditText) findViewById(R.id.editTextPhone);
        Address = (EditText) findViewById(R.id.editTextTextPostalAddress);
        //button
button.setOnClickListener(v -> {
    final String Name=FirstName.getText().toString();
    final String Sname=Surname.getText().toString();
    final String Eadd=Eaddress.getText().toString();
    final String Mob = Mobile.getText().toString();
    final String Tel = Tele.getText().toString();
    final String Add = Address.getText().toString();

    if(Add.length()==0)
    {
        Address.requestFocus();
        Address.setError("FIELD CANNOT BE EMPTY");
    }

    if(Mob.length()==0)
    {
        Mobile.requestFocus();
        Mobile.setError("FIELD CANNOT BE EMPTY");
    }
    else if(Mob.length()!=11) {
        Mobile.requestFocus();
        Mobile.setError("Phone number must be 11 numbers ");
                }
   if(Tel.length()==0)
    {
        Tele.requestFocus();
        Tele.setError("FIELD CANNOT BE EMPTY");
    }
    else if(Tel.length()!=11) {
        Tele.requestFocus();
        Tele.setError("Phone number must be 11 numbers ");
    }
     if(Eadd.length()==0)
    {
        Eaddress.requestFocus();
        Eaddress.setError("FIELD CANNOT BE EMPTY");
    }

    if(Name.length()==0)
    {
        FirstName.requestFocus();
        FirstName.setError("Enter only characters");
    }
    else if(!Name.matches("[a-zA-Z ]+"))
    {
        FirstName.requestFocus();
        FirstName.setError("Enter only characters");
    }
    if(Sname.length()==0)
    {
        Surname.requestFocus();
        Surname.setError("FIELD CANNOT BE EMPTY");
    }
    else
    {
        Toast.makeText(MainActivity.this,"Contact Details Submitted",Toast.LENGTH_LONG).show();
    }
    signup = new Signup(firstName.getText().toString(),surname.getText().toString(),address.getText().toString(),contact.getText().toString(),mobile.getText().toString(),email.getText().toString(),main.getText().toString(),donation.getSelectedItem().toString(),day.getSelectedItem().toString(),time.getSelectedItem().toString(),notes.getText().toString());        AddSignup addSignup = new AddSignup();
    addSignup.execute();

});

        ArrayAdapter<String>  charityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, charities);
        spinner.setAdapter(charityAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String desc = userList.get(position).toString();
                Toast.makeText(MainActivity.this, desc, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

private void displayUserData(User user){

            String charity = user.getCharity();
            String address = user.getAddress();
            String code = user.getCode();
            String userData = "Charity: " + charity + "\nAddress: " + address + "\nCode: " + code;
            Toast.makeText(this, userData, Toast.LENGTH_LONG).show();

}
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, PinControl.class));
    }
}