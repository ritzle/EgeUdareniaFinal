package com.example.egeudareniafinal.Stats.Simple;

public class NoteSimple {
    private final String title, description;

    public NoteSimple(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
