import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TheTurtle {

    private int status;//状态
    private int x, y;//中心坐标
    private int size;//大小/原有大小
    private int rsize;//原有大小
    private int speed;//移动速度
    private TurtlPart head;
    private TurtlPart shell;
    private TurtlPart shell2;
    private TurtlPart leftHand;
    private TurtlPart rightHand;
    private TurtlPart leftLeg;
    private TurtlPart rightLeg;
    private ArrayList<TurtlPart> eyes;
    private ArrayList<TurtlPart> toes;


    //操作控制
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean shift = false;//控制加速
    private boolean tailDir;//true为左，false为右
    private boolean blink; // true睁眼，false闭眼
    private boolean roll = false;//缩！！！
    private boolean climb = true;//爬！！！ true时显示脚趾，false时不显示

    //常量
    public static final int STAY = 0;
    public static final int MOVE = 1;

    public TheTurtle(int status, int x, int y, int size, int speed) {
        this.status = status;
        this.x = x;
        this.y = y;
        this.size = size;
        this.rsize = size;
        this.tailDir = true;
        this.speed = speed;
        this.blink = true;
        init();
    }

    //初始化
    private void init() {
        head = new TurtlPart(x - 3 * size, y - 17 * size, 6 * size, 8 * size, new Color(255, 228, 196));
        shell = new TurtlPart(x - 10 * size, y - 11 * size, 20 * size, 22 * size, new Color(255, 228, 225));
        shell2 = new TurtlPart(x - 7 * size, y - 8 * size, 14 * size, 16 * size, new Color(255, 228, 225));

        //四肢
        leftHand = new TurtlPart(x - 14 * size, y - 8 * size, 8 * size, 6 * size, new Color(255, 228, 196));
        rightHand = new TurtlPart(x + 5 * size, y - 8 * size, 8 * size, 6 * size, new Color(255, 228, 196));
        leftLeg = new TurtlPart(x - 14 * size, y + 4 * size, 8 * size, 6 * size, new Color(255, 228, 196));
        rightLeg = new TurtlPart(x + 5 * size, y + 4 * size, 8 * size, 6 * size, new Color(255, 228, 196));

        //眼睛
        eyes = new ArrayList<>(2);
        eyes.add(new TurtlPart(x - 2 * size, y - 15 * size, size / 3 * 2, size, Color.BLACK));
        eyes.add(new TurtlPart(x + 1 * size, y - 15 * size, size / 3 * 2, size, Color.BLACK));

        //脚趾
        Color toeColor;
        if (shift){//加速时改变脚趾颜色
            toeColor = new Color(0x54FF9F);
        } else {
            toeColor = new Color(0x696969);
        }
        toes = new ArrayList<>(12);
        //leftHandToes
        toes.add(new TurtlPart(x - 13 * size, y - 7 * size, 2 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x - 14 * size, y - 5 * size, 3 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x - 13 * size, y - 3 * size, 2 * size, 1 * size, toeColor));
        //rightHandToes
        toes.add(new TurtlPart(x + 10 * size, y - 7 * size, 2 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x + 10 * size, y - 5 * size, 3 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x + 10 * size, y - 3 * size, 2 * size, 1 * size, toeColor));
        //leftLegToes
        toes.add(new TurtlPart(x - 13 * size, y + 5 * size, 2 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x - 14 * size, y + 7 * size, 3 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x - 13 * size, y + 9 * size, 2 * size, 1 * size, toeColor));
        //rightLegToes
        toes.add(new TurtlPart(x + 10 * size, y + 5 * size, 2 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x + 10 * size, y + 7 * size, 3 * size, 1 * size, toeColor));
        toes.add(new TurtlPart(x + 10 * size, y + 9 * size, 2 * size, 1 * size, toeColor));

    }

    //露个脸
    public void showYourself(Graphics2D graphics) {
        init();
        if (!roll){
            head.show(graphics);
            leftHand.show(graphics);
            rightHand.show(graphics);
            leftLeg.show(graphics);
            rightLeg.show(graphics);
            //tail
            showTail(graphics);
            shell.show(graphics);
            shell2.show(graphics);
            for (int i = 0; i < eyes.size() && blink; i++) {
                eyes.get(i).show(graphics);
            }
            for (int i = 0; i < toes.size() && climb; i++) {
                toes.get(i).show(graphics);
            }
        } else {
            showTail(graphics);
            shell.show(graphics);
            shell2.show(graphics);
        }

    }

    //露尾巴
    private void showTail(Graphics2D graphics) {
        if (tailDir){
            graphics.setColor(new Color(255, 222, 173));
            graphics.fillOval(x - 2 * size, y + 8 * size, 4 * size, 8 * size);
            //设置画笔颜色和背景颜色相同
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillOval(x - 0 * size, y + 10 * size, 4 * size, 8 * size);
        } else {
            graphics.setColor(new Color(255, 222, 173));
            graphics.fillOval(x - 1 * size, y + 8 * size, 4 * size, 8 * size);
            //设置画笔颜色和背景颜色相同
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillOval(x - 3 * size, y + 10 * size, 4 * size, 8 * size);
        }
    }

    //摇尾巴
    public void shakeTail() {
        tailDir = !tailDir;
    }

    //眨眼
    public void blink() {
        blink = !blink;
    }

    //伸缩切换
    public void rollSwitch() {
        roll = !roll;
    }

    //行为控制
    public void setAction(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            //方向控制
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = true;
                break;
            //加速控制
            case KeyEvent.VK_SHIFT:
                shift = true;
                break;
            //伸缩控制
            case KeyEvent.VK_SPACE:
                rollSwitch();
                break;
        }
        zoom(keyEvent.getKeyCode());
    }

    public void disAction(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_SHIFT:
                shift = false;
                break;
        }
    }

    //行为操作
    public void action(int WIDTH, int HEIGHT) {
        //加速
        int speedMuitl;
        if (shift == true || roll){//缩 时自动加速
            speedMuitl = 2;
        } else {
            speedMuitl = 1;
        }
        //移动
        if (up && y - speedMuitl * speed > 10){
            y -= speedMuitl * speed;
            status = MOVE;
        }
        if (down && y + speedMuitl * speed < HEIGHT - 10){
            y += speedMuitl * speed;
            status = MOVE;
        }
        if (left && x - speedMuitl * speed > 10){
            x -= speedMuitl * speed;
            status = MOVE;
        }
        if (right && x + speedMuitl * speed < WIDTH - 10){
            x += speedMuitl * speed;
            status = MOVE;
        }

        //移动时摇尾巴
        if (status == MOVE){
            shakeTail();
        }

        //climb 爬！！！
        if (status == MOVE){
            climb = !climb;
        } else if (status == STAY){
            climb = true;
        }

        //状态恢复
        status = STAY;
    }

    //缩放-变大缩小
    public void zoom(int key) {
        //Q键变大，E键变小，R键复位
        //最大为20，最小为2
        switch (key) {
            case KeyEvent.VK_Q:
                if (size < 20){
                    size += 1;
                }
                break;
            case KeyEvent.VK_E:
                if (size > 2){
                    size -= 1;
                }
                break;
            case KeyEvent.VK_R:
                size = rsize;
                break;
        }
    }

    //setters and getters
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
