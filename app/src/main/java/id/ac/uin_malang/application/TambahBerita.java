package id.ac.uin_malang.application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class TambahBerita extends AppCompatActivity implements View.OnClickListener {
    private EditText edit1, edit2, edit3, edit4;
    private ImageView imageView;
    private Button button1, button2, button3;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL = "http://alvin8mmudhoffar.000webhostapp.com/upload.php";
    private String KEY_IMAGE = "image";
    private String KEY_JUDUL = "judul";
    private String KEY_ISI = "isi";
    private String KEY_TANGGAL = "tanggal";
    private String KEY_AUTHOR = "author";
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_berita);
        imageView = (ImageView)findViewById(R.id.imageAdmin);
        edit1 = (EditText)findViewById(R.id.judul);
        edit2 = (EditText)findViewById(R.id.isi);
        edit3 = (EditText)findViewById(R.id.author);
        edit4 = (EditText)findViewById(R.id.tanggal);
        button1 = (Button)findViewById(R.id.btnAmbil);
        button2 = (Button)findViewById(R.id.btnBack);
        button3 = (Button)findViewById(R.id.btnSimpan);
        button1.setOnClickListener(this);
        button3.setOnClickListener(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar_satu);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), list_admin.class);
                startActivity(intent);
            }
        });


    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(TambahBerita.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(TambahBerita.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String judul = edit1.getText().toString().trim();
                String image = getStringImage(bitmap);
                String isi = edit2.getText().toString().trim();
                String tanggal = edit4.getText().toString().trim();
                String author = edit3.getText().toString().trim();
                Map<String,String> params = new Hashtable<String, String>();

                params.put(KEY_JUDUL, judul);
                params.put(KEY_IMAGE, image);
                params.put(KEY_ISI, isi);
                params.put(KEY_TANGGAL, tanggal);
                params.put(KEY_AUTHOR,author);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onClick(View v){
        if(v==button1){
            showFileChooser();
        }
        else if (v == button3){
            uploadImage();
        }
    }


}
