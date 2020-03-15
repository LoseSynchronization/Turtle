import java.awt.*;

public class TurtlPart {
    /**
     * turtle的一小部分
     */
    private int x, y;//坐标
    private int width, height;//高宽
    private Color color;

    public TurtlPart(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void show(Graphics2D graphics) {
        Color nowColor = graphics.getColor();
        graphics.setStroke(new BasicStroke(5f));
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x, y, width, height);
        graphics.setColor(this.color);
        graphics.fillOval(x, y + 1, width, height);
        graphics.setColor(nowColor);
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
