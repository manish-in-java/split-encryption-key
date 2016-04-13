package org.example.security.keygen;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Provides a random passphrase every time it is invoked.  The passphrase is
 * guaranteed to be cryptographically secure, that is, it cannot be guessed.
 */
public class RandomPassphraseProvider implements PassphraseProvider
{
  private static final Random RANDOM = new SecureRandom();

  /**
   * {@inheritDoc}
   */
  public String getPassphrase()
  {
    return new BigInteger(512, RANDOM).toString(16);
  }
}
