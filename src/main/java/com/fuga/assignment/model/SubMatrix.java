package com.fuga.assignment.model;

public class SubMatrix extends Matrix {
  private final int x;
  private final int y;
  private final boolean edge;

  public SubMatrix(int[][] matrix) {
    super(matrix);
    this.x = 0;
    this.y = 0;
    this.edge = false;
  }

  /**
   * Constructor that takes a new matrix and sets the pixels from where is was cropped
   */
  public SubMatrix(int[][] matrix, int x, int y) {
    super(matrix);
    this.x = x;
    this.y = y;
    edge = x == 0 || y == 0 || x  == matrix.length || y == matrix[0].length;
  }

  /**
   * Crops the original matrix into a submatrix from the specified pixel
   * keeping in mind the width and the length of the new matrix.
   * The original cropping pixel is saved
   * @param x x-axis coordinate for the starting point
   * @param y y-axis coordinate for the starting point
   * @param rows length of the submatrix that will be created
   * @param columns width of the submatrix that will be created
   * @return newly cropped matrix
   */
  public SubMatrix getElement(int x, int y, int rows, int columns) {
    int[][] subMatrix = new int[rows][columns];
    for(int i = 0; i < rows; i++) {
      System.arraycopy(data[x + i], y, subMatrix[i], 0, columns);
    }
    return new SubMatrix(subMatrix, this.x + x , this.y + y);
  }

  /**
   * Crops the original matrix into a submatrix from the specified point
   * including the entire block till another specified point.
   * The original cropping point is saved
   * @param x1 x-axis coordinate for the upper cropping point
   * @param y1 y-axis coordinate for the upper cropping point
   * @param x2 x-axis coordinate for the lower cropping point
   * @param y2 x-axis coordinate for the lower cropping point
   * @return return cropped submatrix
   */
  public SubMatrix getElementByPixels(int x1, int y1, int x2, int y2) {
    int rows = x2 - x1,
        columns = y2 - y1;
    return this.getElement(x1, y1, rows, columns);
  }


  public Pixel getStartingPixel() {
    return new Pixel(x,y);
  }


  public boolean isEdge() {
    return edge;
  }
}
