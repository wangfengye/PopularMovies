package com.example.feng.popularmovies;

/**
 * Created by feng on 2017/9/18.\
 * 保存 json数据解析的字段名
 */

public class HttpParams {
    public static final class Params{
        //list
        private static final String KEY_ID="id";
        private static final String KEY_RESULTS="results";
        private static final String KEY_TITLE="title";
        private static final String KEY_POSTER="poster_path";
        private static final String KEY_DESC="overview";
        private static final String KEY_POPULAR="popularity";
        private static final String KEY_VOTE="vote_average";
        private static final String KEY_RELEASE_DATE="release_date";
        private static final String KEY_COLLECTION="is_collection";
        //detail
        private static final String KEY_RUNTIME="runtime";
        private static final String KEY_TRAILER="key";

        public static String getKeyRuntime() {
            return KEY_RUNTIME;
        }

        public static String getKeyTrailer() {
            return KEY_TRAILER;
        }

        public static String getKeyId() {
            return KEY_ID;
        }

        public static String getKeyResults() {
            return KEY_RESULTS;
        }

        public static String getKeyTitle() {
            return KEY_TITLE;
        }

        public static String getKeyPoster() {
            return KEY_POSTER;
        }

        public static String getKeyDesc() {
            return KEY_DESC;
        }

        public static String getKeyPopular() {
            return KEY_POPULAR;
        }

        public static String getKeyVote() {
            return KEY_VOTE;
        }

        public static String getKeyReleaseDate() {
            return KEY_RELEASE_DATE;
        }

        public static String getKeyCollection() {
            return KEY_COLLECTION;
        }
    }
}
