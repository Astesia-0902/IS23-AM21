package org.am21.client.view.GUI.Interface;

import org.am21.client.view.ClientView;
import org.am21.client.view.GUI.Gui;
import org.am21.client.view.GUI.component.EnemyPanel;
import org.am21.client.view.GUI.component.LivingRoomPanel;
import org.am21.client.view.GUI.utils.ImageUtil;
import org.am21.client.view.GUI.utils.PixelUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LivingRoomInterface extends JDialog {
    public JLayeredPane livingRoomPane;
    public LivingRoomPanel livingRoomPanel;
    public List<String> enemyList;
    public HashMap<String, EnemyPanel> enemiesPanel;


    public LivingRoomInterface(JFrame frame, Gui gui) {
        super(frame);

        try {

            livingRoomPane = new JLayeredPane();
            livingRoomPane.setBounds(0, 0, PixelUtil.pcWidth, PixelUtil.pcHeight);

            livingRoomPanel = new LivingRoomPanel(gui);
            livingRoomPane.add(livingRoomPanel, JLayeredPane.DEFAULT_LAYER);
            enemiesPanel = new HashMap<>();
            enemyList = new ArrayList<>();
            for (String name : ClientView.playersList) {
                if (!name.equals(Gui.username)) {

                    enemyList.add(name);

                }
            }


            if (enemyList.size() >= 1) {
                //setFirst enemy's Label
                EnemyPanel enemyPanelA = new EnemyPanel(PixelUtil.commonY_1, ImageUtil.getBoardImage("enemyA"), enemyList.get(0), ClientView.currentPlayer, ClientView.shelves.get(ClientView.getPlayerIndex(enemyList.get(0))));
                livingRoomPane.add(enemyPanelA, JLayeredPane.PALETTE_LAYER);

                enemiesPanel.put(enemyList.get(0), enemyPanelA);
            }
            if (enemyList.size() >= 2) {
                //setSecond enemy's Label
                EnemyPanel enemyPanelB = new EnemyPanel(PixelUtil.commonY_2, ImageUtil.getBoardImage("enemyB"), enemyList.get(1), ClientView.currentPlayer, ClientView.shelves.get(ClientView.getPlayerIndex(enemyList.get(1))));
                livingRoomPane.add(enemyPanelB, JLayeredPane.PALETTE_LAYER);

                enemiesPanel.put(enemyList.get(1), enemyPanelB);

            }
            if (enemyList.size() >= 3) {
                //setThird enemy's Label
                EnemyPanel enemyPanelC = new EnemyPanel(PixelUtil.commonY_3, ImageUtil.getBoardImage("enemyC"), enemyList.get(2), ClientView.currentPlayer, ClientView.shelves.get(ClientView.getPlayerIndex(enemyList.get(2))));
                livingRoomPane.add(enemyPanelC, JLayeredPane.PALETTE_LAYER);

                enemiesPanel.put(enemyList.get(2), enemyPanelC);
            }


            getContentPane().add(livingRoomPane);

            setSize(PixelUtil.pcWidth, PixelUtil.pcHeight);
            setLayout(null);
            setUndecorated(true);
            setLocationRelativeTo(null);
            setVisible(true);
            System.out.println("Your PC have: \nhigh:" + PixelUtil.pcHeight + "\nwide:" + PixelUtil.pcWidth);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "something is wrong, please renter\r\n\r\n" + e, "Waring", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void refreshEnemiesScores(List<Integer> scores){
        for(String name : enemyList){
            enemiesPanel.get(name).refreshEnemyScores(scores.get(ClientView.getPlayerIndex(name)));
        }
    }

    public void refreshEnemiesShelves(List<String[][]> shelves){
        for(String name : enemyList){
            enemiesPanel.get(name).refreshEnemyShelf(shelves.get(ClientView.getPlayerIndex(name)));
        }
    }
}
