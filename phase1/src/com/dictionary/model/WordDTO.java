package com.dictionary.model;

import java.time.LocalDateTime;

public class WordDTO {
    private int id;
    private String word;
    private int rootId;
    private int patternId;
    private String meaning;
    private String example;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WordDTO() {}

    public WordDTO(String word, int rootId, int patternId, String meaning, String example) {
        this.word = word;
        this.rootId = rootId;
        this.patternId = patternId;
        this.meaning = meaning;
        this.example = example;
    }

    public WordDTO(int id, String word, int rootId, int patternId, String meaning, String example, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.word = word;
        this.rootId = rootId;
        this.patternId = patternId;
        this.meaning = meaning;
        this.example = example;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public int getRootId() { return rootId; }
    public void setRootId(int rootId) { this.rootId = rootId; }

    public int getPatternId() { return patternId; }
    public void setPatternId(int patternId) { this.patternId = patternId; }

    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }

    public String getExample() { return example; }
    public void setExample(String example) { this.example = example; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "WordDTO [id=" + id + ", word=" + word + ", rootId=" + rootId + ", patternId=" + patternId + 
               ", meaning=" + meaning + ", example=" + example + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
