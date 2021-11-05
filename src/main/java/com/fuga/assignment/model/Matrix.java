package com.fuga.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Matrix {
  protected int[][] data;

  public int computeElementsSum() {
    int sum = 0;
    for (int[] row : data) {
      for (int i : row) {
        sum += i;
      }
    }
    return sum;
  }
  public int getWidth() {
    return data[0].length;
  }

  public int getHeight() {
    return data.length;
  }

  /**
   * Computes a result matrix after comparing each value and checking if they correspond.
   * Two values are considered a match if they are both 0 or both bigger than 0. The resulting
   * matrix will hold 1s for the cells that match, and 0 for the ones that do not.
   * The comparison is done from the specified row and column of the current object with the 0,0 position
   * of the other matrix.
   * @param row int that specifies at which row the comparison of the current object starts
   * @param col int that specified at which column the comparison of the current object starts
   * @param matrix the image object holding the matrix to compare against
   * @return wrapper around an int matrix containing 1s and 0s only
   */
  public Matrix computeUnionMatrix(int row, int col, Matrix matrix) {
    int[][] dest = new int[Math.max(getHeight(), matrix.getHeight())][Math.max(getWidth(), matrix.getWidth())];
    for(int i = row, k = 0; i < Math.min(getHeight(), matrix.getHeight()); i++, k++) {
      for(int j = col, l = 0; j < Math.min(getWidth(), matrix.getWidth()); j++, l++) {
        if(matrix.get(k,l) >= 1 && this.get(i,j) >= 1 || matrix.get(k,l) == 0 && this.get(i,j) == 0) {
          dest[k][l] = 1;
        }
      }
    }
    return new Matrix(dest);
  }

  public int get(int i, int j){
    return data[i][j];
  }

  public boolean isFilledSpace(int x, int y) {
    return isValidPixel(x, y) && data[x][y] >= 1;
  }

  private boolean isValidPixel(int x, int y) {
    return x >=0 && x < getHeight() && y >=0 && y < getWidth();
  }
}
