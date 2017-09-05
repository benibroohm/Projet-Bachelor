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

import View.EcranErreur;

/**
 * Classe qui g�re le chiffrement et les m�thodes sous-jacente.
 * 
 * @author Beni Broohm
 *
 */
public class CryptoUtils {
	
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static byte[] key;
    
    /**
     * Fonction principale de chiffrement : prend une cl�, la transforme en cl� secr�te, crypte un objet et le met dans
     * le fichier en param�tre.
     * @param mkey : cl�
     * @param object : objet � sauvegarder
     * @param ostream : fichier de sortie
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public static void encryptO(String mkey, Serializable object, OutputStream ostream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
         try {
        	 key = mkey.getBytes("UTF-8");
        	 key = Arrays.copyOf(key, 16); // On prend la cl� en UTF8 et on la transforme en une cl� de 16 digits.
        	 // On initialise le n�cessaire
        	 SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
         	 Cipher cipher = Cipher.getInstance(TRANSFORMATION);
         	 cipher.init(Cipher.ENCRYPT_MODE, secretKey,
         	        new IvParameterSpec(new byte[16]));
         	 SealedObject sealedObject = new SealedObject(object, cipher); // On initialise notre objet sell�
         	 ostream.flush();
         	 
            ObjectOutputStream outputStream = new ObjectOutputStream(ostream);
            outputStream.writeObject(sealedObject); // On �crit
            outputStream.flush();
            outputStream.close(); // On ferme le fichier
		} catch (IllegalBlockSizeException e) {
			new EcranErreur(e.getMessage()).setVisible(true); // Gestion d'erreur
			return;
		} catch (IOException e) {
			new EcranErreur(e.getMessage()).setVisible(true);
			return;
		}
    }
    
    public static Object decryptO(String mkey, InputStream istream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
    	// M�me gymnastique que pr�c�demment.
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
        	new EcranErreur(e.getMessage()).setVisible(true);
            return null;
        }
    }
 
}
