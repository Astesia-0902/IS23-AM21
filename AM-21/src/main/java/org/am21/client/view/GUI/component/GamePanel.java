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

    //background label
    private JLayeredPane panelBoard;
    JLabel backGroundLabel;
    private ImageIcon imageIconBackGround;

    //game board
    JLabel gameBoardLabel;
    private ImageIcon imageIconGameBoard;

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

        //game label
        this.gameBoardLabel = new JLabel();
        this.gameBoardLabel.setBounds(pcWidth/5,pcHeight/10,777,777);
        this.gameBoardLabel.setIcon(this.imageIconGameBoard);
        this.panelBoard.add(this.gameBoardLabel,JLayeredPane.PALETTE_LAYER);


    }

    private void loadImage(){
        try{
            this.imageIconBackGround = new ImageIcon(new ImageIcon(PathUtil.getPath("misc/sfondo parquet.jpg")).getImage().getScaledInstance(this.panelBoard.getWidth(), this.panelBoard.getHeight(),Image.SCALE_SMOOTH));

            this.imageIconGameBoard = new ImageIcon(new ImageIcon(PathUtil.getPath("boards/livingroom.png")).getImage().getScaledInstance(777,777, Image.SCALE_SMOOTH));

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

}
