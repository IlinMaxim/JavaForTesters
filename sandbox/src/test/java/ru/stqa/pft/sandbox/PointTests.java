package ru.stqa.pft.sandbox;

import Points.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance1(){
    Point p1 = new Point(10.10, 15.15);
    Point p2 = new Point(5.5,2.0);

    Assert.assertEquals(p1.distance(p2),13.931349539796926);
  }
  @Test
  public void testDistance2(){
    Point p2 = new Point(5.5,2.0);
    Point p3 = new Point(8.0,7.5);

    Assert.assertEquals(p2.distance(p3),6.041522986797286);
  }
  @Test
  public void testDistance3(){
    Point p1 = new Point(10.10, 15.15);
    Point p3 = new Point(8.0,7.5);

    Assert.assertEquals(p3.distance(p1),7.933000693306411);
  }
}
