interface PegSolitaireInterface {

    void actionPerformed(java.awt.event.ActionEvent e);
    int	boardScore();
    void ChangeBoard();
    java.lang.Object clone() throws CloneNotSupportedException;
    void createMenu();
    boolean	endofGame();
    void initialize(int boardType);
    void initialize(javax.swing.JButton[][] otherBoard);
    void loadFile();
    void movePegSolitaire(java.lang.String direction);
    void OtherButtons();
    void PlayAuto();
    void PlayAutoAll() throws InterruptedException;
    void startGame();
}

