package com.example.smartwastagesystem;

import android.Manifest;
//import android.graphics.fonts.Font;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.victor.loading.book.BookLoading;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class mreport extends AppCompatActivity {

    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private Firebase mRef;

    private static int SPLASH_TIME_OUT = 4000;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_mreport);

        Button shareR = (Button) findViewById(R.id.buttonsharereport);
        shareR.setEnabled(false);

        BookLoading bl = (BookLoading) findViewById(R.id.bookloading);
        bl.start();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                shareR.setEnabled(true);

            }
        }, SPLASH_TIME_OUT);

        shareR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report();
                Toast.makeText(getApplicationContext(),"Report saved Successfully...",Toast.LENGTH_LONG).show();
                shareR.setEnabled(false);
            }
        });


        }


        public void Report() {
            Log.d("firebase","firebase");
            mRef = new Firebase("https://smart-waste-collection-ef779-default-rtdb.asia-southeast1.firebasedatabase.app/number");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    double finalValue = Double.parseDouble(value);
                    double val;
                    val = (1 - (finalValue / 30)) * 100;

                    TextView tw = (TextView) findViewById(R.id.prcntg);
                    TextView l = (TextView) findViewById(R.id.loading);
                    BookLoading book = (BookLoading) findViewById(R.id.bookloading);
                    book.stop();
                    l.setText("");
                    tw.setText("Waste Report is Successfuly Generated ! \n Save in your Mobile Memory named WasteReport.pdf \n Please Share report with SWC Admin Thanks  ");


                    //create PDF
                    //create document object
                    //Document document = new Document();

                    //output file path
                    // String outpath = Environment.getExternalStorageDirectory() + "/WasteReport.pdf";
                    String outpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
                    File file = new File(outpath, "WasteReport.pdf");


                    try {
                        OutputStream outputStream = new FileOutputStream(file);

                        // PdfWriter.getInstance(document, new FileOutputStream(outpath));
                        Document document = new Document();
                        PdfWriter.getInstance(document,outputStream);
                        document.open();
                        //meta data
                        document.addTitle("My first PDF");
                        document.addSubject("Using iText");
                        document.addKeywords("Java, PDF, iText");
                        document.addAuthor("Ashok Kumar");
                        document.addCreator("Ashok Kumar");

                        //document titile page
                        Paragraph preface = new Paragraph();
                        // We add one empty line
                        addEmptyLine(preface, 1);
                        // Lets write a big header
                        preface.add(new Paragraph("Smart Waste Collection System", catFont));
                        preface.setAlignment(Element.ALIGN_CENTER);

                        addEmptyLine(preface, 3);
                        // Will create: Report generated by: _name, _date
                        preface.add(new Paragraph("Report generated by: Smart Waste Collection System " + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                smallBold));
                        addEmptyLine(preface, 2);
                        preface.add(new Paragraph("This document describe the waste percentage in all the bins. ",
                                smallBold));

                        addEmptyLine(preface, 2);

                        preface.add(new Paragraph("This Document is Generated Automaticly with sensor value.and you must have to send the this doc to SWC Admintration between 8:00 PM -9:00 PM .Thank You. ;-).",
                                redFont));


                        addEmptyLine(preface, 4);

                        final PdfPTable table = new PdfPTable(4);


                        PdfPCell c1 = new PdfPCell(new Phrase("Date:"));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase("Bin"));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase("Waste Percentage"));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);


                        c1 = new PdfPCell(new Phrase("Location"));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(c1);

                        table.setHeaderRows(1);

                        table.addCell(String.valueOf(new Date()));
                        table.addCell("Bin1");
                        //    table.addCell(String.valueOf(val) + "%");
                        table.addCell(finalValue + "%");

                        table.addCell("Eswar college of Engineering");
                        table.addCell(String.valueOf(new Date()));
                        table.addCell("Bin2");
                        table.addCell("85%");
                        table.addCell("Prakash Nagar");

                        table.addCell(String.valueOf(new Date()));
                        table.addCell("Bin3");
                        table.addCell("40%");
                        table.addCell("Arundelpet");


                        table.addCell(String.valueOf(new Date()));
                        table.addCell("Bin4");
                        table.addCell("60%");
                        table.addCell("Barumpet");
                        document.add(preface);
                        document.add(table);

                        document.close();


                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


//                        Button shareR = (Button) findViewById(R.id.buttonsharereport);
//                        shareR.setEnabled(true);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getApplicationContext(),"Fail to get data",Toast.LENGTH_LONG).show();
                }
            });



        }

    private void addEmptyLine (Paragraph paragraph,int number){
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void viewpdf(View view) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"dakstar143@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Smart Waste Collection System");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Waste Report  "  + String.valueOf(new Date()) );
        File root = Environment. getExternalStorageDirectory();
        String pathToMyAttachedFile = "WasteReport.pdf";
        File file = new File(root, pathToMyAttachedFile);
        if (!file.exists() || !file.canRead()) {
            return;
        }
        Uri uri = Uri.fromFile(file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));

    }

    }


