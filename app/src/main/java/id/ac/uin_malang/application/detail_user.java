package id.ac.uin_malang.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class detail_user extends AppCompatActivity {

    //NetworkImageView thumb_image;
    ImageView thumb_image;
    TextView judul, tanggal, isi, author;
    String id_news;
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    Button buttonDel;
    Toolbar toolbar;

    public static final String TAG_ID       = "id";
    public static final String TAG_JUDUL    = "judul";
    public static final String TAG_TGL      = "tanggal";
    public static final String TAG_ISI      = "isi";
    public static final String TAG_GAMBAR   = "gambar";
    public static final String TAG_AUTHOR   = "author";



    private static final String Tag = detail_user.class.getSimpleName();

    private static final String url_detail = "http://alvin8mmudhoffar.000webhostapp.com/detail.php";

    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        toolbar = (Toolbar)findViewById(R.id.toolbar_du);
        buttonDel = (Button)findViewById(R.id.delet);
        judul = (TextView)findViewById(R.id.judulNews);
        tanggal = (TextView)findViewById(R.id.tanggalDetail);
        thumb_image = (ImageView)findViewById(R.id.gambarDetail);
        isi = (TextView)findViewById(R.id.isiDetail);
        author = (TextView)findViewById(R.id.authorDetail);
        id_news = getIntent().getStringExtra(TAG_ID);

        //action bar

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.)

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        if (!id_news.isEmpty()) {
            callDetailNews(id_news);

        }
    }

    private void callDetailNews(final String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_detail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Tag, "JSONResponse" + response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);

                            String Judul = obj.getString(TAG_JUDUL);
                            String Gambar = obj.getString(TAG_GAMBAR);
                            String Isi = obj.getString(TAG_ISI);
                            String Tanggal = obj.getString(TAG_TGL);
                            String Author = obj.getString(TAG_AUTHOR);

                            judul.setText(Judul);
                            tanggal.setText(Tanggal);
                            isi.setText(Html.fromHtml(Isi));
                            author.setText(Author);

                            Picasso.with(getApplicationContext())
                                    .load(Gambar)
                                    .into(thumb_image);

//                    if (obj.getString(TAG_GAMBAR) != "") {
//                        thumb_image.setImageUrl(Gambar, imageLoader);
//                    }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Tag, "DETAIL NEWS ERROR" +error.getMessage());
                Toast.makeText(detail_user.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }

        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
