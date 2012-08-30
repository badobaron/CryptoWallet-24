/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptowallet;

import java.io.*;

/**
 *
 * @author marc
 */
public class CryptoWalletDB {
    
    public static boolean writeNewBlankDatabase(){
        //use cryptowallet_group as the template
        
        return true;
        
    }
    
    public static boolean writeHashFile(String hash, String salt){
        
        //File hashFile = new File("/home/"+System.getProperty("user.name")+"/.cryptowallet/hash.txt");
        try{
            FileWriter fwriter = new FileWriter("/home/"+System.getProperty("user.name")+"/.cryptowallet/hash.txt");
            BufferedWriter writer = new BufferedWriter(fwriter);
            writer.write(hash+"\n");
            writer.write(salt+"\n");
            writer.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
        return true;
    }
            
            
    
}
