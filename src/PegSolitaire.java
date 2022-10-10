import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.Color;
import java.awt.Dimension;

/**
 * There is PegSolitaire class implementations here.
 * @author Abdulsamed Aslan
 * @version 11.0.7
 */

public class PegSolitaire extends JFrame implements PegSolitaireInterface,ActionListener,Cloneable{

    /**
     * Holds the Peg Solitaire board
     */
    private JButton[][] board;
    private int boardType = 1;
    /**
     * The other buttons, panels and Menu items
     */
    private JButton undo;
    private JButton reset;
    private JButton savefile;
    private JButton loadfile;
    private JButton playAuto;
    private JButton playAutoAll;
    private JPanel buttonPanel;
    private JMenuBar menuBar;
    private JMenuItem exitItem;
    private JMenuItem changeBoard; 
    private String lastDirection;
    private JPanel panel; 
    private JPopupMenu menu;
    private JMenuItem m1;
    private JMenuItem m2;
    private JMenuItem m3;
    private JMenuItem m4;
    private int row=0;
    private int col=0;

        
    PegSolitaire(){
        super("PegSolitaire Game");         
    }
    
    /**
     * Creates the window with layouts and other tools
     */
    public void startGame(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750,600);
        this.setLayout(new FlowLayout(FlowLayout.LEFT)); /*FlowLayout has the board and buttons */
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(500,500));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(board.length,board[0].length,5,5));/*GridLayout has the game board */
        createMenu();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                
                if(board[i][j].getText() == "*")
                {
                    board[i][j].setBackground(Color.BLACK);
                    board[i][j].setForeground(Color.BLACK);
                }
                else{ 
                    board[i][j].setBackground(Color.ORANGE);
                    board[i][j].setForeground(Color.BLACK);
                }
                board[i][j].addActionListener(this);
                panel.add(board[i][j]);  
            }            
        }            
        this.setVisible(true);     
        this.add(panel);
        OtherButtons();
    }
    /**
     * This method detects the clicks on buttons and menuitems and it apply required proccess
     * @param e ActionEvent for buttons and menuitems
     * @overriden 
     *
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource() instanceof JButton && ((JButton) e.getSource()).getText() == "P"){
            menu.show((JButton) e.getSource(), board[0][0].getWidth()/2, board[0][0].getHeight()/2);       
            for (int i = 0; i < board.length; i++){
                for (int j= 0; j < board[i].length; j++){
                    if(board[i][j] == e.getSource()){
                        row = i;
                        col = j;  
                        break;
                    }
                }
            }
        }
        else if(e.getSource() instanceof JButton && ((JButton) e.getSource()).getText() == "Undo"){

            if(lastDirection== "Right"){
                board[row][col].setText("P");
                board[row][col+1].setText("P");
                board[row][col+2].setText(" ");
            }
            else if(col-2>=0 &&lastDirection== "Left"){
                board[row][col].setText("P");
                board[row][col-1].setText("P");
                board[row][col-2].setText(" ");
            }
            else if(lastDirection== "Down"){
                board[row][col].setText("P");
                board[row+1][col].setText("P");
                board[row+2][col].setText(" ");
            }
            else if(row-2>=0 && lastDirection== "Up"){
                board[row][col].setText("P");
                board[row-1][col].setText("P");
                board[row-2][col].setText(" ");
            }
        }
    
        else if(e.getSource() instanceof JButton && ((JButton) e.getSource()) == reset){
            
            this.dispose();
            this.setVisible(false);
            PegSolitaire newGame = new PegSolitaire();
            newGame.initialize(boardType);
        }
        else if(e.getSource() == loadfile) 
            loadFile();
        

        else if(e.getSource() == savefile)
            saveFile();
        

        else if(e.getSource() == exitItem)
            System.exit(0);
        
        else if(e.getSource() == changeBoard)
            ChangeBoard();
        
        else if(e.getSource() instanceof JButton && e.getSource() == playAuto)
            PlayAuto();
        
        else if(e.getSource() instanceof JButton && e.getSource() == playAutoAll){
            try {
                PlayAutoAll();
            }catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            
        }
        else if(e.getSource() == m1 || e.getSource() == m2 || e.getSource() == m3 || e.getSource() == m4)
            movePegSolitaire(((JMenuItem) e.getSource()).getText());    
        
    }

    /**
     * This method plays the game until it is over
     * @throws InterruptedException
     */
    public void PlayAutoAll() throws InterruptedException
    {
        while(!endofGame()){
            PlayAuto();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

    }

    /**
     * This method checks the board and if there is any playable move, it returns false. Otherwise returns true
     * @return Returns a boolean which indicates whether the game is over
     */
    public boolean endofGame(){

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                
                if(board[i][j].getText() == "*");

                else if(i + 2 < board.length && board[i][j].getText() == "P" && board[i + 1][j].getText() == "P"  && board[i + 2][j].getText() == " " )
                    return false;

                else if(j + 2 < board.length && board[i][j].getText() == "P" && board[i][j+1].getText() == "P"  && board[i][j+2].getText() == " " )
                    return false;

                else if(i - 2 >= 0 && board[i][j].getText() == "P" && board[i - 1][j].getText() == "P"  && board[i - 2][j].getText() == " " )
                    return false;

                else if(j - 2 >= 0 && board[i][j].getText() == "P" && board[i][j-1].getText() == "P"  && board[i][j-2].getText() == " " )
                    return false;
            }
        }
        return true;
    }

    /**
     * This method creates Random numbers which is row and column and invoke the movePegSolitaire to do this move
     */
    public void PlayAuto(){

        Random rand = new Random();
        boolean check = false;

        while(!check){
            row = rand.nextInt(board.length); /*Creates random numbers for row */
            col = rand.nextInt(board[0].length);  /*Creates random numbers for column */
            
            if (col + 2 < board[row].length && board[row][col].getText()  ==  "P" && board[row][col+1].getText()  ==  "P" && board[row][col+2].getText()  ==  " "){
                movePegSolitaire("Right");
                check = true;
            }
            else if (col - 2 >= 0 && (board[row][col].getText()  ==  "P" && board[row][col - 1].getText()  ==  "P" && board[row][col - 2].getText()  ==  " ")){
                movePegSolitaire("Left");
                check = true;
            }

            else if (row + 2 < board.length && board[row][col].getText()  ==  "P" && board[row + 1][col].getText()  ==  "P" && board[row + 2][col].getText()  ==  " "){
                movePegSolitaire("Down");
                check = true;
            }

            else if (row - 2 >= 0 && board[row][col].getText()  ==  "P" && board[row - 1][col].getText()  ==  "P" && board[row - 2][col].getText()  ==  " "){
                movePegSolitaire("Up");
                check = true;
            }
        }

    }

    /**
     * If the user click save file button then it invoke this method. This method creates a txt file and writes the board in to the file
     */
    public void saveFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null); // select file to open
    
        try {
            PrintWriter writer = new PrintWriter(fileChooser.getSelectedFile().getName());
            
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    
                    if(board[i][j].getText() == "*")
                        writer.print(" ");
                    else if(board[i][j].getText() == "P")
                        writer.print("P");
                    else if(board[i][j].getText() == " ")
                        writer.print(".");
                }
                writer.println();
            }
            writer.close();

        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

     /**
     * This method opens and reads the board from file and allows to be played in the window 
      If the user click load file button then it invoke this method.
     */
    public void loadFile(){
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null); // select file to open
        int i=0;
        int maxIndex=0;
        int counter=0;

        if(response == JFileChooser.APPROVE_OPTION){

            try {
                File file = new File(fileChooser.getSelectedFile().getName());
                Scanner reader = new Scanner(file);

                JButton[][] tempBoard;
                
                /*Counts the line length and number of line */
                while(reader.hasNextLine()){
                    String str = reader.nextLine();
                    
                    if(str.length() > maxIndex)
                        maxIndex = str.length();
                    ++counter;
                }
                reader.close();

                tempBoard= new JButton[counter][maxIndex];
                reader = new Scanner(file);

                /*Reads the file and adds the board to game panel */
                while(reader.hasNextLine()){
                    String str = reader.nextLine();
                    
                    for (int j = 0; j < maxIndex; j++) {

                        if( str.length() > j && str.charAt(j) == 'P')
                            tempBoard[i][j] = new JButton("P");
                        else if(str.length() > j && str.charAt(j) == '.')
                            tempBoard[i][j] = new JButton(" ");
                        else
                            tempBoard[i][j] = new JButton("*");
                    }
                    ++i;
                    
                     
                }
                reader.close();
                this.dispose();
                this.setVisible(false);
                PegSolitaire newGame = new PegSolitaire();
                newGame.initialize(tempBoard);
                
            } catch (FileNotFoundException ae) {
                System.out.println("An error occurred.");
                ae.printStackTrace();
                
            }
        }
    }

    /**
     * Initializes the the other required buttons and adds them to Frame
     */
    public void OtherButtons(){

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200,200));

        undo = new JButton("Undo");
        undo.addActionListener(this);
        undo.setBackground(Color.LIGHT_GRAY);
        
        reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setBackground(Color.LIGHT_GRAY);

        savefile = new JButton("Save File");
        savefile.addActionListener(this);
        savefile.setBackground(Color.LIGHT_GRAY);

        loadfile = new JButton("Load File");
        loadfile.addActionListener(this);
        loadfile.setBackground(Color.LIGHT_GRAY);

        playAuto = new JButton("One Computer Move");
        playAuto.addActionListener(this);
        playAuto.setBackground(Color.LIGHT_GRAY);
        
        playAutoAll = new JButton("Play Auto All");
        playAutoAll.addActionListener(this);
        playAutoAll.setBackground(Color.LIGHT_GRAY);
        
        buttonPanel.add(undo);
        buttonPanel.add(reset);
        buttonPanel.add(savefile);
        buttonPanel.add(loadfile);
        buttonPanel.add(playAuto);
        buttonPanel.add(playAutoAll);
        this.add(buttonPanel);
    }
    
    /**
     * Initializes a Menu to move peg cells and initializes the MenuBar to change game board and exit the game 
     */
    public void  createMenu(){
        menu = new JPopupMenu("Menu");
        m1 = new JMenuItem("Right");
        m2 = new JMenuItem("Left");
        m3 = new JMenuItem("Down");
        m4 = new JMenuItem("Up");           
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);
        menu.add(m4);
        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);
        m4.addActionListener(this);

        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        
        exitItem = new JMenuItem("Exit Game");
        changeBoard = new JMenuItem("Change the Board");

        fileMenu.add(exitItem);
        editMenu.add(changeBoard);

        exitItem.addActionListener(this);
        changeBoard.addActionListener(this);

        this.setJMenuBar(menuBar);
    }

    /**
     * Close the current window and creates a new selection board window
     */
    public void ChangeBoard(){

        this.setVisible(false);
        this.dispose();
        SelectBoard newWindow = new SelectBoard();
        newWindow.showWindow();
    }
    
    /**
     * This method checks if the movement is legal and then applies the necessary changes
     * @param direction Accepts a direction to move in that direction
     */
    public void movePegSolitaire(String direction){
        int i = row;
        int j= col;
        lastDirection = direction;

        switch (direction)
        {
        case "Right":
            if (j + 2 < board[i].length && board[i][j].getText()  ==  "P" && board[i][j+1].getText()  ==  "P" && board[i][j+2].getText()  ==  " ")// check the move if it is possible
            {
                //switch the PegSolitaires and empty to the right side by rules if it is possible
                board[i][j].setText(" ");
                board[i][j + 1].setText(" ");
                board[i][j + 2].setText("P");
                
            }
           
            break;

        case "Left":

            if (j - 2 >= 0 && (board[i][j].getText()  ==  "P" && board[i][j - 1].getText()  ==  "P" && board[i][j - 2].getText()  ==  " "))// check the move if it is possible
            {
                //switch the PegSolitaires and empty to the left side by rules if it is possible
                board[i][j].setText(" ");
                board[i][j - 1].setText(" ");
                board[i][j - 2].setText("P");
            }
           
            break;

        case "Down":
            if (i + 2 < board.length && board[i][j].getText()  ==  "P" && board[i + 1][j].getText()  ==  "P" && board[i + 2][j].getText()  ==  " ")// check the move if it is possible
            {
                //switch the PegSolitaires and empty to the down side by rules if it is possible
                board[i][j].setText(" ");
                board[i+1][j].setText(" ");
                board[i+2][j].setText("P");
            }
           
                
            break;

        case "Up":
            if (i - 2 >= 0 && board[i][j].getText()  ==  "P" && board[i - 1][j].getText()  ==  "P" && board[i - 2][j].getText()  ==  " ") // check the move if it is possible
            {
                //switch the PegSolitaires and empty to the up side by rules if it is possible
                board[i][j].setText(" ");
                board[i-1][j].setText(" ");
                board[i-2][j].setText("P");
            }
                
            break;

        default:
                
            break;
        }

        if(endofGame() == true)
            JOptionPane.showMessageDialog(null, "Game Over\nYour Score: " + boardScore());
        


    }

    /**
     * Counts the number of peg game at the end of the game and returns it
     * @return Returns a integer 
     */
    public int boardScore(){
        int score=0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) 
                if (board[i][j].getText() == "P")
                    ++score;  

        return score;
    }

    /**
     * This method is overriden initialize method
     * @param otherBoard Accepts a game board and allows the play this board
     */
    public void initialize(JButton[][] otherBoard){
        board = otherBoard;
        startGame();
    }

    /**
     * This method initialize the desired Peg Solitaire Game Board 
     * @param boardType Accepts a board type and inserts the peg and empty cells in to it
     */
    public void initialize(int boardType){

        this.boardType = boardType;
        switch (boardType) {
            case 1:
            board = new JButton[][]{
                {new JButton("*"), new JButton("*") ,new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("P") ,new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton(" "), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("*"), new JButton("P") ,new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*")},
                {new JButton("*"), new JButton("*") ,new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*")}
            };
                break;
            case 2:
            board = new JButton[][]{
                {new JButton("*"),new JButton("*") ,new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"),new JButton("*"), new JButton("*"), new JButton("*")},
                {new JButton("*"),new JButton("*") ,new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"),new JButton("*") ,new JButton("*"), new JButton("*")},
                {new JButton("*") ,new JButton("*"),new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"),new JButton("*") ,new JButton("*"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton(" "), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("*") ,new JButton("*"),new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"),new JButton("*"), new JButton("*"),new JButton("*")},
                {new JButton("*") ,new JButton("*"),new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"),new JButton("*"), new JButton("*"),new JButton("*")},
                {new JButton("*") ,new JButton("*"),new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"),new JButton("*"), new JButton("*"),new JButton("*")}
            };
                
                break;
            case 3:
            board = new JButton[][]{
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton(" "), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*"), new JButton("*")}

            };
                
                break;
            case 4:
            board = new JButton[][]{
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton(" "), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"),new JButton("*")}

            };
                
                break;
            case 5:
            board = new JButton[][]{
                {new JButton("*"), new JButton("*"), new JButton("*"), new JButton("*"), new JButton("P"), new JButton("*"), new JButton("*"), new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton(" "), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")},
                {new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("*"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*"), new JButton("*")},
                {new JButton("*"), new JButton("*"), new JButton("*"), new JButton("*"), new JButton("P"), new JButton("*"), new JButton("*"), new JButton("*"), new JButton("*")}
                
            };
                
                break;
            case 6:
            board = new JButton[][]{
                {new JButton(" "), new JButton("*"), new JButton("*"), new JButton("*"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("*")},
                {new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P"), new JButton("P")}
                
            };
                break;
        
            default:
                break;
        }
        startGame();
    }
    /**
     * Clone method is cloning the object
     */
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

}