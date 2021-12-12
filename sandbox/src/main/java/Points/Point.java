package Points;

public class Point {
  double x;
  double y;

  public Point(double x, double y){
    this.x = x;
    this.y = y;
  }


  public double distance(Point p2) {
    double x = p2.x - this.x;
    double y = p2.y - this.y;

    double ab = (x * x) + (y * y);

    return Math.sqrt(ab);
  }
}
