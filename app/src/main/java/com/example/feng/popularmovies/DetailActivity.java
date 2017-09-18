package com.example.feng.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static final String MOVIE ="movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findView();
    }

    private void findView() {
        ImageView img = (ImageView) findViewById(R.id.poster);
        TextView title = (TextView) findViewById(R.id.title);
        TextView vote =(TextView)findViewById(R.id.vote);
        TextView releaseDate = (TextView) findViewById(R.id.release);
        TextView desc = (TextView) findViewById(R.id.desc);

        Bundle bundle=getIntent().getExtras();
        Movie movie = (Movie) bundle.getSerializable(MOVIE);
        Glide.with(this)
                .load(movie.getPoster())
                .centerCrop()
                .into(img);

        title.setText(movie.getTitle());
        vote.setText(String.valueOf(movie.getVoteAverage()));
        desc.setText(movie.getDesc());
        releaseDate.setText(movie.getReleaseDate());
    }
}
