package com.example.appmoveisp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listRelogios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listRelogios = findViewById(R.id.listRelogios);
        ArrayList<ListItem> listR = new ArrayList<>();
        listR.add(new ListItem("Tommy Hilfiger", R.drawable.op1));
        listR.add(new ListItem("TAG Heuer", R.drawable.op2));
        listR.add(new ListItem("Calvin Klein", R.drawable.op3));

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.custom_list_item, listR);
        listRelogios.setAdapter(adapter);

        listRelogios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem selectedItem = listR.get(position);

                Intent intent = new Intent(MainActivity.this, ApresentationActivity.class);
                intent.putExtra("texto", selectedItem.getText());
                intent.putExtra("imagemRedId", selectedItem.getImageResId());
                if(selectedItem.getText().equals("Calvin Klein")){
                    intent.putExtra("valor", "1050.00");
                }else if(selectedItem.getText().equals("Tommy Hilfiger")){
                    intent.putExtra("valor", "1250.00");
                }else if(selectedItem.getText().equals("TAG Heuer")){
                    intent.putExtra("valor", "57030.00");
                }
                startActivity(intent);
            }
        });
    }
}

