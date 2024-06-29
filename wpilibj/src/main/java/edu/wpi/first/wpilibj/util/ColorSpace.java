package edu.wpi.first.wpilibj.util;

import edu.wpi.first.math.interpolation.Interpolatable;

public interface ColorSpace<T> extends Interpolatable<T> {
  int toPackedRGB();

  Color toRGB();
}
