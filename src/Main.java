
import figura.Rectangle;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.LinkedList;

public class Main extends JFrame {

    static Rectangle[] originalData;
    static Rectangle[] ansData;
    static int areaUsed;

    public static void main(String[] args) {
        Main.originalData = getData();
        Main.maxArea();
        Main DrawWindow = new Main();
        DrawWindow.setSize(700, 550);
        DrawWindow.setResizable(true);
        DrawWindow.setLocation(200, 50);
        DrawWindow.setTitle("Paint");
        DrawWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawWindow.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Dimension d = getSize();
        int x = d.width;
        int y = d.height;

        g.setColor(Color.yellow);
        g.fillRect(0, 0, x, y);

        g.setColor(Color.green);
        for (int i = 0; i < y; i += 50) {
            g.drawLine(0, i, x, i);
        }
        for (int i = 0; i < 550; i += 50) {
            g.drawLine(i, 0, i, y);
        }

        g.setColor(Color.red);
        g.drawLine(50, 0, 50, y);
        g.drawLine(0, 500, x, 500);

        g.setColor(Color.BLUE);

        for (Rectangle rec : ansData) {
            g.drawRect(50 + rec.bottomLeft.x * 5, 500 - rec.upperLeft.y * 5,
                    (rec.bottomRight.x * 5 - rec.bottomLeft.x * 5),
                    (rec.upperLeft.y * 5 - rec.bottomRight.y * 5));
            g.drawString(String.valueOf(rec.id), 50 + rec.bottomLeft.x * 5 + (rec.bottomRight.x * 5 - rec.bottomLeft.x * 5) / 2,
                    500 - rec.bottomLeft.y * 5 - (rec.upperLeft.y * 5 - rec.bottomRight.y * 5) / 2);
        }
        for (int i = 0; i <= 10; i++) {
            g.drawString(String.valueOf(i * 10), 50 + 50 * i, 500);
        }
        for (int i = 0; i <= 10; i++) {
            g.drawString(String.valueOf(i * 10), 50, 500 - 50 * i);
        }
        g.drawString("Solución al", 550, 250);
        g.drawString("máximar por área. ", 550, 265);
        g.drawString("Area Total="+String.valueOf(areaUsed), 550, 280);
    }

    static void maxArea() {

        LinkedList<Rectangle>[][] matriz = new LinkedList[originalData.length][originalData.length];
        for (int i = 0; i < originalData.length; i++) {
            LinkedList<Rectangle> list = new LinkedList<>();
            list.add(originalData[i]);
            matriz[i][i] = list;
        }
        for (int i = 1; i < originalData.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int areaG = 0;
                for (int k = j; k >= 0; k--) {
                    LinkedList<Rectangle> list = new LinkedList<>();
                    int areaL = 0;
                    for (Rectangle item : matriz[j][k]) {
                        if (Rectangle.isFree(item, matriz[i][i].getFirst())) {
                            list.add(item);
                            areaL += item.area;
                        } else {
                            areaL = 0;
                            list.clear();
                            break;
                        }
                    }
                    if (areaL > areaG) {
                        areaG = areaL;
                        list.add(matriz[i][i].getFirst());
                        matriz[i][j] = list;
                    }
                }
                if (matriz[i][j] == null) {
                    matriz[i][j] = new LinkedList<>();
                }

            }
        }
        int maxValue = 0;
        int indexI = 0;
        int indexJ = 0;
        for (int i = 0; i < originalData.length; i++) {
            for (int j = 0; j <= i; j++) {
                int value = 0;
                for (Rectangle item : matriz[i][j]) {
                    value += item.area;
                }
                if(value> maxValue){
                    maxValue = value;
                    indexI = i;
                    indexJ = j;
                }
            }
        }
        ansData = Rectangle.getRectangles(matriz[indexI][indexJ]);
        areaUsed = maxValue;
    }

    static Rectangle[] getData() {
        new Rectangle(0, 0, 0, 10, 10);
        new Rectangle(0, 10, 10, 20, 20);
        new Rectangle(0, 20, 20, 30, 30);
        new Rectangle(0, 30, 30, 40, 40);
        new Rectangle(0, 40, 40, 50, 50);
        new Rectangle(0, 40, 40, 60, 60);
        return Rectangle.getRectangles(Rectangle.getList());
    }

}
