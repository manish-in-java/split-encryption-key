package org.example.security.keygen;

import org.example.lang.Pair;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

/**
 * Generates keys suitable for encrypting data using the {@code AES}
 * symmetric-key encryption algorithm.
 */
public class KeyGenerator
{
  private static final String ALGORITHM                    = "AES";
  private static final Object KEY_FACTORY_LOCK             = new Object();
  private static final String KEY_GENERATION_ALGORITHM     = "PBKDF2WithHmacSHA1";
  private static final int    KEY_GENERATION_ROUNDS        = 10000;
  private static final int    KEY_LENGTH                   = 128;
  private static final Object RANDOM_NUMBER_GENERATOR_LOCK = new Object();
  private static final String SALT_GENERATION_ALGORITHM    = "SHA1PRNG";

  private static SecretKeyFactory KEY_FACTORY;
  private static Random           RANDOM_NUMBER_GENERATOR;

  /**
   * Generates a random key for use with a symmetric-key encryption algorithm.
   * Uses a key-generation algorithm that takes a secret passphrase. A random
   * salt is generated to add to the passphrase for generating the key.  This
   * ensures that the same passphrase can be used for generating multiple
   * unique keys simply by changing the salt.
   *
   * @param passphrase A secret passphrase.
   * @return A {@link Pair} containing a randomly generated key and the salt
   * used for key generation.
   * @throws NullPointerException if {@code passphrase} is blank.
   */
  public Pair<Key, String> generateKey(final String passphrase)
  {
    return generateKey(passphrase, encode(generateSalt()));
  }

  /**
   * Generates a random key for use with a symmetric-key encryption algorithm.
   * Uses a key-generation algorithm that takes a secret passphrase and a salt.
   * This ensures that the same passphrase can be used for generating multiple
   * unique keys simply by changing the salt.
   *
   * @param passphrase A secret passphrase.
   * @param salt       A salt to use for generating the key.
   * @return A {@link Pair} containing a randomly generated key and the salt
   * used for key generation.
   * @throws NullPointerException if {@code passphrase} or {@code salt} is
   *                              blank.
   */
  public Pair<Key, String> generateKey(final String passphrase, final String salt)
  {
    if (passphrase == null)
    {
      throw new NullPointerException("Argument [passphrase] must not be null.");
    }

    if (salt == null)
    {
      throw new NullPointerException("Argument [salt] must not be null.");
    }

    try
    {
      // Generate a random key using the passphrase and the salt.
      final Key key = new SecretKeySpec(
          getKeyFactory().generateSecret(new PBEKeySpec(passphrase.toCharArray(), decode(salt), KEY_GENERATION_ROUNDS, KEY_LENGTH)).getEncoded()
          , ALGORITHM);

      return Pair.of(key, salt);
    }
    catch (final InvalidKeySpecException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * Encodes a {@code Base64} {@link String} into bytes.
   *
   * @param text The {@link String} to decode.
   * @return A byte array.
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
   * Gets a factory for generating the random key.
   *
   * @return A {@link SecretKeyFactory}.
   */
  private SecretKeyFactory getKeyFactory()
  {
    if (KEY_FACTORY == null)
    {
      synchronized (KEY_FACTORY_LOCK)
      {
        if (KEY_FACTORY == null)
        {
          try
          {
            KEY_FACTORY = SecretKeyFactory.getInstance(KEY_GENERATION_ALGORITHM);
          }
          catch (final NoSuchAlgorithmException e)
          {
            throw new RuntimeException(e);
          }
        }
      }
    }

    return KEY_FACTORY;
  }

  /**
   * Generates a random 64-bit salt.
   *
   * @return A randomly generated 64-bit salt.
   */
  private byte[] generateSalt()
  {
    final byte[] salt = new byte[8];

    getSaltGenerator().nextBytes(salt);

    return salt;
  }

  /**
   * Gets a pseudo-random number generator suitable for generating random
   * salt.
   *
   * @return A {@link Random} instance.
   */
  private Random getSaltGenerator()
  {
    if (RANDOM_NUMBER_GENERATOR == null)
    {
      synchronized (RANDOM_NUMBER_GENERATOR_LOCK)
      {
        if (RANDOM_NUMBER_GENERATOR == null)
        {
          try
          {
            RANDOM_NUMBER_GENERATOR = SecureRandom.getInstance(SALT_GENERATION_ALGORITHM);
          }
          catch (final NoSuchAlgorithmException e)
          {
            throw new RuntimeException(e);
          }
        }
      }
    }

    return RANDOM_NUMBER_GENERATOR;
  }
}
