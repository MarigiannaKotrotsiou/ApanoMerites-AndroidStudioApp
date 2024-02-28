package com.example.login_register_db;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> totalPoints = new MutableLiveData<>();

    public MutableLiveData<Integer> getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int points) {
        totalPoints.setValue(points);
    }
}
