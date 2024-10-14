package Game.java;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class Pin {
    private static final String PIN_PROMPT = "Enter your PIN";
    private static final String CONTINUE_BUTTON_LABEL = "CONTINUE";
    private static final Font FONT_BOLD = new Font("", Font.BOLD, 15);

    public void pinView(String cardNum) {
        Commons common = new Commons();
        JFrame frame = (JFrame) common.Frame();
        
        //---------------PASSWORD----------------
        JLabel pswdLabel = new JLabel(PIN_PROMPT);
        pswdLabel.setBounds(50, 270, 250, 20);
        pswdLabel.setFont(FONT_BOLD);
        
        JPasswordField pswdField = new JPasswordField();
        pswdField.setBounds(50, 300, 500, 35);
        pswdField.setFont(FONT_BOLD);
        
        frame.add(pswdLabel);
        frame.add(pswdField);
        //-----------------------------------------
        
        //-----------------BUTTON-----------------
        JButton contButton = new JButton(CONTINUE_BUTTON_LABEL);
        contButton.setBounds(200, 400, 200, 50);
        contButton.setFont(new Font("Rockwell", Font.BOLD, 25));
        frame.add(contButton);
        
        contButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String pin = new String(pswdField.getPassword());
                if (pin.isEmpty()) {
                    // Provide feedback for empty PIN
                    Fail fail = new Fail();
                    fail.failView("PIN cannot be empty!");
                    return;
                }

                try {
                    SQLManage man = new SQLManage();
                    ResultSet rst = man.check(cardNum, pin);
                    
                    if (rst.next()) {
                        if ("admin".equals(rst.getString("card"))) {
                            new Admin().adminView();
                        } else {
                            new Home().homeView(rst.getInt("id"));
                        }
                    } else {
                        new Fail().failView("WRONG PIN!!!");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace(); // Consider user feedback for SQL exceptions
                } finally {
                    frame.dispose();
                }
            }
        });
        //----------------------------------------
        
        frame.setVisible(true);
    }
}



