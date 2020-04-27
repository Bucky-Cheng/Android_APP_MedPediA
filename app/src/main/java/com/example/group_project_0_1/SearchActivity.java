package com.example.group_project_0_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.group_project_0_1.Adapter.SeaAdapter;
import com.example.group_project_0_1.Adapter.UserAdapter;
import com.example.group_project_0_1.DAO.SQLDao;
import com.example.group_project_0_1.Model.chatUser;
import com.example.group_project_0_1.Model.seaItem;
import com.example.group_project_0_1.Service.ConnectServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class SearchActivity extends AppCompatActivity {

    EditText editText;
    private RecyclerView recyclerView;
    private SeaAdapter seaAdapter;
    private List<seaItem> mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText=findViewById(R.id.search);

        editText.requestFocus();

        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        mItems=new ArrayList<>();
        //readUsers();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    System.out.println("CLICK");
                    searchItem(s.toString());
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void searchItem(String toLowerCase) throws BrokenBarrierException, InterruptedException, JSONException {

        String SQL=new SQLDao().findItems();
        ArrayList<JSONObject> jsonArray=new ConnectServer().connection(SQL);
        mItems.clear();
        if(toLowerCase.toLowerCase().equals("")&&toLowerCase.length()<=0){
                System.out.println("NULL");
        }else {
            for (int i = 0; i < jsonArray.size(); i++) {

                JSONObject jsonObject = jsonArray.get(i);
                String name_zh = jsonObject.getString("name_zh");
                String name_en = jsonObject.getString("name_en");
                String table = jsonObject.getString("table");
                String t_id = jsonObject.getString("t_id");
                if (name_zh.contains(toLowerCase.toLowerCase())) {
                    mItems.add(new seaItem(0, name_zh, "", table, t_id));
                } else if (name_en.toLowerCase().contains(toLowerCase.toLowerCase())) {
                    mItems.add(new seaItem(0, "", name_en, table, t_id));

                }

            }
        }
        seaAdapter=new SeaAdapter(SearchActivity.this,mItems);
        recyclerView.setAdapter(seaAdapter);
    }
}
