package org.am21.client.view.GUI.component;


import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomButtonUI extends BasicButtonUI {

    private final Color foregroundColor;

    public CustomButtonUI(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();

        // 可以自定义按钮的绘制方式
        // ...

        // 设置前景色
        g.setColor(foregroundColor);

        // 绘制文本
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawString(button.getText(), button.getWidth() / 2 - button.getFontMetrics(button.getFont()).stringWidth(button.getText()) / 2, button.getHeight() / 2 + button.getFontMetrics(button.getFont()).getAscent() / 3);
    }
}
