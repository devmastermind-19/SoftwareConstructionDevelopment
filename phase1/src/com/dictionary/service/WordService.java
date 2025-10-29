package com.dictionary.service;

import com.dictionary.dao.WordDAO;
import com.dictionary.model.WordDTO;
import java.util.List;

public class WordService {
    private WordDAO wordDAO = new WordDAO();

    public void addWord(WordDTO word) {
        wordDAO.addWord(word);
    }

    public List<WordDTO> getAllWords() {
        return wordDAO.getAllWords();
    }

    public WordDTO getWordById(int id) {
        return wordDAO.getWordById(id);
    }

    public void updateWord(WordDTO word) {
        wordDAO.updateWord(word);
    }

    public void deleteWord(int id) {
        wordDAO.deleteWord(id);
    }

    public List<WordDTO> getWordsByRootId(int rootId) {
        return wordDAO.getWordsByRootId(rootId);
    }
}
