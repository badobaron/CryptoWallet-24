/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptowallet;

import java.io.*;
import java.util.Arrays;
//import org.json.JSONArray;


//import java.nio.file.Files;

/**
 *
 * @author marc
 */
public class CryptoWalletDB {
    
    private CipherFunctions crypto;
    
    public CryptoWalletDB(char[] password,byte[] salt) throws Exception{
        crypto=new CipherFunctions("AES");
        crypto.setKey(password,salt);
    }
    
    
    public boolean writeNewBlankDatabase() throws IOException{
        //use cryptowallet_template as the basis for the new database
        FileReader reader = new FileReader("/home/"+System.getProperty("user.name")+
                "/.cryptowallet/cryptowallet_template");
        FileWriter writer = new FileWriter("/home/"+System.getProperty("user.name")+
                "/.cryptowallet/cryptowalletdb");
        BufferedReader bufReader = new BufferedReader(reader);
        BufferedWriter bufWriter = new BufferedWriter(writer);
        String line;
        while((line=bufReader.readLine())!=null){
            //encrypt the line
            try{
                String encLine = crypto.encryptText(line);
                bufWriter.write(encLine+"\r");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                return false;
            }
                
        }
        bufReader.close();
        bufWriter.close();

        return true;
        
    }
    
    public static boolean writeHashFile(String hash, String salt){
        
        //File hashFile = new File("/home/"+System.getProperty("user.name")+"/.cryptowallet/hash.txt");
        try{
            FileWriter fwriter = new FileWriter("/home/"+System.getProperty("user.name")+
                "/.cryptowallet/cryptowallet_hash.txt");
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
