import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;


class NumberKeyListener extends KeyAdapter {

    private Main main;

    public NumberKeyListener( Main main) {
        this.main = main;
    }

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

        if ( ! keyIsNumber(action))
            return;

        main.processNumberKeyPressed(convertKeyActionToNumber(action) );


    }

}


public class Main extends JFrame {

    JPanel listContainer;
    private ArrayList<ScorePanel> scorePanels = new ArrayList<>();

    public Main(String title) {
        if (title == null || title.length() == 0 ) {
            title = "StackTracker";
        }
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new NumberKeyListener(this));

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        this.add(new JScrollPane(listContainer), BorderLayout.CENTER);


        for ( int i = 0; i < 2; i++ ) {
            addStatPanel();
        }

        this.pack();
        this.setVisible(true);

    }

    public void addStatPanel() {
        ScorePanel sp = new ScorePanel( "Edit Pilot name", this );
        sp.setFocusable(true);
        listContainer.add(sp);
        scorePanels.add( sp );
        this.revalidate();
    }

    public void processNumberKeyPressed(int numberPressed) {
        int index = numberPressed - 1;
        ScorePanel sp = scorePanels.get( index );
        sp.requestFocusInWindow();
    }

    private static void create(String title) {
        new Main(title);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                String title = "XWing StatTracker";
                if (args.length > 0 ) {
                    title = args[0];
                }
                create(title);
            }
        });
    }
}

