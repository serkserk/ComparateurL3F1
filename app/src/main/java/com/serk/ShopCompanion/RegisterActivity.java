package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité permet la création d'un compte pour l'application
 */

public class RegisterActivity extends AppCompatActivity {
    EditText Name, Mail, Password, PassConf;
    Button Register;
    TextView toLogin;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Mail = (EditText) findViewById(R.id.rMail);
        Name = (EditText) findViewById(R.id.rName);
        Password = (EditText) findViewById(R.id.rPassword);
        PassConf = (EditText) findViewById(R.id.rPassConf);
        Register = (Button) findViewById(R.id.bRegister);
        toLogin = (TextView) findViewById(R.id.toLogin);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //Fini l'activité courante pour retourner dans l'activité de login
                finish();
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = Mail.getText().toString();

                if (Mail.getText().toString().equals("") || Name.getText().toString().equals("") || Password.getText().toString().equals("")
                        || PassConf.getText().toString().equals("")) { //Vérifie si les champs sont remplis
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Attention");
                    builder.setMessage("Remplir tous les champs");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (!(Password.getText().toString().equals(PassConf.getText().toString()))) {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Attention");
                    builder.setMessage("Les mots de passe ne correspondent pas");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Password.setText("");
                            PassConf.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())) {
                    Mail.setError("Mail non valide");
                } else {
                    BackgroundTask bt = new BackgroundTask(RegisterActivity.this);
                    bt.execute("register", Mail.getText().toString(), Name.getText().toString(), Password.getText().toString());    //Exécute la requète en arrière plan
                }

            }
        });
    }


}
