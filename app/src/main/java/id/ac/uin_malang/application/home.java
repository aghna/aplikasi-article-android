package id.ac.uin_malang.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class home extends Fragment {

    //    public static final String DATA_URL = "http://mobiles.dx.am/lihatdata.php";
    public static final String DATA_URL = "http://alvin8mmudhoffar.000webhostapp.com/lihatdata.php";
    public  static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_IMAGE = "gambar";
    public static final String TAG_TANGGAL = "tanggal";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    RequestQueue requestQueue;
    List<ModelListAdmin> modelListAdmins;
    Context thisContext;

    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState){

        View view = inflater.inflate(R.layout.fragment_home, container, false);




        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerTempHome);
        requestQueue = Volley.newRequestQueue(getContext());
        modelListAdmins=new ArrayList<>();

        getData();

        thisContext = container.getContext();
        manager = new LinearLayoutManager(this.getActivity());
        Log.d("debugMode", "The application stopped after this");
        recyclerView.setLayoutManager(manager);
        adapter = new adapterListHome(modelListAdmins, thisContext);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {


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
                    Intent  i = new Intent(getContext(), detail_user.class);
                    i.putExtra(TAG_ID, modelListAdmins.get(position).getId());
                    startActivity(i);
                   // Toast.makeText(getContext(), "id "+modelListAdmins.get(position).getId()+" selected", Toast.LENGTH_SHORT).show();
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
        return view;
    }

    public void getData(){
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


}