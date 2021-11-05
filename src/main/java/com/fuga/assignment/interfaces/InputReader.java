package com.fuga.assignment.interfaces;

import java.io.File;
import java.io.FileNotFoundException;

public interface InputReader {
  int[][] fileToArray(File file) throws FileNotFoundException;
}
