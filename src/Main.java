
import figura.Rectangle;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Main extends JFrame {

    static Rectangle[] originalData;
    static Rectangle[] ansData;
    
    public static void main(String[] args) {
        new Rectangle(1,10,10,40,40);
        
        Main.originalData = getData();
        Main DrawWindow = new Main();
        DrawWindow.setSize(550, 550);
        DrawWindow.setResizable(true);
        DrawWindow.setLocation(200, 50);
        DrawWindow.setTitle("Paint");
        DrawWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawWindow.setVisible(true);
        maxArea();
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
        for (int i = 0; i < x; i += 50) {
            g.drawLine(i, 0, i, y);
        }

        g.setColor(Color.red);
        g.drawLine(50, 0, 50, y);
        g.drawLine(0, 500, x, 500);

        g.setColor(Color.BLUE);
        
        for(Rectangle rec: originalData) {
            g.drawRect(50 + rec.bottomLeft.x*5, 500 - rec.upperLeft.y*5,
                    (rec.bottomRight.x*5 - rec.bottomLeft.x*5),
                    (rec.upperLeft.y*5 - rec.bottomRight.y*5));
            g.drawString(String.valueOf(rec.id),50 + rec.bottomLeft.x*5 + (rec.bottomRight.x*5 - rec.bottomLeft.x*5) / 2
                    ,500 - rec.bottomLeft.y*5-(rec.upperLeft.y*5 - rec.bottomRight.y*5)/2);
        }
        for (int i = 0; i <= 10; i++) {
            g.drawString(String.valueOf(i*10),50 + 50*i ,500);
        }
        for (int i = 0; i <= 10; i++) {
            g.drawString(String.valueOf(i*10),50,500 - 50*i);
        }
        
    }
    
    static void maxArea(){
        
    }
    static Rectangle[] getData(){
        
        return Rectangle.getRectangles();
    }
}
