package com.fuga.assignment.services;

import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixMatcherImplTest {

  private MatrixMatcherImpl matrixMatcher;

  @BeforeEach
  void setUp() {
    matrixMatcher = new MatrixMatcherImpl();
  }

  @Test
  void testNoMatchingMatrices() {
    SubMatrix matrixOne = new SubMatrix(new int[][] {{0,0,0}, {0,0,0}});
    SubMatrix matrixTwo = new SubMatrix(new int[][] {{1,1,1}, {1,1,1}});
    assertEquals(0.0f, matrixMatcher.determineMatchingPercentage(matrixOne, matrixTwo));
  }

  @Test
  void testFullMatchingMatrices() {
    SubMatrix matrixOne = new SubMatrix(new int[][] {{1,1,1}, {1,1,1}});
    SubMatrix matrixTwo = new SubMatrix(new int[][] {{1,1,1}, {1,1,1}});
    assertEquals(100.0f, matrixMatcher.determineMatchingPercentage(matrixOne, matrixTwo));
  }

  @Test
  void testHalfMatchingMatrices() {
    SubMatrix matrixOne = new SubMatrix(new int[][] {{1,1,1}, {0,0,0}});
    SubMatrix matrixTwo = new SubMatrix(new int[][] {{1,1,1}, {1,1,1}});
    assertEquals(50.0f, matrixMatcher.determineMatchingPercentage(matrixOne, matrixTwo));
  }
}