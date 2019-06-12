import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.*;
import sweeper.Box;


public class MainSweeper extends JFrame {
    private Game game;

    private JPanel panel;
    private JLabel label;

    private int COLS ;
    private int ROWS ;
    private int BOMBS;
    private final int IMAGE_SIZE = 56;
    private final int SHIFT_X = 28;
    private final int SHIFT_Y = 12;


    public static void main(String[] args) {
        new MainSweeper();
    }

    private MainSweeper() {
        initDialog(СommunicationUser.START);
        game = new Game(COLS , ROWS , BOMBS);
        game.start();
        Ranges.setSize(new Coord(COLS , ROWS));
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initDialog(String text) {
        JPanel jpanel = new JPanel();
        String option = JOptionPane.showInputDialog(jpanel, text,
                "Настройка", JOptionPane.QUESTION_MESSAGE);
        String[] input = option.split("/");
        try {
            COLS = Integer.parseInt(input[0]);
            ROWS = Integer.parseInt(input[1]);
            BOMBS = Integer.parseInt(input[2]);
            if (COLS < 2 || ROWS < 2 || BOMBS < 1) throw new NullPointerException();
            if (BOMBS > COLS * ROWS / 2) {
                JOptionPane.showMessageDialog(jpanel, СommunicationUser.ERROR_BOMBS);
            }
        } catch (RuntimeException e) {
            initDialog(СommunicationUser.ERROR_MESSAGE);
        }
    }

    private void initLabel() {
        label = new JLabel(СommunicationUser._0);
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
                return СommunicationUser._1;
            case BOMBED:
                return СommunicationUser._3;
            case WINNER:
                return СommunicationUser._2;
            default:
                return СommunicationUser._0;
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JAVA ШЕСТИУГОЛЬНЫЙ САПЁР");
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
