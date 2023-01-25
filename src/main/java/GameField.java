import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.PrivateKey;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private final int COUNT_APPLE = 3;// количество яблок которые будут в игре
    private int[][] appleXY = new int[COUNT_APPLE][2]; //массив яблок
    private int eatApple = 99; //номер яблока которое съели в массиве, если 99 то яблоко не съели так как в не диапазона и что бы не плодить переменные.
    private  int countEatApple = 0; //количество съеденых яблок для вывода на экран
    private Image dot;
    private Image apple;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int appleX,appleX1,appleX2;
    private int appleY,appleY1,appleY2;
    private int dots;
    private Timer timer;

    private boolean left = false;
    private  boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private  boolean inGame = true;

    public void loadImage(){
        ImageIcon iia = new ImageIcon("d:\\SOLO\\education\\IdeaProjects\\Game\\target\\apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("d:\\SOLO\\education\\IdeaProjects\\Game\\target\\dot.png");
        dot = iid.getImage();
    }
    public void createApple(){
        Random random = new Random();
        if (eatApple == 99) { //если яблоко не съели то просто гинерим новые координаты
            for (int i = 0; i < COUNT_APPLE; i++) {
                appleXY[i][0] = random.nextInt(20) * DOT_SIZE;
                appleXY[i][1] = random.nextInt(20) * DOT_SIZE;
            }
        }else { // если яблоко съели значит не 99 а номер съеденного яблока ему и генерим новые координаты
            appleXY[eatApple][0] = random.nextInt(20) * DOT_SIZE;
            appleXY[eatApple][1] = random.nextInt(20) * DOT_SIZE;
            eatApple = 99; // возвращаем в исходное положение
        }
            //appleX = random.nextInt(20)*DOT_SIZE;
            //appleY = random.nextInt(20)*DOT_SIZE;
}
    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++){
            y[i] = 48;
            x[i] = 48 - i*DOT_SIZE;
        }
        timer = new Timer(150,this);
        timer.start();
        createApple();
    }
    public void checkApple(){
        for (int i=0;i<COUNT_APPLE;i++) {
            if (x[0] == appleXY[i][0] && y[0] == appleXY[i][1]) {
                dots++; //если нашли что яблоко съедено, запоминаем его номер и выходим из цикла
                eatApple = i;
                countEatApple += 1; //счетчик яблок увеличиваем съеденых
                createApple();
                break;
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            for (int i = 0; i < COUNT_APPLE; i++) {
                g.drawImage(apple, appleXY[i][0], appleXY[i][1], this);
                //g.drawImage(apple,appleX1,appleY1,this);
                //g.drawImage(apple,appleX2,appleY2,this);
            }
            for (int i = 0; i < dots; i++){
                g.drawImage(dot, x[i],y[i],this);
            }
        }else {
            String str = "Game Over";
            g.setColor(Color.CYAN);
            g.drawString(str,SIZE/6,SIZE/2);

        }
    String str1 = String.valueOf(countEatApple);
    g.setColor(Color.CYAN);
    g.drawString(str1,SIZE/10,SIZE/10);

    }
    public void checkCollizion() {
        for (int i = dots; i > 0; i--) {
            if (x[0]==x[i]&&y[0]==y[i]){
                inGame = false;
            }
        }
        if (x[0]>SIZE)
            x[0] = 0;
        if (x[0]<0)
            x[0] = SIZE;
        if (y[0]>SIZE)
            inGame = false;
        if (y[0]<0)
            inGame = false;
//        if (y[0]>SIZE)
//            y[0] = 0;
//        if (y[0]<0)
//            y[0] = SIZE;
    }
    @Override
    public void actionPerformed(ActionEvent a) {
        if (inGame) {
            checkApple();
            checkCollizion();
            move();

        }
        repaint();
    }
    public  GameField () {
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);


    }
    public void move () {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) x[0] -= DOT_SIZE;
        if (right) x[0] += DOT_SIZE;
        if (up) y[0] -= DOT_SIZE;
        if (down) y[0] += DOT_SIZE;
    }
    class FiledKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent k) {
            super.keyPressed(k);
            int key = k.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT&&!left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP&&!down){
                left = false;
                right = false;
                up = true;
            }
            if (key == KeyEvent.VK_DOWN&&!up){
                left = false;
                right = false;
                down = true;
            }

        }
    }
}
