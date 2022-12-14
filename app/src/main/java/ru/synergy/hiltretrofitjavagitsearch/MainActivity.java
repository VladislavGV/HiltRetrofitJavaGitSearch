package ru.synergy.hiltretrofitjavagitsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;
import ru.synergy.hiltretrofitjavagitsearch.ViewModel.MainActivityViewModel;
import ru.synergy.hiltretrofitjavagitsearch.adapter.RecyclerViewAdapter;
import ru.synergy.hiltretrofitjavagitsearch.models.RecyclerData;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initViewModel() {
        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getLiveData().observe(this, new Observer<List<RecyclerData>>() {
            @Override
            public void onChanged(List<RecyclerData> recyclerData) {
                if(recyclerData != null) {
                    recyclerViewAdapter.setListDataItems(recyclerData);
                    recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error in getting data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.makeAPICall();
    }

}