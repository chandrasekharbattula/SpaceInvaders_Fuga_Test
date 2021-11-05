package com.fuga.assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MatrixTest {

  private Matrix testMatrix;

  @BeforeEach
  public void setup() {
    int[][] test2DArray = new int[3][3];
    for (int x = 0; x < test2DArray.length; x++)
    {
      for (int y = 0; y < test2DArray[0].length; y++)
      {
        test2DArray[x][y] = x + y;
      }
    }
    testMatrix = new Matrix(test2DArray);
  }

  @Test
  void testComputeElementsSum() {
    int result = testMatrix.computeElementsSum();
    assertEquals(18, result);
  }

  @Test
  public void testIsValidPointOutOfBoundary() {
    assertFalse(testMatrix.isFilledSpace(5, 0));
  }

  @Test
  public void testIsValidPointFalse() {
    assertFalse(testMatrix.isFilledSpace(0, 0));
  }

  @Test
  public void testIsValidPointTrue() {
    assertTrue(testMatrix.isFilledSpace(1, 2));
  }
}