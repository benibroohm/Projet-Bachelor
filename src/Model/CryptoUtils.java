package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
	
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static byte[] key;
    
    public static void encryptO(String mkey, Serializable object, OutputStream ostream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
         try {
        	 key = mkey.getBytes("UTF-8");
        	 key = Arrays.copyOf(key, 16);
        	 SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
         	 Cipher cipher = Cipher.getInstance(TRANSFORMATION);
         	 cipher.init(Cipher.ENCRYPT_MODE, secretKey,
         	        new IvParameterSpec(new byte[16]));
         	 SealedObject sealedObject = new SealedObject(object, cipher);
         	 ostream.flush();
         	 
            ObjectOutputStream outputStream = new ObjectOutputStream(ostream);
            outputStream.writeObject(sealedObject);
            outputStream.flush();
            outputStream.close();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static Object decryptO(String mkey, InputStream istream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
    	key = mkey.getBytes("UTF-8");
   	 	key = Arrays.copyOf(key, 16);
   	 	SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
   	 	Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    	cipher.init(Cipher.DECRYPT_MODE, secretKey,
    	        new IvParameterSpec(new byte[16]));

    	ObjectInputStream inputStream = new ObjectInputStream(istream);
        SealedObject sealedObject;
        try {
            sealedObject = (SealedObject) inputStream.readObject();
            return sealedObject.getObject(cipher);
        } catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
 
}
