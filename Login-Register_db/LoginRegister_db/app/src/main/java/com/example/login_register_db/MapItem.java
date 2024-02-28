package com.example.login_register_db;

public class MapItem {

    private String title;
    private int iconID;

    private String points;
    private String information;
    private String finishingtime;
    private String timeback;
    private String difficulty;
    private String equipment;
    private double latitude;
    private double longitude;
    private int pinResourceID;
    private int markerIcon; // new field for the marker icon resource ID
    private int dialogIconID;

    // Constructor without the markerIcon field
    public MapItem(String title, String information, int iconID, String points, String finishingtime, String timeback, String difficulty, String equipment, double latitude, double longitude, int pinResourceID) {
        this.title = title;
        this.information = information;
        this.iconID = iconID;
        this.points = points;
        this.finishingtime = finishingtime;
        this.timeback = timeback;
        this.difficulty = difficulty;
        this.equipment = equipment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pinResourceID = pinResourceID;
    }

    // Constructor with the markerIcon field


    // Getters and setters for other properties

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getFinishingtime() {
        return finishingtime;
    }

    public void setFinishingtime(String finishingtime) {
        this.finishingtime = finishingtime;
    }

    public String getTimeback() {
        return timeback;
    }

    public void setTimeback(String timeback) {
        this.timeback = timeback;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getPinResourceID() {
        return pinResourceID;
    }

    public void setPinResourceID(int pinResourceID) {
        this.pinResourceID = pinResourceID;
    }

    // Getter for the marker icon resource ID
    public int getMarkerIcon() {
        return markerIcon;
    }

    // Setter for the marker icon resource ID
    public void setMarkerIcon(int markerIcon) {
        this.markerIcon = markerIcon;
    }

    public int getDialogIconID() {
        return dialogIconID;
    }

    public void setDialogIconID(int dialogIconID) {
        this.dialogIconID = dialogIconID;
    }
}
