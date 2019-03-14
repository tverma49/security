import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class Q1 {

	public static void main(String[] args) {
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("DSA");
		
			SecureRandom random = new SecureRandom();
			random.setSeed(2345);
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();
			
			 // sending the data
			 Signature dsa = Signature.getInstance("SHA1withDSA");
			 dsa.initSign(privateKey);
			 byte[] sendText = "This is data".getBytes();
			 dsa.update(sendText);
			 byte[] sig = dsa.sign();
			 
			 // receiving the data and verifying
			 dsa.initVerify(publicKey);
			 dsa.update(sendText);
			 boolean verifies = dsa.verify(sig);
			 System.out.println("signature verifies: " + verifies);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}

}
