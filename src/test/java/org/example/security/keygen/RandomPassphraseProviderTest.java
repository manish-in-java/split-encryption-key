package org.example.security.keygen;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link RandomPassphraseProvider}.
 */
public class RandomPassphraseProviderTest
{
  private PassphraseProvider provider = new RandomPassphraseProvider();

  /**
   * Tests that a different passphrase is obtained every time
   * {@link RandomPassphraseProvider#getPassphrase()} is invoked.
   */
  @Test
  public void testGetPassphrase()
  {
    assertNotNull(provider.getPassphrase());
    assertNotEquals(provider.getPassphrase(), provider.getPassphrase());
  }
}
