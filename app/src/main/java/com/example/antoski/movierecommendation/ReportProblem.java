package com.example.antoski.movierecommendation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportProblem extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);
        Button send = (Button)findViewById(R.id.SendMail);

        EditText poraka = (EditText)findViewById(R.id.ReportID);

        if (send != null) {
            send.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendEmail();
                }
            });
        }


    }


    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"Stefan.antoski@hotmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[MoviePal] MainActivity Problem");
        EditText edit = (EditText)findViewById(R.id.ReportID);
        String porakaa = null;
        if (edit.getText() != null) {
            porakaa = edit.getText().toString();
        }
        else{
            Toast.makeText(ReportProblem.this,
                    "Please describe your problem.", Toast.LENGTH_LONG).show();


        }

        emailIntent.putExtra(Intent.EXTRA_TEXT, porakaa);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReportProblem.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
