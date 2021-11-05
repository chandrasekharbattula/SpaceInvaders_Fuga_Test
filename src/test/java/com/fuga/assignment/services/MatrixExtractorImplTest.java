package com.fuga.assignment.services;

import com.fuga.assignment.model.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixExtractorImplTest {

  private MatrixExtractorImpl matrixExtractor;

  @BeforeEach
  void setUp() {
    matrixExtractor = new MatrixExtractorImpl();
  }

  @Test
  void testExtractBlocksWithValidMatrix() {
    Matrix testMatrix = new Matrix(new int[][]{{0, 1, 1, 1, 0, 1, 1},
        {1, 1, 0, 1, 0, 0, 0},
        {1, 1, 0, 0, 1, 1, 1},
        {0, 0, 1, 0, 0, 0, 0},
        {0, 1, 1, 0, 0, 1, 1},
        {0, 1, 1, 1, 0, 1, 1},
        {0, 1, 1, 1, 0, 1, 1},
        {0, 1, 1, 1, 0, 1, 0}});
    assertEquals(3, matrixExtractor.extractSubMatrices(testMatrix).size());
  }

  @Test
  void testExtractBlocksWithInValidMatrix() {
    Matrix testMatrix = new Matrix(new int[][]{{0, 1, 1}, {0, 1, 0}});
    assertEquals(0, matrixExtractor.extractSubMatrices(testMatrix).size());
  }
}