import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JPanel {

    private int WIDTH = 1000, HEIGHT = 800;
    private TheTurtle turtle;
    private int size;
    private int x, y;

    //构造器
    public MainWindow() {
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.LIGHT_GRAY);
        x = WIDTH / 2;
        y = HEIGHT / 2;
        size = 10;
        turtle = new TheTurtle(TheTurtle.STAY, x, y, size, 10);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                turtle.setAction(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                turtle.disAction(e);
            }
        });
        new MyThread(1).start();
        new MyThread(2).start();
        new MyThread(3).start();
    }

    public MainWindow(int WIDTH, int HEIGHT) {
        this();
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    //重写paint方法
    @Override
    public void paint(Graphics g) {
        Image image = createImage(WIDTH, HEIGHT);
        g.drawImage(image, 0, 0, null);
        turtle.showYourself((Graphics2D) g);
        turtle.action(WIDTH, HEIGHT);
    }

    //线程
    class MyThread extends Thread {
        private int type;

        public MyThread(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    switch (type) {
                        case 1:
                            repaint(0, 0, WIDTH, HEIGHT);
                            Thread.sleep(50);
                            break;
                        case 2:
                            turtle.shakeTail();
                            if (turtle.getStatus() == TheTurtle.STAY){
                                sleep(1000);
                            }
                            break;
                        case 3:
                            turtle.blink();
                            sleep(200);
                            turtle.blink();
                            sleep(1500);
                            break;
                    }
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    //main方法
    public static void main(String[] args) {
        int width, height;
        width = 1000;
        height = 800;
        JFrame jFrame = new JFrame();
        jFrame.setSize(width, height);
        jFrame.setLocation(200,200);
        jFrame.setBackground(Color.LIGHT_GRAY);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Little Turtle");
        MainWindow mw = new MainWindow(width, height);
        jFrame.getContentPane().add(mw);
        jFrame.addKeyListener(mw.getKeyListeners()[0]);
        jFrame.setVisible(true);
        JOptionPane.showMessageDialog(jFrame, "使用WSAD或方向键控制方向。\n空格切换形态， shift加速\n使用Q,E进行缩放,R恢复原来大小", "帮助", JOptionPane.PLAIN_MESSAGE);
    }
}
