import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class ScorePanel extends JPanel {

    final static int PILOT_SKILL_X = 112;
    final static int PILOT_SKILL_Y = 125;
    final static Color PILOT_SKILL_COLOR = new Color(248,128,0);

    final static int ATTACK_X = 303;
    final static int ATTACK_Y = 125;
    final static Color ATTACK_COLOR = new Color(236, 25, 19);

    final static int AGILITY_X = 474;
    final static int AGILITY_Y = 125;
    final static Color AGILITY_COLOR = new Color(106, 191, 59);

    final static int SHIELD_X = 644;
    final static int SHIELD_Y = 125;
    final static Color SHIELD_COLOR = new Color(30, 144, 255);

    final static int HULL_X = 809;
    final static int HULL_Y = 125;
    final static Color HULL_COLOR = new Color( 240, 237, 0) ;


    private BufferedImage image;
    private BufferedImage originalColorImage;
    private BufferedImage originalGrayScaleImage;

    private int agility = 0 ;
    private int attack  = 0;
    private int pilotSkill  = 0;
    private int hull  = 0;
    private int shield  = 0;
    String pilotName;
    private Main main;


    public ScorePanel(String pilotName, Main main) {
        this.main = main;
        this.pilotName = pilotName;
        try {

            originalColorImage = ImageIO.read(getClass().getResource("res/ImageBoard2.png"));
            originalGrayScaleImage = ImageIO.read(getClass().getResource("res/ImageBoard2GrayScale.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        image = process(this.originalGrayScaleImage);
        this.addKeyListener(new MyKeyListener(this));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    private void change()
    {
        if ( deadPilot() ) {
            image = process(this.originalGrayScaleImage);
        }
        else {
            image = process(this.originalColorImage);
        }
        this.repaint();

    }

    public void changeAgility(int direction) {
        this.agility += direction;
        change();
    }

    public void changePilotSkill(int direction) {
        this.pilotSkill += direction;
        change();
    }

    public void changeAttack(int direction) {
        this.attack += direction;
        change();
    }

    public void changeShield(int direction) {
        this.shield += direction;
        change();
    }

    public void changeHull(int direction) {
        this.hull += direction;
        change();
    }

    int getHullFontSize() {
        if ( hull > 9) {
            return 75;
        }
        return 100;
    }

    int getPilotSkillFontSize() {
        if ( pilotSkill > 9) {
            return 75;
        }
        return 100;
    }

    private boolean deadPilot() {
        return this.hull <= 0;
    }

    private void setDeadPilotFontColor( Graphics2D g2d ) {
        if ( deadPilot()) {
            g2d.setPaint( Color.black);
        }
    }
    private void createStatCounter(BufferedImage img, BufferedImage old)
    {
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(PILOT_SKILL_COLOR);
        setDeadPilotFontColor(g2d);
        g2d.setFont(new Font("Serif Gothic", Font.BOLD, getPilotSkillFontSize()));
        g2d.drawString(Integer.toString( pilotSkill ), PILOT_SKILL_X, PILOT_SKILL_Y);

        g2d.setPaint(ATTACK_COLOR);
        setDeadPilotFontColor(g2d);
        g2d.setFont(new Font("Serif Gothic", Font.BOLD, 100 ) );
        g2d.drawString(Integer.toString( attack ), ATTACK_X, ATTACK_Y);

        g2d.setPaint(AGILITY_COLOR);
        setDeadPilotFontColor(g2d);
        g2d.setFont(new Font("Serif Gothic", Font.BOLD, 100 ) );
        g2d.drawString(Integer.toString( agility ), AGILITY_X, AGILITY_Y);

        g2d.setPaint(SHIELD_COLOR);
        setDeadPilotFontColor(g2d);
        g2d.setFont(new Font("Serif Gothic", Font.BOLD, 100 ) );
        g2d.drawString(Integer.toString( shield ), SHIELD_X, SHIELD_Y);

        g2d.setPaint(HULL_COLOR);
        setDeadPilotFontColor(g2d);
        g2d.setFont(new Font("Serif Gothic", Font.BOLD, getHullFontSize()));
        g2d.drawString(Integer.toString( hull ), HULL_X, HULL_Y);

        g2d.setPaint(Color.white);
        setDeadPilotFontColor(g2d);
        g2d.setFont(new Font("Serif Gothic", Font.BOLD, 30 ) );
        g2d.drawString(pilotName, 12, 33);

        g2d.dispose();

    }

    public static void makeGray(BufferedImage img)
    {
        for (int x = 0; x < img.getWidth(); ++x)
            for (int y = 0; y < img.getHeight(); ++y)
            {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int grayLevel = (r + g + b) / 3;
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                img.setRGB(x, y, gray);
            }
    }

    private BufferedImage process(BufferedImage old) {
        int w = old.getWidth();
        int h = old.getHeight();


        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_ARGB);

        createStatCounter( img, old );

        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }


    public void notifyMainOfNumberPress(int number) {
        this.main.processNumberKeyPressed( number );
    }

    public void notifyMainOfNewPanel() {
        this.main.addStatPanel();
    }

    public void setPilotName(String newPilotName) {
        this.pilotName = newPilotName;
        change();
    }
}

class MyKeyListener extends KeyAdapter {

    private ScorePanel scorePanel;

    public MyKeyListener( ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    boolean keyIsUpperCase( KeyEvent e )
    {
        return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK) || e.isShiftDown();

    }

    //at most 8 ships per list.
    private boolean keyIsNumber(int key)
    {
        return key == KeyEvent.VK_1 ||
                key == KeyEvent.VK_2 ||
                key == KeyEvent.VK_3 ||
                key == KeyEvent.VK_4 ||
                key == KeyEvent.VK_5 ||
                key == KeyEvent.VK_6 ||
                key == KeyEvent.VK_7 ||
                key == KeyEvent.VK_8;
    }

    private int convertKeyActionToNumber(int action) {
        if ( action == KeyEvent.VK_1  ) return 1;
        if ( action == KeyEvent.VK_2  ) return 2;
        if ( action == KeyEvent.VK_3  ) return 3;
        if ( action == KeyEvent.VK_4  ) return 4;
        if ( action == KeyEvent.VK_5  ) return 5;
        if ( action == KeyEvent.VK_6  ) return 6;
        if ( action == KeyEvent.VK_7  ) return 7;
        if ( action == KeyEvent.VK_8  ) return 8;
        return 0;

    }

    @Override
    public void keyPressed(KeyEvent e) {

        Object source = e.getSource();
        int action = e.getExtendedKeyCode();

        if ( action == KeyEvent.VK_E) {
            String newPilotName = JOptionPane.showInputDialog( null, "Please enter the new pilot name");
            scorePanel.setPilotName(newPilotName);
            return;
        }

        if ( keyIsNumber(action)) {
            scorePanel.notifyMainOfNumberPress(convertKeyActionToNumber(action));
            return;
        }

        if ( action == KeyEvent.VK_N) {
            scorePanel.notifyMainOfNewPanel();
            return;
        }

        int direction = -1;
        if ( keyIsUpperCase( e ) ) {
            direction = 1;
        }

        if ( action == KeyEvent.VK_D ) {
            scorePanel.changeAgility(direction);
        }
        else if ( action == KeyEvent.VK_P) {
            scorePanel.changePilotSkill(direction);
        }
        else if ( action == KeyEvent.VK_A ) {
            scorePanel.changeAttack(direction);
        }
        else if ( action == KeyEvent.VK_S ) {
            scorePanel.changeShield(direction);
        }
        else if ( action == KeyEvent.VK_H) {
            scorePanel.changeHull(direction);
        }
    }
}
