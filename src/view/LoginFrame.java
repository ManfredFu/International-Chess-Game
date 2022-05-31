package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoginFrame extends JFrame {
    private volatile boolean loginPressed,createPressed;
    private LoginPanel panel;
    private JLayeredPane layeredPane;
    public LoginFrame(){
        loginPressed=createPressed=false;
        panel=new LoginPanel();
        setTitle("登录");
        setSize(533,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        
        layeredPane=new JLayeredPane();
        panel.setSize(533,300);
        layeredPane.add(panel,JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel.userName.textField,JLayeredPane.MODAL_LAYER);
        layeredPane.add(panel.password.textField,JLayeredPane.MODAL_LAYER);
        layeredPane.add(panel.create,JLayeredPane.MODAL_LAYER);
        layeredPane.add(panel.login,JLayeredPane.MODAL_LAYER);
        
        setLayeredPane(layeredPane);
        setVisible(true);
    }
    public boolean getLoginPressed(){
        return loginPressed;
    }
    public boolean getCreatePressed(){
        return createPressed;
    }
    public void removeLoginPressed(){
        loginPressed=false;
    }
    public void removeCreatePressed(){
        createPressed=false;
    }
    public String getUserName(){
        if(panel.userName.textField.getForeground().equals(new Color(200,200,200))){
            return "";
        }
        return panel.userName.textField.getText();
    }
    public long getPassword(){
        if(panel.password.textField.getForeground().equals(new Color(200,200,200))){
            return -114514;
        }
        char[] raw=((JPasswordField)(panel.password.textField)).getPassword();
        long val=0;
        for(char c:raw){
            val=(val*19260817+c)%1000000007;
        }
        return val;
    }
    private class LoginPanel extends JPanel{
        BufferedImage backgroundImage;
        JTextFieldWithHint userName,password;
        JButton create,login;
        public LoginPanel(){
            try{
                backgroundImage= ImageIO.read(new File("images\\default_background_image.jpg"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            userName=new JTextFieldWithHint("用户名",new JTextField());
            userName.textField.setFont(new Font("宋体",Font.PLAIN,18));
            userName.textField.setBounds(100,25,333,50);
            
            password=new JTextFieldWithHint("密码",new JPasswordField());
            password.textField.setFont(new Font("宋体",Font.PLAIN,18));
            password.textField.setBounds(100,112,333,50);
            
            login=new JButton("登录");
            login.setFont(new Font("宋体",Font.PLAIN,18));
            login.setSize(100,50);
            login.setLocation(100,200);
            login.addActionListener(e -> {
                loginPressed=true;
            });
            
            create=new JButton("创建用户");
            create.setFont(new Font("宋体",Font.PLAIN,18));
            create.setSize(133,50);
            create.setLocation(300,200);
            create.addActionListener(e -> {
                createPressed=true;
            });
        }
        @Override
        public void paint(Graphics g){
            g.drawImage(backgroundImage,0,0,533,300,null);
        }
        private class JTextFieldWithHint implements FocusListener{
            private String hint;
            public JTextField textField;
            public JTextFieldWithHint(String hint,JTextField textField){
                this.hint=hint;
                this.textField=textField;
                this.textField.setForeground(new Color(200,200,200));
                if(this.textField instanceof JPasswordField){
                    ((JPasswordField)this.textField).setEchoChar((char)0);
                }
                this.textField.setText(hint);
                this.textField.setOpaque(false);
                this.textField.addFocusListener(this);
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                if(textField.getForeground().equals(new Color(200,200,200))){
                    if(textField instanceof JPasswordField){
                        ((JPasswordField)textField).setEchoChar('*');
                    }
                    textField.setForeground(Color.WHITE);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(textField.getText().equals("")){
                    if(textField instanceof JPasswordField){
                        ((JPasswordField)textField).setEchoChar((char)0);
                    }
                    textField.setForeground(new Color(200,200,200));
                    textField.setText(hint);
                }
            }
        }
    }
}
