package com.sinaapp.yinizhizhublog.nutshell;

public class NewsInfo {
    private String org;      //the date of the news
    private String number;      //the times the users have seen
    private String title;   //the title of the news
    private String url;

//    public NewsInfo(String temp[]){
//        System.out.println("You are here!");
//        if (temp.length == 3){
//            org = "Others";
//            title = temp[0];
//            url = temp[1];
//            number = temp[2];
//            System.out.println("There are three numbers!");
//        }
//        else{
//            org = temp[0];
//            title = temp[1];
//            url = temp[2];
//            number = temp[3];
//            System.out.println("There are four numbers!");
//        }
//    }
    public NewsInfo(String title){
        org="HIT";
        number="100";
        this.title = title;
        url = "http://today.hit.edu.cn";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
