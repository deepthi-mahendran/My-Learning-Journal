package weather.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

public class IconLoader {
    private static final String BASE_URL = "https://openweathermap.org/img/w/";
    private static final String EXT = ".png";

    public static ImageIcon getIcon(String iconCode, int width, int height) {
        if (iconCode == null || iconCode.isEmpty()) iconCode = "01d";
        try {
            URL url = new URL(BASE_URL + iconCode + EXT);
            ImageIcon original = new ImageIcon(url);
            Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            // fallback: placeholder
            return createPlaceholder(width, height);
        }
    }

    public static ImageIcon getIcon(String iconCode) {
        return getIcon(iconCode, 64, 64);
    }

    private static ImageIcon createPlaceholder(int w, int h) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics g = img.getGraphics();
        g.setColor(java.awt.Color.GRAY);
        g.fillRect(0, 0, w, h);
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        g.drawString("?", w/2-10, h/2+10);
        g.dispose();
        return new ImageIcon(img);
    }
}