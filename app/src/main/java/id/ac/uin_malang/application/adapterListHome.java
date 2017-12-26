package id.ac.uin_malang.application;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by lenovo on 29/11/17.
 */

public class adapterListHome extends RecyclerView.Adapter<adapterListHome.HolderItem> {

    List<ModelListAdmin> modelListAdminList;
    Context context;

    public adapterListHome(List<ModelListAdmin> modelListAdmins, Context context){
        this.modelListAdminList = modelListAdmins;
        this.context = context;
    }

    @Override
    public HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        HolderItem holder = new HolderItem(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(HolderItem holder, int position) {
        ModelListAdmin mList = modelListAdminList.get(position);

        holder.tv_judul.setText(mList.getJudul());
        holder.tv_tanggal.setText(mList.getTanggal());

        //loading image

        Glide.with(context).load(mList.getGambar()).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnails);
    }

    @Override
    public int getItemCount() {
        return modelListAdminList.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        ImageView thumbnails;
        TextView tv_judul, tv_tanggal;
        RecyclerView recycleView;

        public HolderItem(View v){
            super(v);
            thumbnails = (ImageView) v.findViewById(R.id.imgHome);
            tv_judul = (TextView) v.findViewById(R.id.judulHome);
            tv_tanggal = (TextView) v.findViewById(R.id.tanggalHome);
            recycleView = (RecyclerView)v.findViewById(R.id.recyclerTempHome);

        }
    }

}
