/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptowallet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import extrautils.Convert;
import java.util.Arrays;

/**
 *
 * @author msweetlove
 */
public class HashFunctions
{
    public static byte[] generateSaltValue()
    {
        SecureRandom r = new SecureRandom();
        byte[] salt = new byte[20];
        r.nextBytes(salt);
        return salt;
    }


    public static byte[] hashPassword(byte[] passwordIn,String hashType,int rounds,byte[] salt)
    {
        byte[] bytesOut=null;
        byte[] hashInput=new byte[passwordIn.length+salt.length];
        System.arraycopy(passwordIn,0,hashInput,0,passwordIn.length);
        System.arraycopy(salt,0,hashInput,passwordIn.length,salt.length);
      
        try
        {
            for(int i=0;i<rounds;i++)
            {
                MessageDigest md = MessageDigest.getInstance(hashType);
                md.update(hashInput);
                bytesOut = md.digest();
                hashInput=bytesOut;
            }
        }
        catch(NoSuchAlgorithmException nsa)
        {
            //JOptionPane.showMessageDialog(new JFrame(), nsa.getMessage());
            System.out.println(nsa);
            return null;
        }
        return bytesOut;
    }

}
