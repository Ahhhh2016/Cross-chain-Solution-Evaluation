package com.example.governapispringboot.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Certificate implements Serializable {
    String fileID;
    String userID;
    String synopsis;
    String time;
    String applicantID;
    String tag="true";
    ArrayList<String> historyFileID=null;

    public Certificate() {
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "fileID='" + fileID + '\'' +
                ", userID='" + userID + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", time='" + time + '\'' +
                ", applicantID='" + applicantID + '\'' +
                ", tag='" + tag + '\'' +
                ", historyFileID=" + historyFileID +
                '}';
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public void setHistoryFileID(ArrayList<String> historyFileID) {
        this.historyFileID = historyFileID;
    }

    public String getUserID() {
        return userID;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getTime() {
        return time;
    }

    public String getApplicantID() {
        return applicantID;
    }

    public String getTag() {
        return tag;
    }

    public ArrayList<String> getHistoryFileID() {
        return historyFileID;
    }

    public Certificate(String fileID, String userID, String applicantID, String synopsis, String time){
        this.fileID=fileID;
        this.userID=userID;
        this.applicantID=applicantID;
        this.synopsis=synopsis;
        this.time=time;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
