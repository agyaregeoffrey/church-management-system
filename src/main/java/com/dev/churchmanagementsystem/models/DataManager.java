package com.dev.churchmanagementsystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import static com.dev.churchmanagementsystem.utils.Helpers.dateUtil;

public class DataManager {

    public ArrayList<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();
        members.add(new Member("Mr. Akwasi Osei", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Edward Osei Owusu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Adonu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mrs. Osei Owusu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Akwasi Osei", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Edward Osei Owusu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Akwasi Osei", "0202141314", "21/01/1990", null));
        members.add(new Member("Mrs. Osei Owusu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Akwasi Osei", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Edward Osei Owusu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Akwasi Osei", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Edward Osei Owusu", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Akwasi Osei", "0202141314", "21/01/1990", null));
        members.add(new Member("Mr. Edward Osei Owusu", "0202141314", "21/01/1990", null));
        return members;
    }

//    public final ObservableList<Member> members = FXCollections.observableArrayList(
//            new Member("Mr. Akwasi Osei", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Edward Osei Owusu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Adonu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mrs. Osei Owusu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Akwasi Osei", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mrs. Osei Owusu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Akwasi Osei", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mrs. Osei Owusu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Akwasi Osei", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mrs. Osei Owusu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Akwasi Osei", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mrs. Osei Owusu", "0202141314", dateUtil("21/01/1990"), null),
//            new Member("Mr. Akwasi Osei", "0202141314", dateUtil("21/01/1990"), null)
//    );



}
