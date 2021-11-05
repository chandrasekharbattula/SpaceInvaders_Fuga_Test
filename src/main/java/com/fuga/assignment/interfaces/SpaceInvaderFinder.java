package com.fuga.assignment.interfaces;

import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;

import java.util.List;

public interface SpaceInvaderFinder {
  List<SubMatrix> find(Matrix matrix, List<int[][]> spaceInvaders);
}
