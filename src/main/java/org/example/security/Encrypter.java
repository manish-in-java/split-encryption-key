package org.example.security;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Base64;

/**
 * Encrypts (and decrypts) {@link String}s using the {@code AES} symmetric-key
 * encryption algorithm.
 */
public class Encrypter
{
  private static final String ALGORITHM = "AES";

  private Cipher cipher;
  private Key    key;

  /**
   * Sets the key to use for encryption and decryption.
   *
   * @param key The key to use.
   */
  public Encrypter(final Key key)
  {
    this.key = key;
  }

  /**
   * Decrypts a {@link String}.
   *
   * @param text The {@link String} to decrypt.
   * @return The decrypted {@link String}.
   */
  public String decrypt(final String text)
  {
    String result = text;

    if (text != null)
    {
      final Cipher cipher = getCipher();

      try
      {
        cipher.init(Cipher.DECRYPT_MODE, key);

        result = new String(cipher.doFinal(decode(text)));
      }
      catch (final Exception e)
      {
        throw new RuntimeException(e);
      }
    }

    return result;
  }

  /**
   * Encrypts a {@link String}.
   *
   * @param text The {@link String} to encrypt.
   * @return An encrypted {@link String}.
   */
  public String encrypt(final String text)
  {
    String result = text;

    if (text != null)
    {
      final Cipher cipher = getCipher();

      try
      {
        cipher.init(Cipher.ENCRYPT_MODE, this.key);

        result = encode(cipher.doFinal(text.getBytes()));
      }
      catch (final Exception e)
      {
        throw new RuntimeException(e);
      }
    }

    return result;
  }

  /**
   * Decodes a {@link String} containing bytes in {@code Base64}
   * representation into raw bytes.
   *
   * @param text The {@link String} to decode.
   * @return An array of bytes.
   */
  private byte[] decode(final String text)
  {
    return Base64.getDecoder().decode(text);
  }

  /**
   * Encodes bytes into a {@link String} using their {@code Base64}
   * representation.
   *
   * @param bytes The bytes to encode.
   * @return A {@link String} containing the {@code Base64} representation
   * of the bytes.
   */
  private String encode(final byte[] bytes)
  {
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * Gets the Java cryptography cipher to use.
   *
   * @return A {@link Cipher}.
   */
  private Cipher getCipher()
  {
    if (cipher == null)
    {
      try
      {
        cipher = Cipher.getInstance(this.getCipherName());
      }
      catch (final Exception e)
      {
        throw new RuntimeException(e);
      }
    }

    return cipher;
  }

  /**
   * Gets the Java cryptography extensions security provider transformation
   * name to use for this obfuscator. This could be something like <b>AES</b>,
   * <b>Blowfish/CBC/NoPadding</b>, <b>DES/None/PKCSPadding</b>, etc.
   *
   * @return A Java cryptography extensions security provider transformation
   * name.
   */
  private String getCipherName()
  {
    return ALGORITHM;
  }
}
