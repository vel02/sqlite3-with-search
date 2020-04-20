package com.austria.developer.friends.model;

public class Friend {

    private String _id;
    private String mName;
    private int mAge;
    private String mGender;
    private String mPhone;
    private String mEmail;

    public Friend(String _id, String name, int age, String gender, String phone, String email) {
        this._id = _id;
        mName = name;
        mAge = age;
        mGender = gender;
        mPhone = phone;
        mEmail = email;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    public String getGender() {
        return mGender;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "mName='" + mName + '\'' +
                ", mAge='" + mAge + '\'' +
                ", mGender='" + mGender + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mEmail='" + mEmail + '\'' +
                '}';
    }
}
