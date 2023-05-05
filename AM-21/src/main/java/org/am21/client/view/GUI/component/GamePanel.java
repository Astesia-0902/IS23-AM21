package org.am21.client.view.GUI.component;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int pcWidth = (int) screenSize.getWidth();
    int pcHeight = (int) screenSize.getHeight();

    /*TODO:game procede*/
    final int gridRows = 999;
    final int gridColumns = 999;
    final int gridSize = 55;

    //menu
    private final JButton jmMenu;
    private ImageIcon imageIconMenu;


    private JMenuItem jmiRule;
    private JMenuItem jmiHelp;
    private JMenuItem jmiLeave;

    //user me
    JLabel userMe;
    private ImageIcon imageIconUserMe;

    //user A
    private final JButton userA;
    private ImageIcon imageIconUserA;
    //user B
    private final JButton userB;
    private ImageIcon imageIconUserB;
    //user C
    private final JButton userC;
    private ImageIcon imageIconUserC;

    //action button
    private final JButton jbInsert;
    private final JButton jbClearSelection;


    //background label
    private final JLayeredPane panelBoard;
    JLabel backGroundLabel;
    private ImageIcon imageIconBackGround;

    //game board
    JLabel gameBoardLabel;
    private ImageIcon imageIconGameBoard;

    //Shelf
    JLabel shelfBoardLabel;
    private ImageIcon imageIconShelfBoard;

    //personal goal
    JLabel personalGoalLabel;
    private ImageIcon imageIconPersonalGoalEmpty;

    //common goal A
    JLabel commonGoalALabel;
    private ImageIcon imageIconCommonGoalAEmpty;

    //common goal B
    JLabel commonGoalBLabel;
    private ImageIcon imageIconCommonGoalBEmpty;

    //bag
    JLabel bagLabel;
    private ImageIcon imageIconBagClose;


    //chat box
    private final JTextArea chatHistory;
    private final JTextField messageField;
    private final JScrollPane scrollHistoryPane;
    private final JLayeredPane chatPanel;
    private final JButton sendButton;

    //take card board
    JLabel takeCard;
    private ImageIcon imageIconTakeCard;


    //card
    final int gameBoardItemSize =999;
    final int itemSize = 999;
    JLabel[] labelItem = new JLabel[999];
    private ImageIcon[] imageIconItem = new ImageIcon[18];


    public GamePanel(){

        //back board panel
        this.setSize(pcWidth,pcHeight);
        this.setLayout(null);
        this.setOpaque(false);

        this.panelBoard = new JLayeredPane();
        this.panelBoard.setBounds(0,0,pcWidth,pcHeight);
        this.panelBoard.setLayout(null);
        this.panelBoard.setOpaque(false);
        this.add(this.panelBoard);

        this.loadImage();

        //back board label
        this.backGroundLabel = new JLabel();
        this.backGroundLabel.setBounds(0,0,this.panelBoard.getWidth(),this.panelBoard.getHeight());
        this.backGroundLabel.setIcon(this.imageIconBackGround);
        /*TODO: add Listener event*/
        this.panelBoard.add(this.backGroundLabel,JLayeredPane.DEFAULT_LAYER);

        //game board label
        this.gameBoardLabel = new JLabel();
        this.gameBoardLabel.setBounds(pcHeight*36/100,pcHeight/10,pcHeight*74/100,pcHeight*74/100);
        this.gameBoardLabel.setIcon(this.imageIconGameBoard);
        //this.gameBoardLabel.setOpaque(true);
        //this.gameBoardLabel.setBackground(new Color(56, 94, 58, 230));
        //this.gameBoardLabel.setForeground(new Color(4, 134, 10, 230));
        this.panelBoard.add(this.gameBoardLabel,JLayeredPane.PALETTE_LAYER);



        //shelf board label
        this.shelfBoardLabel = new JLabel();
        this.shelfBoardLabel.setBounds(pcWidth*70/100,pcHeight*40/100,pcHeight*44/100,pcHeight*44/100);
        this.shelfBoardLabel.setIcon(this.imageIconShelfBoard);
        this.panelBoard.add(this.shelfBoardLabel,JLayeredPane.PALETTE_LAYER);

        //personal goal card label
        this.personalGoalLabel = new JLabel();
        this.personalGoalLabel.setBounds(pcWidth*5/7,pcHeight/10,pcHeight*19/100,pcWidth*18/100);
        this.personalGoalLabel.setIcon(this.imageIconPersonalGoalEmpty);
        this.panelBoard.add(this.personalGoalLabel,JLayeredPane.PALETTE_LAYER);

        //common goal A card label
        this.commonGoalALabel = new JLabel();
        this.commonGoalALabel.setBounds(pcHeight*3/100,pcHeight/10,pcWidth*18/100,pcHeight*19/100);
        this.commonGoalALabel.setIcon(this.imageIconCommonGoalAEmpty);
        this.panelBoard.add(this.commonGoalALabel,JLayeredPane.PALETTE_LAYER);

        //common goal B card label
        this.commonGoalBLabel = new JLabel();
        this.commonGoalBLabel.setBounds(pcHeight*3/100,pcHeight*3/10,pcWidth*18/100,pcHeight*19/100);
        this.commonGoalBLabel.setIcon(this.imageIconCommonGoalBEmpty);
        this.panelBoard.add(this.commonGoalBLabel,JLayeredPane.PALETTE_LAYER);

        //Bag
        this.bagLabel = new JLabel();
        this.bagLabel.setBounds(pcHeight*114/1000,pcHeight*486/1000,pcWidth*9/100,pcWidth*9/100);
        this.bagLabel.setIcon(this.imageIconBagClose);
        this.panelBoard.add(this.bagLabel,JLayeredPane.PALETTE_LAYER);

        //option menu
        this.jmMenu = new JButton();
        //this.jmMenu.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        this.jmMenu.setBounds(pcWidth*964/1000,pcWidth*6/1000,pcWidth*3/100,pcWidth*3/100);
        this.jmMenu.setForeground(new Color(164, 91, 9, 255));
        this.jmMenu.setOpaque(false);
        this.jmMenu.setIcon(this.imageIconMenu);

        /*this.jmiRule = jmMenu.add("Rule");
        this.jmiRule.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //TODO:add Action listner
        this.jmMenu.add(this.jmiRule,JLayeredPane.PALETTE_LAYER);

        this.jmiHelp = jmMenu.add("Help");
        this.jmiHelp.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //TODO:add Action listner
        this.jmMenu.add(this.jmiHelp,JLayeredPane.PALETTE_LAYER);

        this.jmiLeave = jmMenu.add("Leave");
        this.jmiLeave.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //TODO:add Action listner
        this.jmMenu.add(this.jmiHelp,JLayeredPane.PALETTE_LAYER);
*/
        this.panelBoard.add(this.jmMenu,JLayeredPane.PALETTE_LAYER);

        //Insert input button
        this.jbInsert = new JButton("INSERT");
        this.jbInsert.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.jbInsert.setBounds(pcHeight*358/1000,pcHeight*843/1000,pcWidth*230/1000,pcWidth*47/1000);
        this.jbInsert.setOpaque(true);
        this.jbInsert.setBackground(new Color(4, 134, 10, 230));
        this.jbInsert.setForeground(new Color(4, 134, 10, 230));
        //TODO: actionListener
        this.panelBoard.add(this.jbInsert,JLayeredPane.PALETTE_LAYER);

        //clear selection button
        this.jbClearSelection = new JButton("CLEAR");
        this.jbClearSelection.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.jbClearSelection.setBounds(pcHeight*734/1000,pcHeight*843/1000,pcWidth*230/1000,pcWidth*47/1000);
        this.jbClearSelection.setOpaque(true);
        this.jbClearSelection.setBackground(new Color(172, 19, 5, 230));
        this.jbClearSelection.setForeground(new Color(172, 19, 5, 230));
        //this.jbClearSelection.setBorder();
        //TODO: actionListener
        this.panelBoard.add(this.jbClearSelection ,JLayeredPane.PALETTE_LAYER);


        this.BottomOption();


        //chat box
        this.chatPanel = new JLayeredPane();
        this.chatPanel.setBounds(pcWidth*6/1000,pcWidth*393/1000,pcHeight*324/1000,pcHeight*286/1000);
        this.chatPanel.setLayout(null);


        this.panelBoard.add(this.chatPanel,JLayeredPane.PALETTE_LAYER);


        this.chatHistory = new JTextArea();
        this.chatHistory.setEditable(false);
        this.chatHistory.setFont(new Font("DejaVu Sans",Font.PLAIN,12));
        //this.chatHistory.setBounds(10,910,340,40);
        this.chatHistory.setOpaque(false);
        //this.chatHistory.setForeground(new Color(0, 0, 0, 64));
        //this.chatHistory.setBorder(BorderFactory.createLineBorder(new Color(172, 19, 5, 230)));

        this.scrollHistoryPane = new JScrollPane(this.chatHistory);
        this.scrollHistoryPane.setBounds(0,0,pcHeight*324/1000,pcHeight*238/1000);
        this.scrollHistoryPane.setOpaque(false);
        this.scrollHistoryPane.getViewport().setOpaque(false);
        //this.scrollHistoryPane.setBackground(new Color(243, 175, 58, 255));
        this.scrollHistoryPane.setForeground(new Color(85, 35, 222, 230));
        this.scrollHistoryPane.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        this.chatPanel.add(this.scrollHistoryPane,JLayeredPane.MODAL_LAYER);


        this.messageField = new JTextField();
        this.messageField.setBounds(0,pcHeight*24/100,pcHeight*229/1000,pcHeight*38/1000);
        this.messageField.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.messageField.setOpaque(true);
        this.messageField.setForeground(new Color(85, 35, 222, 230));
        this.messageField.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        this.chatPanel.add(this.messageField,JLayeredPane.MODAL_LAYER);

        this.sendButton = new JButton("SEND");
        this.sendButton.setBounds(pcWidth*146/1000,pcHeight*24/100,pcHeight*9/100,pcHeight*38/1000);
        this.sendButton.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.sendButton.setOpaque(true);
        this.sendButton.setBackground(new Color(85, 35, 222, 230));
        this.sendButton.setForeground(new Color(255, 255, 255, 255));
        this.sendButton.setBorder(BorderFactory.createLineBorder(new Color(85, 35, 222, 230)));
        this.chatPanel.add(this.sendButton,JLayeredPane.MODAL_LAYER);
        //this.messagePanel.add(this.sendButton,JLayeredPane.MODAL_LAYER);
        /*



        this.messagePanel.setLayout(new BorderLayout());




        this.messagePanel.add(messageField,BorderLayout.CENTER);
        this.messagePanel.add(sendButton,BorderLayout.EAST);
        this.panelBoard.add(messagePanel,BorderLayout.SOUTH);*/


        /*SwingUtilities.invokeLater(() -> {
            ChatBoxGUI chatBoxGUI = new ChatBoxGUI();
            chatBoxGUI.setVisible(true);
        });*/

        //take card label
        this.takeCard = new JLabel();
        this.takeCard.setBounds(pcWidth*70/100,pcHeight*843/1000,pcHeight*44/100,pcWidth*47/1000);
        this.takeCard.setIcon(this.imageIconTakeCard);
        this.panelBoard.add(this.takeCard,JLayeredPane.PALETTE_LAYER);

        //user Me
        this.userMe = new JLabel();
        this.userMe.setBounds(pcWidth*5/7+pcHeight*19/100+10,pcHeight/10,pcHeight*19/100,pcHeight*19/100);
        this.userMe.setIcon(this.imageIconUserMe);
        this.panelBoard.add(this.userMe,JLayeredPane.PALETTE_LAYER);

        //user A
        this.userA = new JButton();
        this.userA.setBounds(pcHeight*3/100,pcHeight*3/100,pcHeight*6/100,pcHeight*6/100);
        this.userA.setIcon(this.imageIconUserA);
        this.panelBoard.add(this.userA,JLayeredPane.PALETTE_LAYER);

        //user B
        this.userB = new JButton();
        this.userB.setBounds(pcHeight*36/100,pcHeight*3/100,pcHeight*6/100,pcHeight*6/100);
        this.userB.setIcon(this.imageIconUserB);
        this.panelBoard.add(this.userB,JLayeredPane.PALETTE_LAYER);

        //user C
        this.userC = new JButton();
        this.userC.setBounds(pcHeight*69/100,pcHeight*3/100,pcHeight*6/100,pcHeight*6/100);
        this.userC.setIcon(this.imageIconUserC);
        this.panelBoard.add(this.userC,JLayeredPane.PALETTE_LAYER);

    }

    private void loadImage(){
        try{
            this.imageIconBackGround = new ImageIcon(new ImageIcon(PathUtil.getPath("misc/sfondo parquet.jpg")).getImage().getScaledInstance(this.panelBoard.getWidth(), this.panelBoard.getHeight(),Image.SCALE_SMOOTH));

            this.imageIconGameBoard = new ImageIcon(new ImageIcon(PathUtil.getPath("boards/livingroom.png")).getImage().getScaledInstance(pcHeight*74/100,pcHeight*74/100, Image.SCALE_SMOOTH));

            this.imageIconShelfBoard = new ImageIcon(new ImageIcon(PathUtil.getPath("boards/bookshelf_orth.png")).getImage().getScaledInstance(pcHeight*44/100,pcHeight*44/100, Image.SCALE_SMOOTH));

            this.imageIconPersonalGoalEmpty = new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/front_EMPTY.jpg")).getImage().getScaledInstance(pcHeight*19/100,pcWidth*18/100, Image.SCALE_SMOOTH));

            this.imageIconCommonGoalAEmpty = new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/back.jpg")).getImage().getScaledInstance(pcWidth*18/100,pcHeight*19/100, Image.SCALE_SMOOTH));

            this.imageIconCommonGoalBEmpty = new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/back.jpg")).getImage().getScaledInstance(pcWidth*18/100,pcHeight*19/100, Image.SCALE_SMOOTH));

            this.imageIconTakeCard = new ImageIcon(new ImageIcon(PathUtil.getPath("misc/base_pagina2.jpg")).getImage().getScaledInstance(pcHeight*44/100,pcWidth*47/1000, Image.SCALE_SMOOTH));

            this.imageIconBagClose = new ImageIcon(new ImageIcon(PathUtil.getPath("misc/Sacchetto Chiuso.png")).getImage().getScaledInstance(pcWidth*9/100,pcWidth*9/100, Image.SCALE_SMOOTH));

            this.imageIconMenu = new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/menu.png")).getImage().getScaledInstance(pcWidth*3/100,pcWidth*3/100, Image.SCALE_SMOOTH));

            this.imageIconUserMe = new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/user.png")).getImage().getScaledInstance(pcHeight*19/100,pcHeight*19/100, Image.SCALE_SMOOTH));

            this.imageIconUserA = new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U1.jpg")).getImage().getScaledInstance(pcHeight*6/100,pcHeight*6/100, Image.SCALE_SMOOTH));

            this.imageIconUserB = new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U2.jpg")).getImage().getScaledInstance(pcHeight*6/100,pcHeight*6/100, Image.SCALE_SMOOTH));

            this.imageIconUserC = new ImageIcon(new ImageIcon(PathUtil.getPath("icon tool/U3.jpg")).getImage().getScaledInstance(pcHeight*6/100,pcHeight*6/100, Image.SCALE_SMOOTH));

            this.imageIconItem[0] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.1.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[1] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.2.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[2] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Books1.3.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[3] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.1.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[4] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.2.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[5] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Cats1.3.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[6] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.1.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[7] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.2.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[8] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Frames1.3.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[9] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.1.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[10] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.2.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[11] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Games1.3.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[12] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.1.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[13] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.2.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[14] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Plants1.3.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[15] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.1.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[16] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.2.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));
            this.imageIconItem[17] = new ImageIcon(new ImageIcon(PathUtil.getPath("item tiles/Trophies1.3.png")).getImage().getScaledInstance(this.itemSize,this.itemSize,Image.SCALE_SMOOTH));

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;


    }
    private void BottomOption(){
        //chat
       // JLabel jlbChat = new JLabel();

        //Insert button

    }

}
