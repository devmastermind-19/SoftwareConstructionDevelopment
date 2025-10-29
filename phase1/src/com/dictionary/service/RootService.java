package com.dictionary.service;

import com.dictionary.dao.RootDAO;
import com.dictionary.model.RootDTO;
import java.util.List;

public class RootService{
    private RootDAO rootDAO = new RootDAO();

    public void addRoot(String rootLetters) {
        RootDTO root = new RootDTO(rootLetters);
        rootDAO.addRoot(root);
    }

    public List<RootDTO> getAllRoots() {
        return rootDAO.getAllRoots();
    }

    public void updateRoot(int id, String rootLetters) {
        RootDTO root = new RootDTO(id, rootLetters);
        rootDAO.updateRoot(root);
    }

    public void deleteRoot(int id) {
        rootDAO.deleteRoot(id);
    }

}
