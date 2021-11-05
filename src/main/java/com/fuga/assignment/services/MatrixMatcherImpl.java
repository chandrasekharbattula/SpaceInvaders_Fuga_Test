package com.fuga.assignment.services;

import com.fuga.assignment.interfaces.MatrixMatcher;
import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;
import org.springframework.stereotype.Component;

@Component
public class MatrixMatcherImpl implements MatrixMatcher {

  /**
   * Compares the two matrices pixel by pixel. A new matrix is created,
   * with the sizes of the smaller matrix of the two. At comparison time, the result matrix will only
   * contain values for the values that match in both matrices. In order to match, they either both have to
   * be zero, or positive. The percentage of similarity will be the proportion of positive values from the
   * result matrix as opposed to the original pattern.
   * @param spaceInvaderFound matrix containing part of the image
   * @param originalSpaceInvader matrix containing the original pattern that needs to be found
   * @return percentage of similarity between two matrices
   */
  @Override
  public float determineMatchingPercentage(SubMatrix spaceInvaderFound, SubMatrix originalSpaceInvader) {
    Matrix unionMatrix;
    int widthDiff = spaceInvaderFound.getWidth() - originalSpaceInvader.getWidth(),
        heightDiff = spaceInvaderFound.getHeight() - originalSpaceInvader.getHeight();
    float bestMatch = 0f;
    int originalWidth = originalSpaceInvader.getWidth();
    int originalHeight = originalSpaceInvader.getHeight();
    for(int i = 0; i <= Math.abs(widthDiff); i++) {
      for(int j = 0; j <= Math.abs(heightDiff); j++) {
        SubMatrix croppedOriginalSpaceInvader = originalSpaceInvader;
        // if sub matrix is smaller than the original matrix that needs to be found and it has at least an edge it is a valid candidate
        if(spaceInvaderFound.isEdge() && (widthDiff < 0 && heightDiff < 0)) {
          croppedOriginalSpaceInvader = croppedOriginalSpaceInvader.getElement(i, j, originalHeight - i, originalWidth - j);
        }
        int correctMatrixSum = croppedOriginalSpaceInvader.getHeight() * croppedOriginalSpaceInvader.getWidth();
        unionMatrix = spaceInvaderFound.computeUnionMatrix(i, j, croppedOriginalSpaceInvader); //
        float currentMatch = getPercentage(unionMatrix.computeElementsSum(), correctMatrixSum) ;
        bestMatch = Math.max(currentMatch, bestMatch); // remember the best match
      }
    }
    return bestMatch;
  }

  /**
   * Utility method to compute a percentage from total
   * @param n proportion to be computed
   * @param total the total number
   * @return percentage out of 100
   */
  private float getPercentage(int n, int total) {
    float proportion = ((float) n) / ((float) total);
    return proportion * 100;
  }
}
