package com.example.governapispringboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.governapispringboot.utils.ListTypHandler;

import java.util.ArrayList;
import java.util.List;

@TableName(value = "userrecordstable",autoResultMap = true)
public class FilesListofUser {
    @TableField(value = "userID")
    String userID;
    @TableField(value = "fileList",typeHandler = ListTypHandler.class)
    List<String> fileList=new ArrayList<String>();
//    @TableField(value = "fileList")
//    String fileList;
    public String getUserID() {
        return userID;
    }

    public FilesListofUser() {
    }

    public FilesListofUser(String userID, List<String> fileList) {
        this.userID = userID;
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return "FilesListofUser{" +
                "userID='" + userID + '\'' +
                ", fileList=" + fileList +
                '}';
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
        System.out.println(fileList);
    }

    public List<String> getFileList() {
        return fileList;
    }
}
