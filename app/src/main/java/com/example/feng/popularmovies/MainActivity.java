package com.example.feng.popularmovies;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    public static final String KEY_URL = "url";
    private static final int LOADER_MAIN_ID = 1;
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String URL_POPULAR = "popular";
    private static final String URL_TOP_RATED = "top_rated";
    private ThumbnailAdapter adapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        reSetData(URL_POPULAR);
    }

    private void reSetData(String s) {
        String url = BASE_URL + s;
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        getSupportLoaderManager().restartLoader(LOADER_MAIN_ID, bundle, this);
    }

    private void initView() {
        emptyView = (TextView) findViewById(R.id.empty_view);
        emptyView.setVisibility(View.GONE);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        reSetData(URL_POPULAR);
                        break;
                    case 1:
                        reSetData(URL_TOP_RATED);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(manager);
        adapter = new ThumbnailAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String url = args.getString(KEY_URL);
        return new MainLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null || data.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            return;
        }
        emptyView.setVisibility(View.GONE);
        adapter.reSettData(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

}
