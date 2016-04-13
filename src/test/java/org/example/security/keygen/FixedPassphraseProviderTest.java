package org.example.security.keygen;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link FixedPassphraseProvider}.
 */
public class FixedPassphraseProviderTest
{
  private PassphraseProvider provider = new FixedPassphraseProvider();

  /**
   * Tests that the same passphrase is obtained every time
   * {@link FixedPassphraseProvider#getPassphrase()} is invoked.
   */
  @Test
  public void testGetPassphrase()
  {
    assertNotNull(provider.getPassphrase());
    assertEquals(provider.getPassphrase(), provider.getPassphrase());
  }
}
