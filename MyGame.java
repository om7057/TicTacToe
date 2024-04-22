import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading, clockLabel;
    Font font = new Font("", Font.BOLD, 40);
    JPanel mainPanel;

    JButton[] btns = new JButton[9];

    // Game instance variables...

    int gameChances[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int activePlayer = 0;

    int wps[][] = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };


    MyGame() {
        System.out.println("Creating instance of Game");
        super.setTitle("My TicTacToe Game ");
        super.setSize(650, 650);
        ImageIcon icon = new ImageIcon("src/images/title.png");
        super.setIconImage(icon.getImage());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        super.setVisible(true);
    }


    private void createGUI() {
        this.getContentPane().setBackground(Color.decode("#26D5B8"));
        this.setLayout(new BorderLayout());

        // North Heading
        heading = new JLabel("Tic Tac Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setForeground(Color.white);
        this.add(heading, BorderLayout.NORTH);

        // Creating object of JLabel for clock

        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(JLabel.CENTER);
        clockLabel.setForeground(Color.white);
        this.add(clockLabel, BorderLayout.SOUTH);

        Thread t = new Thread() {
            public void run() {
                try {
                    while (true) {
                        String dateTime = new Date().toLocaleString();

                        clockLabel.setText(dateTime);

                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // Panel Section
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            JButton btn = new JButton();
            btn.setBackground(Color.decode("#26BBD5"));
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i));
        }
        this.add(mainPanel, BorderLayout.CENTER);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton = (JButton) e.getSource();
        String buttonName = currentButton.getName();
        int name = Integer.parseInt(buttonName.trim());


        if (gameChances[name] == 2) {
            if (activePlayer == 1) {
                currentButton.setIcon(new ImageIcon("src/images/x.png"));
                gameChances[name] = activePlayer;
                activePlayer = 0;
            } else {
                currentButton.setIcon(new ImageIcon("src/images/0.png"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }

            for (int[] temp : wps) {
                if ((gameChances[temp[0]] == gameChances[temp[1]]) && (gameChances[temp[1]] == gameChances[temp[2]]) && gameChances[temp[2]] != 2) {
                    int winner = gameChances[temp[0]];

                    JOptionPane.showMessageDialog(null, "Player" + winner + " has won the game..");
                    int i = JOptionPane.showConfirmDialog(this, "Do you want to play more?");
                    if (i == 0) {
                        this.setVisible(false);
                        new MyGame();
                    } else if (i == 1) {
                        System.exit(1234);
                    }
                    break;
                }
            }



        } else {
            JOptionPane.showMessageDialog(this, "Position already occupied");
        }
    }
}
