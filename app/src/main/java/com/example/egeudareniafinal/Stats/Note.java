package com.example.egeudareniafinal.Stats;

public class Note {
    private final String title, account, addept;

    public Note(String title, String account, String addept) {
        this.title = title;
        this.account = account;
        this.addept = addept;
    }

    public String getTitle() {
        return title;
    }

    public String getAccount() {
        return account;
    }

    public String getAddept() {
        return addept;
    }
}
