package org.example.security.keygen;

/**
 * Provides a fixed passphrase every time it is invoked.  The passphrase is
 * guaranteed to be cryptographically secure, that is, it cannot be guessed.
 */
public class FixedPassphraseProvider extends RandomPassphraseProvider
{
  private static final Object LOCK = new Object();

  private String passphrase;

  /**
   * {@inheritDoc}
   */
  public String getPassphrase()
  {
    if (passphrase == null)
    {
      synchronized (LOCK)
      {
        if (passphrase == null)
        {
          passphrase = super.getPassphrase();
        }
      }
    }

    return passphrase;
  }
}
