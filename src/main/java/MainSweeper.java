import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.w3c.dom.ranges.Range;
import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class MainSweeper extends JFrame {
    private Game game;

    private JPanel panel;
    private JLabel label;

    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 56;
    private final int SHIFT_X = 28;
    private final int SHIFT_Y = 12
            ;


    public static void main(String[] args) {
        new MainSweeper();
    }

    private MainSweeper() {
        game = new Game(COLS , ROWS , BOMBS);
        game.start();
        Ranges.setSize(new Coord(COLS , ROWS));
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label , BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for ( Coord coord : Ranges.getAllCoords() ) {
                    g.drawImage((Image) game.getBox(coord).image ,
                            coord.x * IMAGE_SIZE + SHIFT_X * (coord.y % 2) ,
                            coord.y * (IMAGE_SIZE - SHIFT_Y) , this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int y = e.getY() / (IMAGE_SIZE - SHIFT_Y) ;
                int x = (e.getX() - SHIFT_X * (y % 2)) / IMAGE_SIZE;

                Coord coord = new Coord(x , y);
                if ( e.getButton() == MouseEvent.BUTTON1 )
                    game.pressLeftButton(coord);
                if ( e.getButton() == MouseEvent.BUTTON3 )
                    game.pressRightButton(coord);
                if ( e.getButton() == MouseEvent.BUTTON2 )
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE + SHIFT_X ,
                Ranges.getSize().y * (IMAGE_SIZE - SHIFT_Y) + SHIFT_Y));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Think!";
            case BOMBED:
                return "YOU LOSE!";
            case WINNER:
                return "COUNGRATULATION!";
            default:
                return "Welcome!";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Hexagon Sweeper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }


    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        String fileName = name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }
}
