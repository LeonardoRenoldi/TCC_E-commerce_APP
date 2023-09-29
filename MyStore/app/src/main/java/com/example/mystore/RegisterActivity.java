package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText ufirstname, ulastname, uemail, upassword, uconfpassword, ucontactnumber;
    Button btnRegister;
    TextInputLayout userFirstnameWrapper, userLastNameWrapper, userEmailWrapper, userPasswordWrapper,
            userConfPasswordWrapper, userContactWrapper;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        ufirstname = findViewById(R.id.userFirstName);
        ulastname = findViewById(R.id.userLastName);
        uemail = findViewById(R.id.userEmailAdress);
        upassword = findViewById(R.id.userCrtPassword);
        uconfpassword = findViewById(R.id.userConfPassword);
        ucontactnumber = findViewById(R.id.userContactNumber);

        userFirstnameWrapper = findViewById(R.id.userFirstNameWrapper);
        userLastNameWrapper = findViewById(R.id.userLastNameWrapper);
        userEmailWrapper = findViewById(R.id.userEmailWrapper);
        userPasswordWrapper = findViewById(R.id.passwordWrapper);
        userConfPasswordWrapper = findViewById(R.id.confPasswordWrapper);
        userContactWrapper = findViewById(R.id.contactNumberWrapper);


        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser()!= null){
// usuario logado e pode entrar em outra activity
                }else{

                }
               String firstname = ufirstname.getText().toString().trim();
               String lastname = ulastname.getText().toString().trim();
               String email = uemail.getText().toString().trim();
               String password = upassword.getText().toString().trim();
               String confpassword = uconfpassword.getText().toString().trim();
               String contactno = ucontactnumber.getText().toString().trim();
               if (firstname.isEmpty()){
                   userFirstnameWrapper.setError("Enter Firstname");
                   userFirstnameWrapper.requestFocus();
                   return;
               }
               if (lastname.isEmpty()){
                   userLastNameWrapper.setError("Enter Lastname");
                   userLastNameWrapper.requestFocus();
                   return;
               }
               if (email.isEmpty()){
                   userEmailWrapper.setError("Enter Email");
                   userEmailWrapper.requestFocus();
                   return;
               }
               if (password.isEmpty()){
                   userPasswordWrapper.setError("Enter Password");
                   userPasswordWrapper.requestFocus();
                   return;
               }
               if (confpassword.isEmpty()){
                   userConfPasswordWrapper.setError("Enter Confirm Password");
                   userConfPasswordWrapper.requestFocus();
                   return;
               }
               if (!password.equals(confpassword)){
                   userConfPasswordWrapper.setError("Password didn't match");
                   userConfPasswordWrapper.requestFocus();
                   return;
               }
               if (contactno.isEmpty()){
                   userContactWrapper.setError("Enter Contact Number");
                   userContactWrapper.requestFocus();
                   return;
               }
                Map<String,Object> userdb = new HashMap<>();
                userdb.put("Firstname", firstname);
                userdb.put("Lastname", lastname);
                userdb.put("Email", email);
                userdb.put("Password", password);
                userdb.put("ConfirmPassword", confpassword);
                userdb.put("ContactNumber", contactno);

                db.collection("user")
                        .add(userdb);

               mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           // adicionar informação adicional no firebase DB
                           User user = new User(firstname, lastname, email, contactno);
                           FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful()){
                                               Toast.makeText(RegisterActivity.this, "User Create successfully", Toast.LENGTH_LONG).show();
                                               Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                                               startActivity(intent);
                                           }else {
                                               Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   });
                       }else {
                           Toast.makeText(RegisterActivity.this, task.getException().getMessage()
                           , Toast.LENGTH_LONG).show();
                       }
                   }
               });
            }
        });
    }
}