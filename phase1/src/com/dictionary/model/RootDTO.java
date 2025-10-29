package com.dictionary.model;

public class RootDTO {
    private int id;
    private String rootLetters;

    public RootDTO() {}

    public RootDTO(int id, String rootLetters) {
        this.id = id;
        this.rootLetters = rootLetters;
    }

    public RootDTO(String rootLetters) {
        this.rootLetters = rootLetters;
    }

    public int getId() {
        return id;
    }

    public String getRootLetters() {
        return rootLetters;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRootLetters(String rootLetters) {
        this.rootLetters = rootLetters;
    }

    @Override
    public String toString() {
        return "RootDTO [id=" + id + ", rootLetters=" + rootLetters + "]";
    }
}
