package com.sinaapp.yinizhizhublog.nutshell;

public class MovieInfo {
    private String movieName;  //the name of movie
    private String movieScore;         //the score of the movie
    private String movieContent;    //the brief details of the movie
    private String movieDate; //the date of the onscreen.

    private String moviePicture;     //the picture of the movie

    public MovieInfo(String temp[]){
        this.movieName = temp[0];
        this.movieDate = temp[1];
        this.movieScore = temp[2];
        this.movieContent = temp[4];
        this.moviePicture = temp[3];
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(String movieScore) {
        this.movieScore = movieScore;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getMoviePicture() {
        return moviePicture;
    }

    public void setMoviePicture(String moviePicture) {
        this.moviePicture = moviePicture;
    }

    public String getMovieContent() {
        return movieContent;
    }

    public void setMovieContent(String movieContent) {
        this.movieContent = movieContent;
    }
}
