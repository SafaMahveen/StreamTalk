package StreamTalk;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.*;
import java.text.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class Server implements ActionListener{
    JTextField text;
    JPanel panel2;
    static DataOutputStream dout;
    static Box vertical=Box.createVerticalBox();
    static JFrame f=new JFrame();
    Server(){
        f.setLayout(null);
        JPanel panel=new JPanel();
        panel.setBackground(new Color(180,130,230)); 
        panel.setBounds(0,0,450,70);
        panel.setLayout(null);
        f.add(panel); 
        ImageIcon img1=new ImageIcon(ClassLoader.getSystemResource("StreamTalk/images/arrow.png"));
        Image i2=img1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        panel.add(back);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });

        ImageIcon dp=new ImageIcon(ClassLoader.getSystemResource("StreamTalk/images/Rapunzel.jpg"));
        Image i4=dp.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i5=new ImageIcon(i4);
        JLabel profile=new JLabel(i5);
        profile.setBounds(45,10,60,60);
        panel.add(profile);
        ImageIcon vid=new ImageIcon(ClassLoader.getSystemResource("StreamTalk/images/video.png"));
        Image i6=vid.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i7=new ImageIcon(i6);
        JLabel video=new JLabel(i7);
        video.setBounds(330,20,30,30);
        panel.add(video);
        ImageIcon phn=new ImageIcon(ClassLoader.getSystemResource("StreamTalk/images/phone.png"));
        Image i8=phn.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel phone=new JLabel(i9);
        phone.setBounds(400,20,30,30);
        panel.add(phone);

        JLabel name=new JLabel("RAPUNZEL");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        panel.add(name);

        JLabel status=new JLabel("Online");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.GREEN);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        panel.add(status);
         panel2=new JPanel();
        panel2.setBounds(5,75,437,520);
        f.add(panel2);

        text=new JTextField();
        text.setBounds(5,605,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);
        JButton send=new JButton("Send");
        send.setBounds(320,605,123,40);
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setBackground(new Color(180, 130, 230));
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);
        

        f.setSize(450,700);
        f.setLocation(200,5);
       f.setUndecorated(true);
       f.getContentPane().setBackground(new Color(255, 204, 51));
       f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try{
        String chat=text.getText();

        JPanel p2=formatLabel(chat,true);
       
        panel2.setLayout(new BorderLayout());
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));

        panel2.add(vertical,BorderLayout.PAGE_START);
        dout.writeUTF(chat);
        text.setText("");


        f.repaint();
        f.invalidate();
        f.validate();
        }
        catch(Exception e2){
            e2.printStackTrace();
        }
    }
    public static JPanel formatLabel(String chat, boolean isSender) {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output=new JLabel(chat);
        output.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        if (isSender)
        {
            output.setBackground(new Color(173, 216, 230)); // Light Blue for Sent Messages
            output.setBorder(new EmptyBorder(15, 15, 15, 50));
        } else {
            output.setBackground(new Color(255, 204, 102)); // Light Orange for Received Messages
            output.setBorder(new EmptyBorder(15, 15, 15, 50));
        }
        
        output.setOpaque(true);
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String args[]){
        Server server=new Server();
        try{
            ServerSocket skt=new ServerSocket(6001);
            while(true){
                Socket s= skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                 dout=new DataOutputStream(s.getOutputStream());


            
                 while (true) {
                    String msg=din.readUTF();
                    
                    JPanel panel=formatLabel(msg, false); 
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    f.validate();
                }
                
        }
    }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
