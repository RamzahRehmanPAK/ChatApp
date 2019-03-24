package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;

/**
 * Created by Ramzah Rehman on 11/17/2016.
 */
 class MyBoolean{
        boolean isMessage;
        }
public class Client extends WindowAdapter implements ActionListener {

    JFrame mainFrame;
    JButton Register;
    JTextArea chatBox;
    JTextField msgField;
    JButton Disconnect;
    JTextField name;
    JScrollPane chatBoxScrollPane;
    JPanel panel;
    ServerConnection send;
    ServerConnection receive;
    MyBoolean message;
    String text;

    public Client(){

        mainFrame=new JFrame("Chat");
        Register=new JButton("Register");
        chatBox=new JTextArea();
        msgField=new JTextField();
        Disconnect=new JButton("Disconnect");
        name=new JTextField();
        chatBoxScrollPane = new JScrollPane(chatBox);
        panel=new JPanel();
        send=null;
        receive=null;

        message=new MyBoolean();
        message.isMessage=false;

    }
    public void setView(){

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mainFrame.setBounds((int) toolkit.getScreenSize().getWidth()/8 , (int) toolkit.getScreenSize().getHeight()/8, (int) toolkit.getScreenSize().getWidth()*7/10 , (int) toolkit.getScreenSize().getHeight()*7/10 );

        Dimension d=new Dimension(100,30);
        name.setPreferredSize(d);

        Register.setPreferredSize(d);
        Disconnect.setPreferredSize(d);

        msgField.setPreferredSize(new Dimension(800,50));


        //chatBoxScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //chatBoxScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatBox.setEditable(false);

        GridBagLayout layout=new GridBagLayout();

        panel.setLayout(layout);

        GridBagConstraints gbc=new GridBagConstraints();

        gbc.insets=new Insets(0,0,0,10);

        gbc.gridx=0;
        gbc.gridy=0;

        panel.add(name,gbc);

        gbc.gridx=1;
        gbc.gridy=0;

        panel.add(Register,gbc);

        gbc.gridx=2;
        gbc.gridy=0;

        panel.add(Disconnect,gbc);


        gbc.insets=new Insets(20,0,0,0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(chatBoxScrollPane, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(msgField, gbc);

        mainFrame.setContentPane(panel);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        msgField.addActionListener(this);
        Register.addActionListener(this);
        mainFrame.addWindowListener(this);
        Disconnect.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==msgField){

            if(send==null && receive==null){
                JOptionPane.showMessageDialog(mainFrame, "You are not registered!");
                msgField.setText("");
            }
            else{

                 text = msgField.getText();

                if(!text.isEmpty() && (text.trim().length() > 0) ){


                    synchronized (message){

                        message.isMessage=true;
                        message.notify();
                    }

                    msgField.setText("");
                }
            }
        }
        if(e.getSource()==Register){

            if(send!=null && receive!=null){
                JOptionPane.showMessageDialog(mainFrame, "You are already registered!");
            }
            else{
                if(!name.getText().isEmpty() && (name.getText().trim().length() > 0)){

                    try{

                        ServerConnection.client=this;

                        ServerConnection temp=new SocketServerConnection(0);//0 for send 1 for receive
                        temp.register(name.getText());

                        send=temp;
                        receive=new SocketServerConnection(1);


                        SocketServerConnection.running=true;
                        send.start();
                        receive.start();

                    }
                    catch(Exception p){
                        p.printStackTrace();
                    }

                }
                else{
                    JOptionPane.showMessageDialog(mainFrame, "Please enter name first!");
                }
            }

        }

        if(e.getSource()==Disconnect){

            if(send==null && receive==null){
                JOptionPane.showMessageDialog(mainFrame, "You should first be connected to disconnect!");
            }
            else{

                msgField.setText("");
                text="@@#DISCONNECT#";

                synchronized (message){
                    message.isMessage=true;
                    message.notify();
                }
                send=null;
                receive=null;

                JOptionPane.showMessageDialog(mainFrame, "You have disconnect!");

            }
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(send!=null && receive!=null){

            text="@@#DISCONNECT#";

            synchronized (message){
                message.isMessage=true;
                message.notify();
            }
        }
    }
}
