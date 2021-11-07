package com.fuga.assignment.commands;

import com.fuga.assignment.interfaces.InputReader;
import com.fuga.assignment.interfaces.SpaceInvaderFinder;
import com.fuga.assignment.model.Matrix;
import com.fuga.assignment.model.SubMatrix;
import com.fuga.assignment.services.SpaceInvaderPatternReader;
import com.fuga.assignment.model.Pixel;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;
import java.util.List;

@ShellComponent
public class SpaceInvadersAppCommands {

  private final SpaceInvaderPatternReader spaceInvaderPatternReader;

  private final SpaceInvaderFinder spaceInvaderFinder;

  private final InputReader inputReader;

  public SpaceInvadersAppCommands(SpaceInvaderPatternReader spaceInvaderPatternReader, SpaceInvaderFinder spaceInvaderFinder, InputReader inputReader) {
    this.spaceInvaderPatternReader = spaceInvaderPatternReader;
    this.spaceInvaderFinder = spaceInvaderFinder;
    this.inputReader = inputReader;
  }

  @ShellMethod("Find the space invader from the radar image")
  public void findSpaceInvaders(
      @ShellOption(defaultValue = "src/main/resources/samples/invader-1.txt,src/main/resources/samples/invader-2.txt",
          help = "Path to space invader pattern files separated by comma")
          String[] invaderFiles,
      @ShellOption(defaultValue = "src/main/resources/radar.txt",
          help = "Path to the radar image file")
          String radarImage
  ) throws IOException {

    System.out.println("--------Program Started-----------");

    File[] spaceInvaderPatterns = new File[invaderFiles.length];
    int i = 0;
    while (i < invaderFiles.length) {
      spaceInvaderPatterns[i] = new File(invaderFiles[i]);
      i++;
    }

    List<int[][]> spaceInvaders = spaceInvaderPatternReader.getSpaceInvaderPatterns(spaceInvaderPatterns);

    if (spaceInvaders.isEmpty()) {
      System.out.println("No valid space invader patterns provided");
    } else {
      List<SubMatrix> spaceInvadersFound = spaceInvaderFinder.find(
          new Matrix(inputReader
              .fileToArray(new File(radarImage))),
          spaceInvaders);
      if (spaceInvadersFound.isEmpty()) {
        System.out.println("No space invaders found");
      } else {
        System.out.printf("Total no of space invaders found are %s%n", spaceInvadersFound.size());
        for (SubMatrix spaceInvader : spaceInvadersFound) {
          Pixel startingPixel = spaceInvader.getStartingPixel();
          System.out.printf("A space invader found at pixels (%s,%s) %n", startingPixel.getX(), startingPixel.getY());
        }
      }
    }

    System.out.println("----------Program Ended------------");
  }
}
