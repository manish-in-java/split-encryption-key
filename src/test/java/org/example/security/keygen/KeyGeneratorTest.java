package org.example.security.keygen;

import org.example.lang.Pair;
import org.example.security.Encrypter;
import org.junit.Test;

import java.security.Key;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link KeyGenerator}.
 */
public class KeyGeneratorTest
{
  /**
   * Tests {@link KeyGenerator#generateKey(String)}.
   */
  @Test(expected = NullPointerException.class)
  public void testGenerateKeyWithNullPassphrase()
  {
    getGenerator().generateKey(null);
  }

  /**
   * Gets a {@link KeyGenerator} to use for running the tests.
   *
   * @return A {@link KeyGenerator}.
   */
  private KeyGenerator getGenerator()
  {
    return new KeyGenerator();
  }

  /**
   * Tests {@link KeyGenerator#generateKey(String)}.
   */
  @Test
  public void testGenerateKeyWithValidPassphrase()
  {
    // Generate a key using a passphrase.
    final Pair<Key, String> pair = getGenerator().generateKey(getRandomString());

    // Ensure that the key and the salt used to generate it were obtained
    // correctly.
    assertNotNull(pair);
    assertNotNull(pair.getItem1());
    assertNotNull(pair.getItem2());
    assertNotNull(pair.getItem1().getEncoded());
    assertNotEquals(0, pair.getItem1().getEncoded().length);
    assertNotEquals(0, pair.getItem2().length());

    // Ensure that the key can be used to encrypt and decrypt data correctly.
    final Encrypter encrypter = new Encrypter(pair.getItem1());
    final String text = getRandomString();

    assertNotNull(encrypter.encrypt(text));
    assertNotNull(encrypter.decrypt(encrypter.encrypt(text)));
    assertEquals(text, encrypter.decrypt(encrypter.encrypt(text)));
  }

  /**
   * Gets a randomly generated string.
   *
   * @return A randomly generated string.
   */
  private String getRandomString()
  {
    return UUID.randomUUID().toString();
  }
}
