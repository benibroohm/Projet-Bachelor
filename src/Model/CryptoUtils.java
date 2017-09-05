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
 * Classe qui gère le chiffrement et les méthodes sous-jacente.
 * 
 * @author Beni Broohm
 *
 */
public class CryptoUtils {
	
	private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static byte[] key;
    
    /**
     * Fonction principale de chiffrement : prend une clé, la transforme en clé secrète, crypte un objet et le met dans
     * le fichier en paramètre.
     * @param mkey : clé
     * @param object : objet à sauvegarder
     * @param ostream : fichier de sortie
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     */
    public static void encryptO(String mkey, Serializable object, OutputStream ostream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
         try {
        	 key = mkey.getBytes("UTF-8");
        	 key = Arrays.copyOf(key, 16); // On prend la clé en UTF8 et on la transforme en une clé de 16 digits.
        	 // On initialise le nécessaire
        	 SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
         	 Cipher cipher = Cipher.getInstance(TRANSFORMATION);
         	 cipher.init(Cipher.ENCRYPT_MODE, secretKey,
         	        new IvParameterSpec(new byte[16]));
         	 SealedObject sealedObject = new SealedObject(object, cipher); // On initialise notre objet sellé
         	 ostream.flush();
         	 
            ObjectOutputStream outputStream = new ObjectOutputStream(ostream);
            outputStream.writeObject(sealedObject); // On écrit
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
    	// Même gymnastique que précédemment.
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
