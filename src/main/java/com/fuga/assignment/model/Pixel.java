package com.fuga.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@AllArgsConstructor
@Data
public class Pixel implements Comparable<Pixel>{

  private int x;
  private int y;

  /**
   * Compares two pixels by x-coordinate.
   */
  public static final Comparator<Pixel> X_ORDER = new XOrder();

  /**
   * Compares two pixels by y-coordinate.
   */
  public static final Comparator<Pixel> Y_ORDER = new YOrder();

  @Override
  public int compareTo(Pixel p) {
    if (this.y < p.y) {
      return -1;
    }
    if (this.y > p.y) {
      return 1;
    }
    return Integer.compare(this.x, p.x);
  }

  // compare pixels according to their x-coordinate
  private static class XOrder implements Comparator<Pixel> {
    public int compare(Pixel p, Pixel q) {
      return Integer.compare(p.x, q.x);
    }
  }

  // compare pixels according to their y-coordinate
  private static class YOrder implements Comparator<Pixel> {
    public int compare(Pixel p, Pixel q) {
      return Integer.compare(p.y, q.y);
    }
  }

}
