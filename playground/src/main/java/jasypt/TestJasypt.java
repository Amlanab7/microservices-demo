package jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

public class TestJasypt {
    public static void main(String[] args) {

        StandardPBEStringEncryptor standardPBESStringEncryptor = new StandardPBEStringEncryptor();
        standardPBESStringEncryptor.setPassword("password");
        standardPBESStringEncryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        standardPBESStringEncryptor.setIvGenerator(new RandomIvGenerator());
        String result=standardPBESStringEncryptor.encrypt("springCloud_Pass");
        System.out.println(result);
        System.out.println(standardPBESStringEncryptor.decrypt(result));
    }
}
