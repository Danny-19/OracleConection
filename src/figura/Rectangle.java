package figura;

import java.util.LinkedList;

public class Rectangle {

    public int id;
    public Point bottomLeft;
    public Point bottomRight;
    public Point upperLeft;
    public Point upperRight;
    public int area;

    static LinkedList<Rectangle> list = new LinkedList<>();

    public Rectangle(int id, int x1, int y1, int x2, int y2) {
        int bottomLeftX;
        int upperRightX;
        int bottomLeftY;
        int upperRightY;
        if (x1 <= x2) {
            bottomLeftX = x1;
            upperRightX = x2;
        } else {
            bottomLeftX = x2;
            upperRightX = x1;
        }
        if (y1 <= y2) {
            bottomLeftY = y1;
            upperRightY = y2;
        } else {
            bottomLeftY = y2;
            upperRightY = y1;
        }
        this.id = id;
        this.bottomLeft = new Point(bottomLeftX, bottomLeftY);
        this.bottomRight = new Point(upperRightX, bottomLeftY);
        this.upperRight = new Point(upperRightX, upperRightY);
        this.upperLeft = new Point(bottomLeftX, upperRightY);
        this.area = (upperRightX - bottomLeftX) * (upperRightY - bottomLeftY);
        list.add(this);
    }

    @Override
    public String toString() {
        return "Rectangle{" + "bottomLeft=" + bottomLeft + ", upperRight=" + upperRight + ", area=" + area;
    }

    public static Rectangle[] getRectangles(LinkedList<Rectangle> list) {
        int size = list.size();
        Rectangle[] arr = new Rectangle[size];
        int index = 0;
        for (Rectangle rec : list) {
            arr[index] = rec;
            index++;
        }
        return arr;
    }

    public static Boolean isFree(Rectangle A, Rectangle B) {
        if (A.upperLeft.x > B.bottomRight.x || B.upperLeft.x > A.bottomRight.x) {
            return true;
        }
        if (A.upperLeft.y < B.bottomRight.y || B.upperLeft.y < A.bottomRight.y) {
            return true;
        }
        if(A.upperLeft.x == B.upperRight.x || A.upperRight.x == B.upperLeft.x){
            return true;
        }
        if(A.bottomLeft.y == B.upperLeft.y || A.upperLeft.y == B.bottomLeft.y){
            return true;
        }
        return false;

    }

    public static LinkedList<Rectangle> getList() {
        return list;
    }

}
