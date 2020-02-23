
import figura.Rectangle;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main extends JFrame {

    static Rectangle[] originalData;
    static Rectangle[] ansData;
    static int areaUsed;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Main.originalData = getData();
        System.out.println("1) Para maximizar Area.\n 2) Para maximizar Ganancias");
        int option = in.nextInt();
        if (option == 1) {
            Main.maxArea();
        } else {

        }
        Main DrawWindow = new Main();
        DrawWindow.setSize(700, 600);
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
        for (int i = 0; i < 600; i += 50) {
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
        g.drawString("Area Total=" + String.valueOf(areaUsed), 550, 280);
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
                if (value > maxValue) {
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
        connBD();
        return Rectangle.getRectangles(Rectangle.getList());
    }

    static void connBD() {
        Connection conn;
        Statement sentencia;
        ResultSet resultado;
        try { // Se carga el driver JDBC-ODBC
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver JDBC");
            return;
        }

        try { // Se establece la conexi�n con la base de datos Oracle Express
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "gato", "gato");
            sentencia = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("No hay conexion con la base de datos.");
            return;
        }
        int c = 0;
        try {
            resultado = sentencia.executeQuery("SELECT idoferente as id,locales as locals from empire");

            while (resultado.next()) {
                int id = resultado.getInt("id");
                Object[] objects = (Object[]) resultado.getArray(2).getArray();
                for (Object ob : objects) {
                    oracle.sql.STRUCT item = (oracle.sql.STRUCT) ob;
                    Object[] items = item.getAttributes();
                    int[] local = new int[5];
                    int index = 0;
                    for (Object item1 : items) {
                        if (item1 != null) {
                            java.math.BigDecimal value = (java.math.BigDecimal) item1;
                            local[index] = value.intValue();
                            index++;
                        }
                    }
                    if (index != 0) {
                        new Rectangle(id, local[0], local[1], local[2], local[3], local[4]);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
