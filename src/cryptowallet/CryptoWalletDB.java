/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptowallet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
    
    public static class PWCredentials{
        public String hash;
        public String salt;
    }

    
    //database is JSON so we return a json object
    public JSONObject decryptDatabase()throws Exception{
        FileReader reader = new FileReader("/home/"+System.getProperty("user.name")+
                "/.cryptowallet/cryptowalletdb");
        BufferedReader bufReader = new BufferedReader(reader);
        String line;
        StringBuilder decryptedText=new StringBuilder("");
        while((line = bufReader.readLine())!=null){
            decryptedText.append(crypto.decryptText(line.trim()));
        }
        
        bufReader.close();
        
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject)parser.parse(decryptedText.toString());
        return jsonObj;
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
                "/.cryptowallet/cryptowallet_hash");
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
    
    
    public static PWCredentials readHashAndSalt(){
        PWCredentials cred = new PWCredentials();
        
        //open the hash file
        try{
            FileReader freader = new FileReader("/home/"+System.getProperty("user.name")+
                "/.cryptowallet/cryptowallet_hash");
            BufferedReader reader = new BufferedReader(freader);
            //first line is has 2nd line is salt
            cred.hash = reader.readLine();
            cred.salt = reader.readLine();
            reader.close();
        }
        catch(Exception e){
            cred.hash="";
            cred.salt="";
            System.out.println(e.getMessage());
        }
        return cred;
    }
            
            
            
    
}
