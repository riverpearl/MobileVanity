package com.vanity.mobilevanity.data;

/**
 * Created by Tacademy on 2016-08-24.
 */
public class User {
    private long id;
    private String userProfile;
    private String userNickName;
    private int skinType;
    private int skinTone;
    private int eyeNum;
    private int lipNum;
    private int skinNum;
    private int faceNum;
    private int cleansingNum;
    private int toolNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getSkinType() {
        return skinType;
    }

    public void setSkinType(int skinType) {
        this.skinType = skinType;
    }

    public int getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(int skinTone) {
        this.skinTone = skinTone;
    }

    public int getEyeNum() {
        return eyeNum;
    }

    public void setEyeNum(int eyeNum) {
        this.eyeNum = eyeNum;
    }

    public int getLipNum() {
        return lipNum;
    }

    public void setLipNum(int lipNum) {
        this.lipNum = lipNum;
    }

    public int getSkinNum() {
        return skinNum;
    }

    public void setSkinNum(int skinNum) {
        this.skinNum = skinNum;
    }

    public int getFaceNum() {
        return faceNum;
    }

    public void setFaceNum(int baseNum) {
        this.faceNum = baseNum;
    }

    public int getCleansingNum() {
        return cleansingNum;
    }

    public void setCleansingNum(int cleansingNum) {
        this.cleansingNum = cleansingNum;
    }

    public int getToolNum() {
        return toolNum;
    }

    public void setToolNum(int toolNum) {
        this.toolNum = toolNum;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    private int gender;
}
