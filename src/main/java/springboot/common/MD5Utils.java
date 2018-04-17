package springboot.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
@Component 
public class MD5Utils {
	@Value("${user.password.key}")
	private static String key;
	
	public String getKey() {
		return key;
	}
	@Value("${user.password.key}")
	public void setKey(String key) {
		MD5Utils.key = key;
	}

	
	public static String getPassWordMD5(String password){
		if(StringUtils.isEmpty(password))
    		return null;
		return getMD5(password + key + password);
		
	}

	/**
     * 获取String的MD5值
     *
     * @param info 字符串
     * @return 该字符串的MD5值
     */
    @SuppressWarnings("unused")
    public static String getMD5(String info) {
    	if(StringUtils.isEmpty(info))
    		return null;
        try {
            //获取 MessageDigest 对象，参数为 MD5 字符串，表示这是一个 MD5 算法（其他还有 SHA1 算法等）：
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] md5Array = md5.digest();
            return bytesToHex1(md5Array);
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String bytesToHex1(byte[] md5Array) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < md5Array.length; i++) {
            int temp = 0xff & md5Array[i];
            String hexString = Integer.toHexString(temp);
            if (hexString.length() == 1) {//如果是十六进制的0f，默认只显示f，此时要补上0
                strBuilder.append("0").append(hexString);
            } else {
                strBuilder.append(hexString);
            }
        }
        return strBuilder.toString();
    }

    //通过java提供的BigInteger 完成byte->HexString
    @SuppressWarnings("unused")
    public static String bytesToHex2(byte[] md5Array) {
        BigInteger bigInt = new BigInteger(1, md5Array);
        return bigInt.toString(16);
    }

    //通过位运算 将字节数组到十六进制的转换
    public static String bytesToHex3(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
}
