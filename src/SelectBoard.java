import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;


/**
 * Normally i also added pictures of all boards but it didn't work on ubuntu
 * Because of that I had to delete images :(( 
 * And I guess it is working on Java 17, I don't know why
 */

public class SelectBoard extends JFrame implements ActionListener{

    private JRadioButton[] buttons = new JRadioButton[6];
    private JPanel panel;
    
    SelectBoard(){
        super("Selection Board");
    }

    /**
     * This Method show a window which has image of board and radio buttons
     */
    public void showWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); /*Borderlayout has images and radio buttons */
        this.setSize(700,600);

        panel = new JPanel(new FlowLayout());
        buttons[0] = new JRadioButton("Board1");
        buttons[1] = new JRadioButton("Board2");
        buttons[2] = new JRadioButton("Board3");
        buttons[3] = new JRadioButton("Board4");
        buttons[4] = new JRadioButton("Board5");
        buttons[5] = new JRadioButton("Board6");

        ButtonGroup group = new ButtonGroup();
        group.add(buttons[0]);
        group.add(buttons[1]);
        group.add(buttons[2]);
        group.add(buttons[3]);
        group.add(buttons[4]);
        group.add(buttons[5]);

        for (int i = 0; i < buttons.length; i++) { /*Add action listener for radio buttons */
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }
        this.add(panel,BorderLayout.NORTH);
        this.setVisible(true);


    }

    /**
     * This method detects the clicks on buttons and radiobuttons and it applies required proccess
     */
    public void actionPerformed(ActionEvent e){

        this.setVisible(false);
        PegSolitaire Peg = new PegSolitaire(); /*Starts the game with selected board */

        for (int i = 0; i < buttons.length; i++) {
            if(((JRadioButton) e.getSource()) == buttons[i])
                Peg.initialize(i+1);    
        }
    }
    
}
