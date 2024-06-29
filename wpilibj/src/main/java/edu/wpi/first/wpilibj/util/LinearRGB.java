package edu.wpi.first.wpilibj.util;

import edu.wpi.first.math.MathUtil;

public class LinearRGB implements ColorSpace<LinearRGB> {
  /** The red component of the color. [0, 1] */
  public final double x;

  /** The green component of the color. [0, 1] */
  public final double y;

  /** The blue component of the color. [0, 1] */
  public final double z;

  /**
   * Constructs a LinearRGB color from the given values.
   *
   * @param x the red component of the color. [0, 1]
   * @param y the green component of the color. [0, 1]
   * @param z the blue component of the color. [0, 1]
   * @return the LinearRGB object
   */
  public LinearRGB(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Constructs a LinearRGB color from an {@link Color sRGB} color.
   *
   * @param r the red component of the color. [0, 1]
   * @param g the green component of the color. [0, 1]
   * @param b the blue component of the color. [0, 1]
   * @return the LinearRGB object
   */
  public static LinearRGB fromRGB(double r, double g, double b) {
    return new LinearRGB(linearize(r), linearize(g), linearize(b));
  }

  /**
   * Constructs a LinearRGB object from the given sRGB values.
   *
   * @param r the red component of the color. [0, 255]
   * @param g the green component of the color. [0, 255]
   * @param b the blue component of the color. [0, 255]
   * @return the LinearRGB object
   */
  public static LinearRGB fromRGB(int r, int g, int b) {
    return fromRGB(r / 255.0, g / 255.0, b / 255.0);
  }

  /**
   * Constructs a LinearRGB object from the given sRGB color.
   *
   * @param color the sRGB color
   * @return the LinearRGB object
   */
  public static LinearRGB fromRGB(Color color) {
    return fromRGB(color.red, color.green, color.blue);
  }

  /**
   * Linearizes a sRGB value.
   *
   * @param x the sRGB value to be linearized
   * @return the linear RGB value
   */
  private static double linearize(double x) {
    if (x >= 0.04045) {
      return Math.pow((x + 0.055) / (1 + 0.055), 2.4);
    } else {
      return x / 12.92;
    }
  }

  /**
   * Delinearizes a linear RGB value.
   *
   * @param x the linear RGB value to be delinearized
   * @return the sRGB value
   */
  private static double delinearize(double x) {
    if (x >= 0.0031308) {
      return Math.pow(x, 1.0 / 2.4) * 1.055 - 0.055;
    } else {
      return x * 12.92;
    }
  }

  @Override
  public LinearRGB interpolate(LinearRGB endValue, double t) {
    return new LinearRGB(
        MathUtil.interpolate(this.x, endValue.x, t),
        MathUtil.interpolate(this.y, endValue.y, t),
        MathUtil.interpolate(this.z, endValue.z, t));
  }

  @Override
  public Color toRGB() {
    return new Color(
        (int) Math.round(delinearize(x) * 255),
        (int) Math.round(delinearize(y) * 255),
        (int) Math.round(delinearize(z) * 255));
  }

  @Override
  public int toPackedRGB() {
    return Color.packRGB(
        (int) Math.round(delinearize(x) * 255),
        (int) Math.round(delinearize(y) * 255),
        (int) Math.round(delinearize(z) * 255));
  }
}
