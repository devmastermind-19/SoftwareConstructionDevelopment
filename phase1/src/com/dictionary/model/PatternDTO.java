package com.dictionary.model;

public class PatternDTO {
    private int id;
    private String patternForm;

    public PatternDTO() {}

    public PatternDTO(int id, String patternForm) {
        this.id = id;
        this.patternForm = patternForm;
    }

    public PatternDTO(String patternForm) {
        this.patternForm = patternForm;
    }

    public int getId() {
        return id;
    }

    public String getPatternForm() {
        return patternForm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatternForm(String patternForm) {
        this.patternForm = patternForm;
    }

    @Override
    public String toString() {
        return "PatternDTO [id=" + id + ", patternForm=" + patternForm + "]";
    }
}
