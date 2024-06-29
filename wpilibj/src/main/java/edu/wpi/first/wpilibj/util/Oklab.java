package edu.wpi.first.wpilibj.util;

import edu.wpi.first.math.MathUtil;

public class Oklab implements ColorSpace<Oklab> {
  public final double l;
  public final double a;
  public final double b;

  public Oklab(double l, double a, double b) {
    this.l = l;
    this.a = a;
    this.b = b;
  }

  /**
   * Constructs an Oklab color from a LinearRGB color.
   *
   * @param linearRGB the LinearRGB color
   * @return the Oklab color
   */
  public static Oklab fromLinearRGB(LinearRGB linearRGB) {
    double l = 0.4122214708 * linearRGB.x + 0.5363325363 * linearRGB.y + 0.0514459929 * linearRGB.z;
    double m = 0.2119034982 * linearRGB.x + 0.6806995451 * linearRGB.y + 0.1073969566 * linearRGB.z;
    double s = 0.0883024619 * linearRGB.x + 0.2817188376 * linearRGB.y + 0.6299787005 * linearRGB.z;

    double l_ = Math.cbrt(l);
    double m_ = Math.cbrt(m);
    double s_ = Math.cbrt(s);

    return new Oklab(
        0.2104542553 * l_ + 0.7936177850 * m_ - 0.0040720468 * s_,
        1.9779984951 * l_ - 2.4285922050 * m_ + 0.4505937099 * s_,
        0.0259040371 * l_ + 0.7827717662 * m_ - 0.8086757660 * s_);
  }

  /**
   * Constructs an Oklab color from an {@link Color sRGB} color.
   *
   * @param r the red component of the color. [0, 255]
   * @param g the green component of the color. [0, 255]
   * @param b the blue component of the color. [0, 255]
   * @return the Oklab color
   */
  public static Oklab fromRGB(int r, int g, int b) {
    return fromLinearRGB(LinearRGB.fromRGB(r, g, b));
  }

  /**
   * Constructs an Oklab color from an {@link Color sRGB} color.
   *
   * @param r the red component of the color. [0, 1]
   * @param g the green component of the color. [0, 1]
   * @param b the blue component of the color. [0, 1]
   * @return the Oklab color
   */
  public static Oklab fromRGB(double r, double g, double b) {
    return fromLinearRGB(LinearRGB.fromRGB(r, g, b));
  }

  /**
   * Constructs an Oklab color from an {@link Color sRGB} color.
   *
   * @param color the sRGB color
   * @return the Oklab color
   */
  public static Oklab fromRGB(Color color) {
    return fromLinearRGB(LinearRGB.fromRGB(color));
  }

  @Override
  public Oklab interpolate(Oklab endValue, double t) {
    return new Oklab(
        MathUtil.interpolate(this.l, endValue.l, t),
        MathUtil.interpolate(this.a, endValue.a, t),
        MathUtil.interpolate(this.b, endValue.b, t));
  }

  /**
   * Converts this Oklab color to a LinearRGB color.
   *
   * @return the LinearRGB color
   */
  public LinearRGB toLinearRGB() {
    double l_ = l + 0.3963377774 * a + 0.2158037573 * b;
    double m_ = l - 0.1055613458 * a - 0.0638541728 * b;
    double s_ = l - 0.0894841775 * a - 1.2914855480 * b;

    double l = l_ * l_ * l_;
    double m = m_ * m_ * m_;
    double s = s_ * s_ * s_;

    double x = 4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s;
    double y = -1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s;
    double z = -0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s;

    return new LinearRGB(x, y, z);
  }

  @Override
  public Color toRGB() {
    return toLinearRGB().toRGB();
  }

  @Override
  public int toPackedRGB() {
    return toLinearRGB().toPackedRGB();
  }
}
