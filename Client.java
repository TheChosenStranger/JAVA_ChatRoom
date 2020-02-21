/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6_2;

import java.net.*; 
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 *
 * @author Moustafa Ghareeb
 */
public class Client  extends JFrame {

    public Socket clientSocket;
    public DataInputStream inputData ;
    public PrintStream outputData;
    JTextArea ta;
    JScrollPane scroll;  
    JTextField tf,tf1,tf3;
    JButton sendButton ;
    JLabel lab2 ;
    String temp;
    String typ;
    static String cName = new String();
    
    public static void main(String[] args)
    {       
        new Client();
    }
    public Client()
    {
        //new StartClient();
        
           try
            {
            clientSocket = new Socket("127.0.0.1", 4000);
            inputData = new DataInputStream(clientSocket.getInputStream ());
            outputData = new PrintStream(clientSocket.getOutputStream ());
            StartGUI();
            tf1.setText("User");
            sendButton.addActionListener(new ActionListener(){  //Send press
            public void actionPerformed(ActionEvent ae){
                    temp = tf.getText();
                    tf.setText("");
                    cName = tf1.getText();
                    typ = "";
                    outputData.println(cName+":  "+temp );
            }
            }); 
            
            tf.addKeyListener(new KeyListener()
                {
                    public void keyPressed (KeyEvent e){
                        if (e.getKeyCode()== KeyEvent.VK_ENTER) {
                            temp = tf.getText();
                            tf.setText("");
                            cName = tf1.getText();
                            typ = "";
                            outputData.println(cName+":  "+temp );
                        }
                    }
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyCode()!= KeyEvent.VK_ENTER) {
                        typ = "Someone is typing";
                        outputData.println(typ);
                    }
                }

                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode()== KeyEvent.VK_ENTER) {
                        typ = "";
                        outputData.println(typ);
                    }
                }
                });
            }
        catch(IOException ex)
            {;}     
        
         new Thread (new Runnable(){
        
            public void run()
            {   
                String s = new String() ;
                while(true){   
                    typ ="";
                    try {s = inputData.readLine();}
                    catch (IOException ex) {System.out.print("");}
                    if(s.equals(""))
                    {
                        lab2.setText("");
                    }
                     if(s.equals(cName+":  "+temp))
                    {   
                        ta.append("ME:  "+temp+"\n");
                        lab2.setText("");
                    }
                     else if(s.equals("Someone is typing"))
                    {
                        lab2.setText("Someone is typing");
                    }
                    else if (!s.equals("") && !(s.equals("Someone is typing")))
                    {
                        ta.append(s+"\n");
                        lab2.setText("");
                    }
                    
                   
                    
                }
            }
            }).start();
        
    }
    
    void StartGUI(){
        /////////////GUI////////////////////////////
            this.setLayout(new FlowLayout());
            ta=new JTextArea(24,40);          //All msg
            scroll=new JScrollPane(ta);     //Scroll
            scroll.setViewportView(ta);
            JLabel lab = new JLabel("Name:");
            tf=new JTextField(18);           //Sending msg
            JLabel lab1 = new JLabel("Message:");
            tf1=new JTextField(8);           //Sending msg
            lab2 = new JLabel("");
            sendButton = new JButton(">");       //Send button
            add(scroll);
            add(lab);
            add(tf1);
            add(lab1);
            add(tf);
            add(sendButton);
            add(lab2);
            
            this.setSize(500, 500);
            this.setVisible(true);
        //////////////////////////////////////////////
        }
        
 }

