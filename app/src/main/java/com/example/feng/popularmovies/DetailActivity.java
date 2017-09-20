package com.example.feng.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {
    public static final String MOVIE = "movie";
    public static final int DETAIL_LOADER = 2;
    public static final String ID = "id";
    public static final int LOADER_UPDATE = 3;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findView();
        initData();
    }

    private void initData() {
        Bundle bundle = new Bundle();
        bundle.putLong(ID, movie.getId());
        getSupportLoaderManager().restartLoader(DETAIL_LOADER, bundle, this);
    }

    private void findView() {
        ImageView img = (ImageView) findViewById(R.id.poster);
        TextView title = (TextView) findViewById(R.id.title);
        TextView vote = (TextView) findViewById(R.id.vote);
        TextView releaseDate = (TextView) findViewById(R.id.release);
        TextView desc = (TextView) findViewById(R.id.desc);

        Bundle bundle = getIntent().getExtras();
        movie = (Movie) bundle.getSerializable(MOVIE);
        Glide.with(this)
                .load(movie.getPoster())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(400, 800)
                .into(img);

        title.setText(movie.getTitle());
        vote.setText(String.valueOf(movie.getVoteAverage()));
        desc.setText(movie.getDesc());
        releaseDate.setText(movie.getReleaseDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu_detail, menu);
        setIcon(menu.findItem(R.id.action_collection));
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        movie.setCollection(!movie.getCollection());
        update();
        setIcon(item);
        return super.onOptionsItemSelected(item);
    }

    private void setIcon(MenuItem item) {
        if (movie.getCollection()) {
            item.setIcon(android.R.drawable.star_big_on);
        } else {
            item.setIcon(android.R.drawable.star_big_off);
        }
    }

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new DetailLoader(this, args.getLong(ID));
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        movie.setRuntime(data.getRuntime());
        movie.setTrailer(data.getTrailer());
        TextView runtimeTv = (TextView) findViewById(R.id.runtime);
        TextView trailerTv = (TextView) findViewById(R.id.trailer);
        runtimeTv.setText(movie.getRuntime()==0?getString(R.string.unknow):(movie.getRuntime() + getString(R.string.minutes)));
        if (movie.getTrailer() != null && movie.getTrailer().length() > 0) {
            trailerTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri content_url = Uri.parse(movie.getTrailer());
                    intent.setData(content_url);
                    startActivity(intent);
                }
            });
        } else {
            trailerTv.setVisibility(View.GONE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

    /**
     * 更新收藏状态
     */
    private void update(){
        getSupportLoaderManager().restartLoader(LOADER_UPDATE, null, new LoaderManager.LoaderCallbacks<CacheData>() {
            @Override
            public Loader<CacheData> onCreateLoader(int id, Bundle args) {
                CacheLoader.Update update= new CacheLoader.Update() {
                    @Override
                    public boolean onUpdate() {
                        ContentValues values = new ContentValues();
                        values.put(HttpParams.Params.getKeyCollection(), movie.getCollection());
                        getContentResolver().update(MovieProvider.URI, values, HttpParams.Params.getKeyId() + " = ?", new String[]{String.valueOf(movie.getId())});
                        return true;
                    }
                };
                return new CacheLoader(DetailActivity.this, update);
            }

            @Override
            public void onLoadFinished(Loader<CacheData> loader, CacheData data) {

            }

            @Override
            public void onLoaderReset(Loader<CacheData> loader) {

            }
        });
    }
}
