package com.dictionary.service;

import com.dictionary.model.PatternDTO;
import java.util.List;

public interface IPatternService {
    void addPattern(PatternDTO pattern);
    void updatePattern(int id, String patternForm);
    void deletePattern(int id);
    List<PatternDTO> getAllPatterns();
    PatternDTO getPatternById(int id);
}
