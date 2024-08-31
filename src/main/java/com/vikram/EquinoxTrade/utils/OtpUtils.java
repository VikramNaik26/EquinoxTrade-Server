package com.vikram.EquinoxTrade.utils;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * OtpUtils
 */
public class OtpUtils {
  private static final int OTP_LENGTH = 6;
  private static final String HMAC_ALGO = "HmacSHA256";
  private static final byte[] SECRET_KEY = "YourSecretKeyHere".getBytes();
  private static final int TIME_STEP = 60;

  public static String generateOtp() throws NoSuchAlgorithmException, InvalidKeyException {
    long timestamp = new Date().getTime() / (TIME_STEP * 1000);
    byte[] timeBytes = ByteBuffer.allocate(8).putLong(timestamp).array();

    Mac hmac = Mac.getInstance(HMAC_ALGO);
    SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, HMAC_ALGO);
    hmac.init(keySpec);
    byte[] hmacResult = hmac.doFinal(timeBytes);

    int offset = hmacResult[hmacResult.length - 1] & 0xf;
    long truncatedHash = ByteBuffer.wrap(hmacResult, offset, 4).getInt() & 0x7FFFFFFF;

    long otp = truncatedHash % (long) Math.pow(10, OTP_LENGTH);

    return String.format("%0" + OTP_LENGTH + "d", otp);
  }

}
