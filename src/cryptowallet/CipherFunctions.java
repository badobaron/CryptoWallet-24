/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptowallet;
import extrautils.Convert;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 *
 * @author msweetlove
 */
public class CipherFunctions
{
    private Cipher cipher;
    private SecretKey secretKey;
    private String cipherAlg;
    private IvParameterSpec iv;

    public CipherFunctions(String cipherAlg)
    {
        try
        {
            cipher = Cipher.getInstance(cipherAlg+"/CBC/PKCS5Padding");
            //cipher = Cipher.getInstance(cipherAlg+"/CBC/NoPadding");
            this.cipherAlg = cipherAlg;
            this.iv = new IvParameterSpec(new byte[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00});
        }
        catch (NoSuchAlgorithmException nsa)
        {
            System.out.println(nsa);
        }
        catch(NoSuchPaddingException nsp)
        {
            System.out.println(nsp);
        }
    }
    
    
    public String decryptText(String encHexStringIn) throws Exception
    {
        //System.out.println("secret decryption key = "+Convert.bytesToHexString(this.secretKey.getEncoded()));
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey,this.iv);
        return Convert.bytesToString(this.cipher.doFinal(Convert.stringToHexBytes(encHexStringIn)));
    }        
    
    public String encryptText(String plainTextIn) throws Exception
    {
        //System.out.println("secret encryption key = "+Convert.bytesToHexString(this.secretKey.getEncoded()));
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey,this.iv);
        String encryptedText=Convert.bytesToHexString(this.cipher.doFinal(plainTextIn.getBytes()));
        this.iv = new IvParameterSpec(this.cipher.getIV());
        return encryptedText;
    }  

    public void setKey(String password, String salt) throws Exception
    {       
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1000, 128);  
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");    
        this.secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), this.cipherAlg);  
    }
}
