package com.fuga.assignment.services;

import com.fuga.assignment.interfaces.MatrixExtractor;
import com.fuga.assignment.model.DisjointUnionSets;
import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;
import com.fuga.assignment.model.Pixel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatrixExtractorImpl implements MatrixExtractor {
  private final static int MAX_SIZE = 1000*1000;
  private final static int MIN_OBJECT_SIZE = 5;

  private int[][] labels;

  public List<SubMatrix> extractSubMatrices(Matrix rawMatrix) {
    identifyBlocks(rawMatrix);
    return computeObjects();
  }

  /**
   * Receives an Image object as an input and identifies the continuous blocks of data
   * in the matrix. Blocks are identified starting with the top left node by visiting neighbors
   * and marking them accordingly. The algorithm passes the matrix two times. First one checks the neighbors
   * of the start point and assigns them a label. The label is saved in a disjoint union set eg.
   * each node is saved in a set and they are slowly joined according to the neighboring labels using
   * the minimum label as a representative
   * The second pass updates the resulting image matrix with the joined labels.
   * @param matrix
   */
  private void identifyBlocks(Matrix matrix) {
    DisjointUnionSets linked = new DisjointUnionSets();
    labels = new int[matrix.getHeight()][matrix.getWidth()];
    int nextLabel = 1;

    for(int row = 0; row < matrix.getHeight(); row++) {
      for(int col = 0; col < matrix.getWidth(); col++) {
        if(!matrix.isFilledSpace(row, col)) {
          continue;
        }
        List<Integer> neighbors = getNeighborsWithLabel(matrix, row, col);
        if(neighbors.isEmpty()) {
          linked.addNewElement(nextLabel);
          labels[row][col] = nextLabel;
          nextLabel++;
        } else {
          int min = MAX_SIZE; // minimum label among all neighbors
          for(Integer n : neighbors) {
            if(min > n) {
              min = n;
            }
          }
          labels[row][col] = min;
          for(int neighbor : neighbors) {
            linked.union(neighbor, min);
          }
        }
      }
    }
    for(int row = 0; row < matrix.getHeight(); row++) {
      for(int col = 0; col < matrix.getWidth(); col++) {
        if(!matrix.isFilledSpace(row, col)) {
          continue;
        }
        labels[row][col] = linked.find(labels[row][col]);
      }
    }

  }

  private List<Integer> getNeighborsWithLabel(Matrix matrix, int row, int col) {
    List<Integer> neighbors = new ArrayList<>();

    int[] rowNum = {-1, 0, 0, 1};
    int[] colNum = {0, -1, 1, 0};

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {

        int x = row + rowNum[i];
        int y = col + colNum[j];

        if (!matrix.isFilledSpace(x, y)) {
          continue;
        }

        if (labels[x][y] > 0) {
          neighbors.add(labels[x][y]);
        }
      }
    }
    return neighbors;
  }

  /**
   * @param matrix
   * @return maximum value found in the input matrix
   */
  private int getMaxValInMatrix(int[][] matrix) {
    int max = 0;
    for(int i = 0; i < matrix.length; i++) {
      for(int j = 0; j < matrix[i].length; j++) {
        if(max < matrix[i][j]) {
          max = matrix[i][j];
        }
      }
    }
    return max;
  }

  /**
   * Returns a list of sub matrices, cropped from the labeled matrix.
   * Cropping is done after checking all the pixels in the matrix that belong to
   * a certain area. The upper and lower boundary points are computed as being the
   * extreme points that will be a part of the block, the left most x and y and rightmost
   * x and y
   * @return list of image elements
   */
  private List<SubMatrix> computeObjects() {
    SubMatrix wholePicture = new SubMatrix(labels);

    // list that will contain different lists containing
    // all the points included in an area
    List<List<Pixel>> areas = new ArrayList<>();

    // array that will contain the extracted image blocks
    List<SubMatrix> subMatrices = new ArrayList<>();

    // the maximum value in the matrix shows how many possible
    // blocks we have in the matrix
    int nbOfBlocks = getMaxValInMatrix(labels);

    for(int i = 0; i <= nbOfBlocks; i++) {
      areas.add(i, new ArrayList<>());
    }

    //add to the list of pixels in the matrix
    //with the indicated value
    for(int row = 0; row < labels.length; row++) {
      for(int col = 0; col < labels[row].length; col++) {
        areas.get(labels[row][col]).add(new Pixel(row, col));
      }
    }

    for(int i = 0; i <= nbOfBlocks; i++) {
      int sizeOfElement = areas.get(i).size();
      if(sizeOfElement < MIN_OBJECT_SIZE) {
        continue;
      }
      // sort the list of pixel by the x axis to determine x
      // coordinates for the boundary pixels
      areas.get(i).sort(Pixel.X_ORDER);
      int lowestX = areas.get(i).get(0).getX();
      int highestX = areas.get(i).get(sizeOfElement - 1).getX();

      // sort the list of pixel by the y axis to determine y
      // coordinates for the boundary pixels
      areas.get(i).sort(Pixel.Y_ORDER);
      int lowestY = areas.get(i).get(0).getY();
      int highestY = areas.get(i).get(sizeOfElement - 1).getY();

      //the area that is cropped is delimited by the two pixels
      //with the coordinates previously computed
      subMatrices.add(wholePicture.getElementByPixels(lowestX, lowestY, highestX, highestY));
    }

    return subMatrices;
  }
}
