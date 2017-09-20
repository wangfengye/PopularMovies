package com.example.feng.popularmovies;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    public static final String KEY_URL = "url";
    private static final int LOADER_MAIN_ID = 1;
    private static final int LOADER_INSERT = 2;
    private static final int LOADER_QUERY = 3;
    private ThumbnailAdapter adapter;
    private final Handler mHanler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        register();
        getCache();

    }

    /**
     * 注册监听缓存变化
     */
    private void register() {
        getContentResolver().registerContentObserver(MovieProvider.URI, true,
                new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange) {
                        getCache();
                        super.onChange(selfChange);
                    }
                });
    }

    private void setCache(final List<Movie> movies) {
        Bundle bundle = new Bundle();
        getSupportLoaderManager().restartLoader(LOADER_INSERT, null,
                new LoaderManager.LoaderCallbacks<CacheData>() {
                    @Override
                    public Loader<CacheData> onCreateLoader(int id, Bundle args) {
                        CacheLoader.SetCache setCache = new CacheLoader.SetCache() {
                            @Override
                            public boolean onSetCache() {
                                Uri uri = MovieProvider.URI;
                                for (Movie movie : movies) {
                                    ContentValues values = new ContentValues();
                                    values.put(HttpParams.Params.getKeyId(), movie.getId());
                                    values.put(HttpParams.Params.getKeyPoster(), movie.getPoster());
                                    values.put(HttpParams.Params.getKeyTitle(), movie.getTitle());
                                    values.put(HttpParams.Params.getKeyDesc(), movie.getDesc());
                                    values.put(HttpParams.Params.getKeyPopular(), movie.getPopularity());
                                    values.put(HttpParams.Params.getKeyVote(), movie.getVoteAverage());
                                    values.put(HttpParams.Params.getKeyReleaseDate(), movie.getReleaseDate());
                                    values.put(HttpParams.Params.getKeyCollection(), movie.getCollection());
                                    getContentResolver().insert(uri, values);
                                }
                                return true;
                            }
                        };
                        return new CacheLoader(MainActivity.this, setCache);
                    }

                    @Override
                    public void onLoadFinished(Loader<CacheData> loader, CacheData data) {

                    }

                    @Override
                    public void onLoaderReset(Loader<CacheData> loader) {

                    }
                });
    }

    private void reSetData() {
        getSupportLoaderManager().restartLoader(LOADER_MAIN_ID, null, this);
    }

    private void initView() {
        AppCompatSpinner collectSpinner = (AppCompatSpinner) findViewById(R.id.conllection);
        AppCompatSpinner sortSpinner = (AppCompatSpinner) findViewById(R.id.sort);
        RecyclerView listView = (RecyclerView) findViewById(R.id.list);

        collectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> view, View view1, int i, long l) {
                if (i == 0) {
                    adapter.showCollection(false);
                } else {
                    adapter.showCollection(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> view) {

            }
        });
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> view, View view1, int i, long l) {
                adapter.setSort(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> view) {

            }
        });

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(manager);
        adapter = new ThumbnailAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MainLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null || data.size() == 0) {
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
            return;
        }
        setCache(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                reSetData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getCache() {
        getSupportLoaderManager().restartLoader(LOADER_QUERY, null,
                new LoaderManager.LoaderCallbacks<CacheData>() {
                    @Override
                    public Loader<CacheData> onCreateLoader(int id, Bundle args) {
                        CacheLoader.GetCache getCache = new CacheLoader.GetCache() {
                            @Override
                            public List<Movie> onGetCache() {
                                ArrayList<Movie> data = new ArrayList<>();
                                Cursor cursor = getContentResolver().query(MovieProvider.URI, new String[]{HttpParams.Params.getKeyId(),
                                        HttpParams.Params.getKeyPoster(), HttpParams.Params.getKeyTitle(), HttpParams.Params.getKeyDesc(),
                                        HttpParams.Params.getKeyPopular(), HttpParams.Params.getKeyVote(), HttpParams.Params.getKeyReleaseDate(),
                                        HttpParams.Params.getKeyCollection()}, null, null, null);
                                while (cursor.moveToNext()) {
                                    Movie movie = new Movie();
                                    movie.setId(cursor.getLong(cursor.getColumnIndex(HttpParams.Params.getKeyId())));
                                    movie.setPoster(cursor.getString(cursor.getColumnIndex(HttpParams.Params.getKeyPoster())));
                                    movie.setTitle(cursor.getString(cursor.getColumnIndex(HttpParams.Params.getKeyTitle())));
                                    movie.setDesc(cursor.getString(cursor.getColumnIndex(HttpParams.Params.getKeyDesc())));
                                    movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(HttpParams.Params.getKeyPopular())));
                                    movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(HttpParams.Params.getKeyVote())));
                                    movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(HttpParams.Params.getKeyReleaseDate())));
                                    if (cursor.getInt(cursor.getColumnIndex(HttpParams.Params.getKeyCollection())) == 1) {
                                        movie.setCollection(true);
                                    }
                                    data.add(movie);
                                }
                                return data;
                            }
                        };
                        return new CacheLoader(MainActivity.this, getCache);
                    }

                    @Override
                    public void onLoadFinished(Loader<CacheData> loader, CacheData data) {
                        adapter.reSetData(data.getMovies());
                    }

                    @Override
                    public void onLoaderReset(Loader<CacheData> loader) {

                    }
                });

    }
}
