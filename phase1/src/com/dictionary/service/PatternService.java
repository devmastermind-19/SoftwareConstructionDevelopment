package com.dictionary.service;

import com.dictionary.dao.PatternDAO;
import com.dictionary.model.PatternDTO;
import java.util.List;

public class PatternService implements IPatternService {
    private PatternDAO patternDAO = new PatternDAO();

    @Override
    public void addPattern(PatternDTO pattern) {
        patternDAO.addPattern(pattern);
    }

    @Override
    public List<PatternDTO> getAllPatterns() {
        return patternDAO.getAllPatterns();
    }

    @Override
    public PatternDTO getPatternById(int id) {
        return patternDAO.getPatternById(id);
    }

    @Override
    public void updatePattern(int id, String patternForm) {
        PatternDTO pattern = new PatternDTO(id, patternForm);
        patternDAO.updatePattern(pattern);
    }

    @Override
    public void deletePattern(int id) {
        patternDAO.deletePattern(id);
    }
}
