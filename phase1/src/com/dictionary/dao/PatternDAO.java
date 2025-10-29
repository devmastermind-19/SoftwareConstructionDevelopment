package com.dictionary.dao;

import com.dictionary.database.DatabaseConnection;
import com.dictionary.model.PatternDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatternDAO {
    public void addPattern(PatternDTO pattern) {
        String query = "INSERT INTO patterns (patternForm) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, pattern.getPatternForm());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<PatternDTO> getAllPatterns() {
        List<PatternDTO> patterns = new ArrayList<>();
        String query = "SELECT * FROM patterns";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                patterns.add(new PatternDTO(rs.getInt("id"), rs.getString("patternForm")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return patterns;
    }

    public PatternDTO getPatternById(int id) {
        String query = "SELECT * FROM patterns WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new PatternDTO(rs.getInt("id"), rs.getString("patternForm"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePattern(PatternDTO pattern) {
        String query = "UPDATE patterns SET patternForm = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, pattern.getPatternForm());
            pst.setInt(2, pattern.getId());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deletePattern(int id) {
        String query = "DELETE FROM patterns WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
