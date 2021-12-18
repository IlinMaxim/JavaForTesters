package Points;

public class MainClass {

  public static void main(String[] args) {
    Point p1 = new Point(2, 3);
    Point p2 = new Point(3, 5);

    System.out.println("Расстояние между точкой с координатами (" + p1.x + ", " + p1.y + ") и точкой с координатами (" + p2.x + ", " + p2.y + ") = " + distance(p1, p2));

    System.out.println("Расстояние между точкой с координатами (" + p1.x + ", " + p1.y + ") и точкой с координатами (" + p2.x + ", " + p2.y + ") = " + p1.distance(p2));
  }

  public static double distance(Point p1, Point p2) {
    double x = p2.x - p1.x;
    double y = p2.y - p1.y;

    double ab = (x * x) + (y * y);

    return Math.sqrt(ab);
  }
}
