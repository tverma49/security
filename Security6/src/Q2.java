import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class Q2 {

	public static void main(String[] args) throws Exception {
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("DSA");
		
			SecureRandom random = new SecureRandom();
			random.setSeed(2345);
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();
			 writeToFile("\\src\\data\\public.txt", publicKey);
			
			 // sending the data
			 Signature dsa = Signature.getInstance("SHA1withDSA");
			 dsa.initSign(privateKey);
			 byte[] sendText = "This is data".getBytes();			 
			 writeToFile("\\src\\data\\text.txt", sendText);
			 
			 dsa.update(sendText);
			 byte[] sig = dsa.sign();
			 writeToFile("\\src\\data\\sign.txt", sig);
			 
			 
			 
			 // receiving the data and verifying
			 byte[] text = (byte[]) readFromFile("\\src\\data\\text.txt");
			 PublicKey publicFromFile = (PublicKey) readFromFile("\\src\\data\\public.txt");
			 dsa.initVerify(publicFromFile);
			 dsa.update(text);
			 
			 byte[] sign = (byte[]) readFromFile("\\src\\data\\sign.txt");
			 boolean verifies = dsa.verify(sign);
			 System.out.println("signature verifies: " + verifies);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}

	static void writeToFile(String filename, Object object) throws Exception {
		String localDir = System.getProperty("user.dir");
		FileOutputStream fout = new FileOutputStream(localDir+filename);
		ObjectOutputStream oout = new ObjectOutputStream(fout);
		oout.writeObject(object);
		oout.close();
	}
	
	static Object readFromFile(String filename) throws Exception {
		String localDir = System.getProperty("user.dir");
		FileInputStream fin = new FileInputStream(localDir+filename);
		ObjectInputStream oin = new ObjectInputStream(fin);
		Object object = oin.readObject();
		oin.close();
		return object;
	}
}
