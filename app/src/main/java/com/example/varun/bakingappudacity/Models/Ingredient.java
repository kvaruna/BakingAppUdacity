package com.example.varun.bakingappudacity.Models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@org.parceler.Parcel
public class Ingredient implements Serializable {

    @SerializedName("quantity")
    @Expose
    String quantity;
    @SerializedName("measure")
    @Expose
    String measure;
    @SerializedName("ingredient")
    @Expose
    String ingredient;


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


}
