import view.ChessGameFrame;
import view.LoginFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static boolean userNameValidity(String userName){
        for(int i=0;i<userName.length();i++){
            char c=userName.charAt(i);
            if(!((c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z')||c=='_')){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        LoginFrame loginFrame=new LoginFrame();
        File users=new File("users");
        if(!users.exists()){
            users.mkdirs();
        }
        String userName= "";
        long password=0;
        while(true){
            while(!(loginFrame.getLoginPressed()|| loginFrame.getCreatePressed()));
            if(loginFrame.getLoginPressed()){
                loginFrame.removeLoginPressed();
                userName=loginFrame.getUserName();
                password=loginFrame.getPassword();
                if(userName.equals("")){
                    JOptionPane.showMessageDialog(null,"用户名不能为空！");
                }
                else if(!userNameValidity(userName)){
                    JOptionPane.showMessageDialog(null,"用户名只能包含字母、数字和下划线");
                }
                else if(password==-114514){
                    JOptionPane.showMessageDialog(null,"密码不能为空！");
                }
                else{
                    File user=new File("users\\"+userName+".txt");
                    if(!user.exists()){
                        JOptionPane.showMessageDialog(null,"用户不存在！");
                    }
                    else{
                        try {
                            Scanner sc=new Scanner(user);
                            long std=sc.nextLong();
                            if(std!=password){
                                JOptionPane.showMessageDialog(null,"密码错误！");
                            }
                            else{
                                break;
                            }
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            else{
                loginFrame.removeCreatePressed();
                userName=loginFrame.getUserName();
                password=loginFrame.getPassword();
                if(userName.equals("")){
                    JOptionPane.showMessageDialog(null,"密码不能为空！");
                }
                else if(!userNameValidity(userName)){
                    JOptionPane.showMessageDialog(null,"用户名只能包含字母、数字和下划线");
                }
                else if(password==-114514){
                    JOptionPane.showMessageDialog(null,"密码不能为空！");
                }
                else{
                    File user=new File("users\\"+userName+".txt");
                    if(user.exists()){
                        JOptionPane.showMessageDialog(null,"用户已存在！");
                    }
                    else{
                        try {
                            user.createNewFile();
                            FileOutputStream fos=new FileOutputStream("users\\"+userName+".txt");
                            fos.write(String.valueOf(password).getBytes());
                            fos.flush();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
        mainFrame.setVisible(true);
    }
}
