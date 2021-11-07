package com.fuga.assignment.services;

import com.fuga.assignment.interfaces.InputReader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
@Slf4j
public class ImageReader implements InputReader {

  private static final Logger logger = LoggerFactory.getLogger(ImageReader.class);

  private static final char FILLED_PIXEL = 'o';

  /**
   * Reads a file containing an image and save the image as a matrix of ints.
   * The elements that are designated as being part of the blackpixel are marked
   * with a value of 1 in the matrix, for ease of calculation
   *
   * @param inputFile File object containing the input image
   * @return result matrix containing 1s and 0s only
   */
  public int[][] fileToArray(File inputFile) throws FileNotFoundException{

    int[][] matrix;
    Scanner in = null;
    try {
      in = new Scanner(inputFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("File at path {} not Found. Please check and try again", inputFile.getPath());
    }
    char[] length = in.nextLine().trim().toCharArray();
    int intLength = length.length;
    int lineCount = 1;
    while (in.hasNextLine()) {
      lineCount++;
      in.nextLine();
    }
    in.close();
    try {
      in = new Scanner(inputFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      logger.error("File at path {} not Found. Please check and try again", inputFile.getPath());
    }
    matrix = new int[lineCount][intLength];
    lineCount = 0;
    while (in.hasNextLine()) {
      char[] currentLine = in.nextLine().trim().toCharArray();
      matrix[lineCount++] = convertToBinaryArray(currentLine);
    }
    in.close();
    return matrix;
  }

  /**
   * Replaces the blackpixel characters will 1s. The rest will remain zero
   *
   * @param line character array containing one line from the input image
   * @return int array containing the processed line
   */
  private int[] convertToBinaryArray(char[] line) {
    int[] binaryArray = new int[line.length];
    for (int i = 0; i < line.length; i++) {
      binaryArray[i] = line[i] == FILLED_PIXEL ? 1 : 0;
    }
    return binaryArray;
  }

}
