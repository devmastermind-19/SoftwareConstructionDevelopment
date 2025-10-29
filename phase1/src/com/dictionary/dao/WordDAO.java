package com.dictionary.dao;

import com.dictionary.database.DatabaseConnection;
import com.dictionary.model.WordDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {
    public void addWord(WordDTO word) {
        String query = "INSERT INTO words (word, rootId, patternId, meaning, example, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, word.getWord());
            pst.setInt(2, word.getRootId());
            pst.setInt(3, word.getPatternId());
            pst.setString(4, word.getMeaning());
            pst.setString(5, word.getExample());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<WordDTO> getAllWords() {
        List<WordDTO> words = new ArrayList<>();
        String query = "SELECT * FROM words ORDER BY id DESC";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                words.add(new WordDTO(
                    rs.getInt("id"),
                    rs.getString("word"),
                    rs.getInt("rootId"),
                    rs.getInt("patternId"),
                    rs.getString("meaning"),
                    rs.getString("example"),
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("updatedAt").toLocalDateTime()
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }

    public WordDTO getWordById(int id) {
        String query = "SELECT * FROM words WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new WordDTO(
                    rs.getInt("id"),
                    rs.getString("word"),
                    rs.getInt("rootId"),
                    rs.getInt("patternId"),
                    rs.getString("meaning"),
                    rs.getString("example"),
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateWord(WordDTO word) {
        String query = "UPDATE words SET word = ?, rootId = ?, patternId = ?, meaning = ?, example = ?, updatedAt = NOW() WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, word.getWord());
            pst.setInt(2, word.getRootId());
            pst.setInt(3, word.getPatternId());
            pst.setString(4, word.getMeaning());
            pst.setString(5, word.getExample());
            pst.setInt(6, word.getId());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteWord(int id) {
        String query = "DELETE FROM words WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<WordDTO> getWordsByRootId(int rootId) {
        List<WordDTO> words = new ArrayList<>();
        String query = "SELECT * FROM words WHERE rootId = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, rootId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                words.add(new WordDTO(
                    rs.getInt("id"),
                    rs.getString("word"),
                    rs.getInt("rootId"),
                    rs.getInt("patternId"),
                    rs.getString("meaning"),
                    rs.getString("example"),
                    rs.getTimestamp("createdAt").toLocalDateTime(),
                    rs.getTimestamp("updatedAt").toLocalDateTime()
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }
}
