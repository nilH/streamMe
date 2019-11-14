package com.example.fodmap.data;

public class Ingredient {
    String name;
    Safety safety;
    public enum Safety{
        good,
        ok,
        bad
    }
    public Ingredient(String text, Safety safety1){
        name=text;
        safety=safety1;
    }
    public void setName(String name1){
        name=name1;
    }
    public String getName(){
        return name;
    }
    public void setSafety(Safety safety1){
        safety=safety1;
    }
    public Safety getSafety(){
        return safety;
    }
}
