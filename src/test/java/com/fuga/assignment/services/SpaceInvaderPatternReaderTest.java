package com.fuga.assignment.services;

import com.fuga.assignment.interfaces.InputReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SpaceInvaderPatternReaderTest {

  private SpaceInvaderPatternReader spaceInvaderPatternReader;

  @Mock
  private InputReader inputReader;

  @BeforeEach
  void setUp() {
    spaceInvaderPatternReader = new SpaceInvaderPatternReader(inputReader);
  }

  @Test
  void getSpaceInvaderPatterns() throws IOException {
    assertTrue(true);
    Mockito.when(inputReader.fileToArray(ArgumentMatchers.any(File.class))).thenReturn(new int[][]{{1,2,3},{1,2,3}});
    assertEquals(spaceInvaderPatternReader.getSpaceInvaderPatterns(new File("src/test/resources/samples/invader-1.txt")).size(), 1);
  }

  @Test
  void getSpaceInvaderPatternsForNoFileFound() throws FileNotFoundException {
    assertTrue(true);
    Mockito.when(inputReader.fileToArray(ArgumentMatchers.any(File.class))).thenThrow(FileNotFoundException.class);
    assertEquals(spaceInvaderPatternReader.getSpaceInvaderPatterns(new File("src/test/resources/samples/invader-3.txt")).size(), 0);
  }
}