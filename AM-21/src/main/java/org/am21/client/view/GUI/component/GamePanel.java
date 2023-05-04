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

    //button
    private JButton jbInsert;
    private JButton jbClearSelection;


    //background label
    private JLayeredPane panelBoard;
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



    //card
    final int itemSize = 999;
    JLabel[] labelItem = new JLabel[999];
    private ImageIcon[] imageIconItem = new ImageIcon[18];


    public GamePanel(){

        this.setSize(pcWidth,pcHeight);
        this.setLayout(null);

        //back board panel
        this.panelBoard = new JLayeredPane();
        this.panelBoard.setBounds(0,0,pcWidth,pcHeight);
        this.panelBoard.setLayout(null);
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
        this.gameBoardLabel.setBounds(pcHeight*35/100,pcHeight/10,pcHeight*74/100,pcHeight*74/100);
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

        //Insert input button
        this.jbInsert = new JButton("INSERT");
        this.jbInsert.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.jbInsert.setBounds(pcHeight*343/1000,pcHeight*843/1000,pcWidth*232/1000,pcWidth*47/1000);
        this.jbInsert.setOpaque(true);
        this.jbInsert.setBackground(new Color(4, 134, 10, 230));
        this.jbInsert.setForeground(new Color(4, 134, 10, 230));
        //TODO: actionListener
        this.panelBoard.add(this.jbInsert,JLayeredPane.PALETTE_LAYER);

        //clear selection button
        this.jbClearSelection = new JButton("CLEAR");
        this.jbClearSelection.setFont(new Font("DejaVu Sans",Font.PLAIN,16));
        this.jbClearSelection.setBounds(pcHeight*724/1000,pcHeight*843/1000,pcWidth*232/1000,pcWidth*47/1000);
        this.jbClearSelection.setOpaque(true);
        this.jbClearSelection.setBackground(new Color(172, 19, 5, 230));
        this.jbClearSelection.setForeground(new Color(172, 19, 5, 230));
        //this.jbClearSelection.setBorder();
        //TODO: actionListener
        this.panelBoard.add(this.jbClearSelection ,JLayeredPane.PALETTE_LAYER);





        this.BottomOption();


    }

    private void loadImage(){
        try{
            this.imageIconBackGround = new ImageIcon(new ImageIcon(PathUtil.getPath("misc/sfondo parquet.jpg")).getImage().getScaledInstance(this.panelBoard.getWidth(), this.panelBoard.getHeight(),Image.SCALE_SMOOTH));

            this.imageIconGameBoard = new ImageIcon(new ImageIcon(PathUtil.getPath("boards/livingroom.png")).getImage().getScaledInstance(777,777, Image.SCALE_SMOOTH));

            this.imageIconShelfBoard = new ImageIcon(new ImageIcon(PathUtil.getPath("boards/bookshelf_orth.png")).getImage().getScaledInstance(463,463, Image.SCALE_SMOOTH));

            this.imageIconPersonalGoalEmpty = new ImageIcon(new ImageIcon(PathUtil.getPath("personal goal cards/front_EMPTY.jpg")).getImage().getScaledInstance(200,300, Image.SCALE_SMOOTH));

            this.imageIconCommonGoalAEmpty = new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/back.jpg")).getImage().getScaledInstance(300,200, Image.SCALE_SMOOTH));

            this.imageIconCommonGoalBEmpty = new ImageIcon(new ImageIcon(PathUtil.getPath("common goal cards/back.jpg")).getImage().getScaledInstance(300,200, Image.SCALE_SMOOTH));

            //this.imageIconUserACard = new ImageIcon(new ImageIcon(PathUtil.getPath("misc/base_pagina2.jpg")).getImage().getScaledInstance(300,200, Image.SCALE_SMOOTH));

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

    private void BottomOption(){
        //chat
       // JLabel jlbChat = new JLabel();

        //Insert button

    }

}
