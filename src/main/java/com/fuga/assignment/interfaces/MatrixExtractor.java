package com.fuga.assignment.interfaces;

import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;

import java.util.List;

public interface MatrixExtractor {
  List<SubMatrix> extractSubMatrices(Matrix matrixMatrix);
}
