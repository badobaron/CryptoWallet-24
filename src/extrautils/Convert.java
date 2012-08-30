/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package extrautils;

import java.math.BigInteger;


/**
 *
 * @author msweetlove
 */
public class Convert
{

    /** Creates a new instance of Convert */
    public Convert()
    {
    }

    public static byte[] stringToHexBytes(String s1){
      int arrayLength;

      // calculate what the array length should
      if(s1.length()%2==0){    
          arrayLength = s1.length() / 2;
      }    
      else{    
          arrayLength = 1+(s1.length() / 2);
      }    

     byte[] hexBytes = new BigInteger(s1, 16).toByteArray(); // convert to bytes

     if(hexBytes.length > arrayLength){
         // correct the array by removing the first byte which will be 0x00
         byte[] modHexBytes = new byte[arrayLength];
         for(int i=0;i<arrayLength;i++)
         {
             modHexBytes[i] = hexBytes[i+1];
         }    
         hexBytes = modHexBytes;
     }
     else if (hexBytes.length < arrayLength){
        // pad with 0x00
        byte [] temp = new byte[arrayLength];
        for(int x=0;x<arrayLength;x++){    
            temp[x] = 0;
        }  
            
        System.arraycopy(hexBytes,0,temp,arrayLength-hexBytes.length,hexBytes.length);
        hexBytes = temp;
     }

      return hexBytes;
    }


    public static byte[] stringToBytes(String dataIn){
        byte[] bytes = new byte[dataIn.length()];
        for(int i=0;i<dataIn.length();i++){
            bytes[i]=(byte)dataIn.charAt(i);
        }

        return bytes;
    }


    public static String bytesToHexString(byte[] bytesIn){
        String hexByteStr;
        String hexOut = new String();
        for(int i=0;i<bytesIn.length;i++){
            hexByteStr = Integer.toHexString(bytesIn[i]&0xFF);
            if(hexByteStr.length()==1){
                hexByteStr = "0"+hexByteStr; // prepend '0'
            }

            hexOut += hexByteStr;
        }

        return hexOut.toUpperCase();
    }


    public static String bytesToString(byte[] bytesIn){
        String s1="";

        for(int i=0;i<bytesIn.length;i++){
            s1 += (char)bytesIn[i];
        }

        return s1;
    }

    public static byte[] oddParity(byte[] bytesIn){
        //byte[] cBytes = new byte[bytesIn.length];
        byte test, mask, bitsSet;

        mask=1;
        bitsSet=0;
        for(int n=0; n<bytesIn.length;n++){
            // count the number of bits set
            for(int bitNum=0;bitNum<8;bitNum++){
                if((bytesIn[n] & mask) == 1){
                    bitsSet++;
                }    
                mask <<= 1;
            }

            if(bitsSet % 2 == 0){
                bytesIn[n] ^= 1; // toggle parity bit if number of bits is even
            }        
            bitsSet=0;
            mask=1;
        }

        return bytesIn;
    }
    
    public static byte[] charArrayToBytes(char[] charsIn){
        byte[] bytesOut = new byte[charsIn.length];
        for(int i=0;i<charsIn.length;i++){
            bytesOut[i]=(byte)charsIn[i];
        }
        return bytesOut;
    }
}
