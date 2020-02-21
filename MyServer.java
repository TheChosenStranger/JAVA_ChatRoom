/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6_1;

import java.net.*; 
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.Enumeration; 

/**
 *
 * @author Moustafa Ghareeb
 */
public class MyServer {
    
    ServerSocket serverSocket;
    Socket soc; 
    String str = new String() ;
    static ArrayList<StartChat> clientsQueue = new ArrayList<StartChat>();
    
     public static void main(String[] args) {
        
         new MyServer();
    }
     
        public MyServer()
        {
            try
            {
                serverSocket = new ServerSocket(4000);
                while(true)
                {   
                    Socket soc = serverSocket.accept();
                    new StartChat(soc);
                }
            }
            catch(IOException ex) {;}
        }
        
        public class StartChat extends Thread
        {
            DataInputStream inputData ;
            PrintStream outputData;
                        
            StartChat(Socket mysoc)
            {       
                try
                {
                    start();
                    inputData = new DataInputStream(mysoc.getInputStream());
                    outputData = new PrintStream(mysoc.getOutputStream());
                    clientsQueue.add(this);
                    
                }
                catch(IOException ex)
                {;}
            }
            
            public void run()
            {
                while(true)
                {
                    try     {str = inputData.readLine();}
                    catch(Exception ex) {;}
                     for(StartChat msg : clientsQueue)
                        {msg.outputData.println(str);}
                }
            }
        }

}

