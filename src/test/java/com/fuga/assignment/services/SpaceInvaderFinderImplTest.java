package com.fuga.assignment.services;

import com.fuga.assignment.interfaces.MatrixExtractor;
import com.fuga.assignment.interfaces.MatrixMatcher;
import com.fuga.assignment.interfaces.SpaceInvaderFinder;
import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpaceInvaderFinderImplTest {

  private SpaceInvaderFinderImpl spaceInvaderFinderImpl;

  @Mock
  private MatrixExtractor matrixExtractor;

  @Mock
  private MatrixMatcher matrixMatcher;

  @BeforeEach
  void setUp() {
    spaceInvaderFinderImpl = new SpaceInvaderFinderImpl(matrixMatcher, matrixExtractor);
  }

  @Test
  void testNoMatchingSpaceInvadersFinder() {
    int[][] array = new int[][] {{0,1,1,0},{0,1,0,1}};
    Matrix testMatrix = new Matrix(array);
    SubMatrix testSubMatrix = new SubMatrix(array);
    Mockito.when(matrixExtractor.extractSubMatrices(ArgumentMatchers.any()))
        .thenReturn(Collections.singletonList(testSubMatrix));
    Mockito.when(matrixMatcher.determineMatchingPercentage(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(45.0f);
    assertEquals(0,spaceInvaderFinderImpl.find(testMatrix, Collections.singletonList(array)).size());
  }

  @Test
  void testMatchingSpaceInvadersFinder() {
    int[][] array = new int[][] {{0,1,1,0},{0,1,0,1}};
    Matrix testMatrix = new Matrix(array);
    SubMatrix testSubMatrix = new SubMatrix(array);
    Mockito.when(matrixExtractor.extractSubMatrices(ArgumentMatchers.any()))
        .thenReturn(Collections.singletonList(testSubMatrix));
    Mockito.when(matrixMatcher.determineMatchingPercentage(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(72.0f);
    assertEquals(1,spaceInvaderFinderImpl.find(testMatrix, Collections.singletonList(array)).size());
  }

}