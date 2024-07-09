package JavaProject;
import java.awt.Color;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();                  
        Gameplay gamePlay = new Gameplay();
        
        obj.setBounds(10, 10, 700, 600);          
        obj.setTitle("Break out Balls");
                   
        obj.setResizable(false);                   
        obj.setVisible(true);                     
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        obj.add(gamePlay);    

        ImageIcon image=new ImageIcon("logo.png");
        obj.setIconImage(image.getImage());
        obj.getContentPane().setBackground(new Color(123,50,250));
    }
}
