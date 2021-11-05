package com.fuga.assignment.interfaces;

import com.fuga.assignment.model.SubMatrix;

public interface MatrixMatcher {
  float determineMatchingPercentage(SubMatrix sourceMatrix, SubMatrix matrixToMatch);
}
