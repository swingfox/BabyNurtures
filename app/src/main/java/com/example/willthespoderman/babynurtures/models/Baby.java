package com.example.willthespoderman.babynurtures.models;

/**
 * Created by WILL THE SPODERMAN on 10/8/2015.
 */
public class Baby {


    private String title,thumbnailUrl, birthday, height, weight, nationality, city, country,gender;
    private String BabyId;



    public Baby() {
    }

    public Baby(String name, String thumbnailUrl, String birthday, String height, String weight,
                String nationality, String city, String country,String gender, String id) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.nationality = nationality;
        this.city = city;
        this.country = country;
        this.gender = gender;
        BabyId = id;

    }
    //Setters.......

    public void setBabyId(String id) {
        this.BabyId = id;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setBabyName(String name) {
        this.title = name;
    }

    public void setBabyBirthday(String birthday){
        this.birthday = birthday;
    }

    public void setBabyHeight(String height){
        this.height = height;
    }

    public void setBabyWeight(String weight){
        this.weight = weight;
    }

    public void setBabyNationality(String nationality){
        this.nationality = nationality;
    }

    public void setBabyCity(String city){
        this.city = city;
    }

    public void setBabyCountry(String country){
        this.country = country;
    }

    public void setBabyGender(String gender){
        this.gender = gender;
    }




    //Getters......
    public String getBabyId() {
        return BabyId;
    }

    public String getBabyName() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getBabyBirthday(){
        return birthday;
    }

    public String getBabyHeight(){
        return height;
    }

    public String getBabyWeight(){
        return weight;
    }

    public String getBabyNationality(){
        return nationality;
    }

    public String getBabyCity(){
        return city;
    }

    public String getBabyCountry(){
        return country;
    }

    public String getBabyGender(){
        return gender;
    }


}
