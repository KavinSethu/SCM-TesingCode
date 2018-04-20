package com.pnf.elar.app.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elar.util.SmartClassUtil;
import com.pnf.elar.app.Bo.TodaysNoteBO;
import com.pnf.elar.app.Bo.schedule.ChildrensBo;
import com.pnf.elar.app.Bo.schedule.ScheduleMonthList;
import com.pnf.elar.app.Drawer;
import com.pnf.elar.app.MyCustomProgressDialog;
import com.pnf.elar.app.R;
import com.pnf.elar.app.UserSessionManager;
import com.pnf.elar.app.activity.schedule.AddActivityActivity;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.API;
import com.pnf.elar.app.activity.schedule.apiClient.retrofit.ToStringConverterFactory;
import com.pnf.elar.app.adapter.schedule.ChildrensAdapter;
import com.pnf.elar.app.util.Const;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pmohan on 05-07-2017.
 */

public class TodaysNoteFragment extends Fragment {

    Context context;
    View v;
    RecyclerView rv;
    String lang,Base_url,securityKey = "H67jdS7wwfh",user_id = "", name;
    UserSessionManager session;
    Animation animFadein;
    List<TodaysNoteBO.NoteListBean> todaysNoteBOList = new ArrayList<>();
    TodaysNoteAdapter todaysNoteAdapter;
    MyCustomProgressDialog dialogLoading;
    TextView empty_notes,username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();

        dialogLoading = new MyCustomProgressDialog(context);
        dialogLoading.setIndeterminate(true);
        dialogLoading.setCancelable(false);

        session = new UserSessionManager(context);

        v = inflater.inflate(R.layout.todays_note, container, false);

        rv = (RecyclerView) v.findViewById(R.id.todaysNoteRv);
        empty_notes = (TextView) v.findViewById(R.id.empty_notes);
        username = (TextView) v.findViewById(R.id.username);

        HashMap<String, String> user = session.getUserDetails();
        lang = user.get(UserSessionManager.TAG_language);
        Base_url = user.get(UserSessionManager.TAG_Base_url);
        user_id = user.get(UserSessionManager.TAG_child_id);
        name = user.get(UserSessionManager.TAG_cld_nm);

        username.setText(name);
        animFadein = AnimationUtils.loadAnimation(context,
                R.anim.slide_up);

        if (lang.equalsIgnoreCase("sw")) {
            ((Drawer) context).setActionBarTitle("Dagens Nothistorik");
        } else {
            ((Drawer) context).setActionBarTitle("Todays Note History");
        }
        ((Drawer) context).showbackbutton();
        ((Drawer) context).setBackForChildEduedu();
        ((Drawer) context).Hideserch();
        ((Drawer) context).HideRefresh();

        todaysNoteAdapter=new TodaysNoteAdapter(context,todaysNoteBOList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(todaysNoteAdapter);

        GetTodaysNoteService();

        return v;
    }


    public void GetTodaysNoteService(){
        try {
            dialogLoading.show();
            JSONObject reqJsonObj = new JSONObject();
            reqJsonObj.put(Const.Params.SecurityKey, securityKey);
            reqJsonObj.put(Const.Params.StudentId, user_id);
            reqJsonObj.put(Const.Params.Language, lang);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), reqJsonObj.toString());
            Call<TodaysNoteBO> response = retrofit.create(API.class).getTodaysNote(body);
            response.enqueue(new Callback<TodaysNoteBO>() {
                @Override
                public void onResponse(Call<TodaysNoteBO> call, Response<TodaysNoteBO> response) {
                    dialogLoading.dismiss();
                    Log.d("LMS","TodaysNoteFragment ---> "+response.body().toString());

                    if (response.body() != null) {
                        TodaysNoteBO todaysNoteBO = response.body();
                        if (todaysNoteBO.getStatus().equalsIgnoreCase(Const.Params.TRUE)) {
                            if(!todaysNoteBO.getNote_list().isEmpty()&&todaysNoteBO.getNote_list()!=null) {
                                for (int i = 0; i < todaysNoteBO.getNote_list().size(); i++) {
                                    TodaysNoteBO.NoteListBean noteListBean = todaysNoteBO.getNote_list().get(i);
                                    todaysNoteBOList.add(noteListBean);
                                }
                            }
                            todaysNoteAdapter.notifyDataSetChanged();
                        } else {
                            SmartClassUtil.showToast(context, "Service Failed");
                        }
                    }
                    if(response.body().getNote_list().isEmpty()){
                        empty_notes.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);
                        if(lang.equalsIgnoreCase("sw")){
                            empty_notes.setText("Dagens nothistorik är tomt");
                        }else{
                            empty_notes.setText("Today's Note History Is Empty");
                        }
                    }
                }

                @Override
                public void onFailure(Call<TodaysNoteBO> call, Throwable t) {
                    dialogLoading.dismiss();
                    t.printStackTrace();

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class TodaysNoteAdapter extends RecyclerView.Adapter<TodaysNoteAdapter.ActivityViewHolder> {

    private LayoutInflater layoutInflater;
    List<TodaysNoteBO.NoteListBean> list_todaysNote = new ArrayList<>();
    Context ctx;


    TodaysNoteAdapter(Context context, List<TodaysNoteBO.NoteListBean> todaysNoteBOList) {
        this.ctx = context;
        this.list_todaysNote = todaysNoteBOList;
        this.layoutInflater=LayoutInflater.from(ctx);
    }

    @Override
    public TodaysNoteAdapter.ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.todays_note_item, parent, false);
        return new TodaysNoteAdapter.ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {

        holder.subject.setText(list_todaysNote.get(position).getTodayNote().getDescription());
        holder.date.setText(list_todaysNote.get(position).getTodayNote().getNote_date());

    }


    @Override
    public int getItemCount() {
        return list_todaysNote.size();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout ll_td;
        TextView subject;
        TextView date;

        ActivityViewHolder(View itemView) {
            super(itemView);
            ll_td = (RelativeLayout) itemView.findViewById(R.id.ll_td);
            subject = (TextView) itemView.findViewById(R.id.subject);
            date = (TextView) itemView.findViewById(R.id.date);
        }


    }
}
