package id.ac.uin_malang.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class list_admin extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

//    public static final String DATA_URL = "http://mobiles.dx.am/lihatdata.php";
    public static final String DATA_URL = "http://alvin8mmudhoffar.000webhostapp.com/lihatdata.php";
    public  static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_IMAGE = "gambar";
    public static final String TAG_TANGGAL = "tanggal";

    private SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    RequestQueue requestQueue;
    List<ModelListAdmin> modelListAdmins;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admin);

        toolbar = (Toolbar)findViewById(R.id.tolList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerTemp);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        modelListAdmins=new ArrayList<>();

        swipeRefreshLayout =(SwipeRefreshLayout)findViewById(R.id.refresh);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                adapter.notifyDataSetChanged();
                getData();
            }
        });

        //swipeRefreshLayout.setOnRefreshListener(this);



        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new adapterList(modelListAdmins, list_admin.this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.OnGestureListener() {


                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent motionEvent) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

                @Override
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent motionEvent) {

                }

                @Override
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                    return false;
                }


            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)){
                    int position = rv.getChildAdapterPosition(child);
                    Intent  i = new Intent(getApplicationContext(), DetailNews.class);
                    i.putExtra(TAG_ID, modelListAdmins.get(position).getId());
                    startActivity(i);
                    //Toast.makeText(getApplicationContext(), "id "+modelListAdmins.get(position).getId()+" selected", Toast.LENGTH_SHORT).show();
                }
                return  false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        button =(Button)findViewById(R.id.btnTambah);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_admin.this, TambahBerita.class);
                startActivity(intent);
            }
        });

    }

    public void getData(){
        swipeRefreshLayout.setRefreshing(true);
//        final ProgressDialog loading = ProgressDialog.show(this, "Loading...", "Load data",false, false);
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST ,DATA_URL, null,
                new Response.Listener<JSONArray>(){
            public void onResponse(JSONArray response){
                //loading.dismiss();
                Log.d("JSONResponse", response.toString());
                for (int i = 0; i<response.length(); i++){
                    try {
                        JSONObject data = response.getJSONObject(i);
                        ModelListAdmin model = new ModelListAdmin();
                        model.setId(data.getString("id"));
                        model.setJudul(data.getString("judul"));
                        model.setTanggal(data.getString("tanggal"))   ;
                        model.setGambar(data.getString("gambar"));

                        modelListAdmins.add(model);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();

                }
                swipeRefreshLayout.setRefreshing(false);
            }
        },
                new Response.ErrorListener(){
                    public void onErrorResponse(VolleyError error){

                        Log.d("ERRORRequest", "ERROR :"+error.getMessage());

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        getData();
    }
}
