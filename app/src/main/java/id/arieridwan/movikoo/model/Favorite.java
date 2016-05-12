package id.arieridwan.movikoo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;



public class Favorite extends SugarRecord implements Parcelable
{
    private int ids;
    private String title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private double vote_average;
    private double popularity;

    protected Favorite(Parcel in) {
        ids = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        popularity = in.readDouble();
    }
    public Favorite() {

    }

    public Favorite(int ids, String title, String poster_path, String overview, String backdrop_path, String release_date, double vote_average,double popularity){
        this.ids = ids;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.popularity = popularity;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ids);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(backdrop_path);
        dest.writeString(release_date);
        dest.writeDouble(vote_average);
        dest.writeDouble(popularity);
    }


    public int getIds() {
        return ids;
    }

    public void setIds(int id) {
        this.ids = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
