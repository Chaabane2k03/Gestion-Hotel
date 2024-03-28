package guiElements;

import java.awt.Color;
import java.awt.Cursor;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;


@SuppressWarnings("serial")
public class Button extends JButton {

	public Button(URL url, String text) {
        setText(text);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(null);
        setBackground(Color.WHITE);
        setIcon(new ImageIcon(url));
        setBounds(83, 150, 137, 113);
        setFocusable(false);
        setOpaque(false);
        UIManager.put("Button.select", new Color(1, 1, 1, 0.2f));
    }

    public Button() {

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(null);
        setBackground(Color.WHITE);
        setBounds(83, 150, 137, 113);
        setFocusable(false);
        setOpaque(false);
        UIManager.put("Button.select", new Color(1, 1, 1, 0.2f));
    }
}

