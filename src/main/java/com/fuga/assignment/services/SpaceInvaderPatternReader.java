package com.fuga.assignment.services;

import com.fuga.assignment.customfunctions.Try;
import com.fuga.assignment.interfaces.InputReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpaceInvaderPatternReader {

  private final InputReader inputReader;

  public SpaceInvaderPatternReader(InputReader inputReader) {
    this.inputReader = inputReader;
  }

  public List<int[][]> getSpaceInvaderPatterns(File... files) {
    return Arrays.stream(files)
        .map(Try.lift(inputReader::fileToArray))
        .filter(Try::isSuccess)
        .map(Try::get)
        .collect(Collectors.toList());
  }

}
