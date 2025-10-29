package com.dictionary.dao;

import com.dictionary.database.DatabaseConnection;
import com.dictionary.model.RootDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RootDAO {
    public void addRoot(RootDTO root) {
        String query = "INSERT INTO roots (rootLetters) VALUES (?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, root.getRootLetters());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<RootDTO> getAllRoots() {
        List<RootDTO> roots = new ArrayList<>();
        String query = "SELECT * FROM roots";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                roots.add(new RootDTO(rs.getInt("id"), rs.getString("rootLetters")));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return roots;
    }

    public void updateRoot(RootDTO root) {
        String query = "UPDATE roots SET rootLetters = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, root.getRootLetters());
            pst.setInt(2, root.getId());
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoot(int id) {
        String query = "DELETE FROM roots WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
