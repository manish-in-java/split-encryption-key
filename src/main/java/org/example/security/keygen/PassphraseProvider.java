package org.example.security.keygen;

/**
 * Contract for providing passphrases that can be used for cryptographic
 * operations such as generation of encryption keys.
 */
public interface PassphraseProvider
{
  /**
   * Gets a passphrase.
   *
   * @return A passphrase.
   */
  String getPassphrase();
}
