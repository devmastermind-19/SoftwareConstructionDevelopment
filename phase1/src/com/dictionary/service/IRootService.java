package com.dictionary.service;


import com.dictionary.model.RootDTO;
import java.util.List;

public interface IRootService {
    void addRoot(RootDTO root);
    void updateRoot(int id, String letters);
    void deleteRoot(int id);
    List<RootDTO> getAllRoots();
}
