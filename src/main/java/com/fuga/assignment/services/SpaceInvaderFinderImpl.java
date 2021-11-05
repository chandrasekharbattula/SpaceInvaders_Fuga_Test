package com.fuga.assignment.services;

import com.fuga.assignment.interfaces.MatrixExtractor;
import com.fuga.assignment.interfaces.MatrixMatcher;
import com.fuga.assignment.interfaces.SpaceInvaderFinder;
import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpaceInvaderFinderImpl implements SpaceInvaderFinder {
  private final static int MINIMUM_MATCH_PERCENTAGE = 65;

  private final MatrixMatcher matrixMatcher;

  private final MatrixExtractor matrixExtractor;

  public SpaceInvaderFinderImpl(MatrixMatcher matrixMatcher, MatrixExtractor matrixExtractor) {
    this.matrixMatcher = matrixMatcher;
    this.matrixExtractor = matrixExtractor;
  }

  /**
   * Take an radar image in the form of an int matrix of 1s and 0s and returns a list containing
   * elements that was matched and the certain part of the image
   * where it was found. The minimum percentage for the match to be successful is 65%
   * @param radarMatrix original image processed into an int matrix containing 1s and 0s
   * @param spaceInvaders list of space invader matrix to match
   * @return list containing space invader found
   */
  @Override
  public List<SubMatrix> find(Matrix radarMatrix, List<int[][]> spaceInvaders) {
    List<SubMatrix> blocks = matrixExtractor.extractSubMatrices(radarMatrix); // traverse matrix and split into continuous blocks
    List<SubMatrix> spaceInvadersFound = new ArrayList<>();
    for(int[][] sampleSpaceInvader : spaceInvaders) {
      SubMatrix subMatrixFromSample = new SubMatrix(sampleSpaceInvader);
      for(SubMatrix subMatrixFromRadarMatrix : blocks) {
        float percentage = matrixMatcher.determineMatchingPercentage(subMatrixFromRadarMatrix, subMatrixFromSample);
        if(percentage > MINIMUM_MATCH_PERCENTAGE) {
          spaceInvadersFound.add(subMatrixFromRadarMatrix);
        }
      }
    }
    return spaceInvadersFound;
  }
}
