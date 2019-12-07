import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.Random;

public class PieChart {
    private double x;
    private double y;
    private double height;
    private double width;
    private  HistogramAlphaBet drawSource;

    PieChart(double x, double y, double height, double width, HistogramAlphaBet drawSource){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.drawSource = drawSource;
    }

    public void draw(GraphicsContext gc, int numArcs){
        double remainder = 1.0;
        double totalValue = 0.0;
        double startAngle;
        double arcAngle;
        int y = 50; // y coordinate for label
        int xCord = 500; //  x coordinate for color key
        int yCord = 30; // y coordinate for color key
        gc.fillText("Legend ",580,20);

        for(int i = 0; i < numArcs; i++) {
            startAngle = ((totalValue * 360));
            arcAngle = (((drawSource.getCharMap()).get((drawSource.getCharMap()).keySet().toArray()[i]) * 360)+0.7);
            gc.setFill(getRandColor());
            gc.fillArc(60,100,400,400,startAngle,arcAngle, ArcType.ROUND);
            totalValue += (drawSource.getCharMap()).get((drawSource.getCharMap()).keySet().toArray()[i]);
            gc.fillRect(xCord,yCord,50,30);
            gc.fillText("= "+ ((drawSource.getCharMap()).keySet().toArray()[i]) + ": " +
                    (drawSource.getCharMap()).get((drawSource.getCharMap()).keySet().toArray()[i]), 560, y);
            yCord += 50;
            y += 50;
        }
        remainder -= totalValue;
        startAngle =((totalValue * 360)+ 0.5);
        arcAngle = ((remainder * 360)+ 0.5);
        gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        gc.fillArc(60,100,400,400,startAngle,arcAngle,ArcType.ROUND);
        gc.fillRect(xCord,yCord,50,30);
        gc.fillText("= other: " + remainder, 560, y);
    }

    public Color getRandColor(){
        Random rand = new Random();
        return Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }
}
