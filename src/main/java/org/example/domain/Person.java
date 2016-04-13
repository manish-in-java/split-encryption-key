package org.example.domain;

import org.example.security.Encrypter;
import org.example.security.keygen.FixedPassphraseProvider;
import org.example.security.keygen.KeyGenerator;
import org.example.security.keygen.PassphraseProvider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Represents a person.
 */
@Entity
@Table(name = "person")
public class Person extends Model
{
  private static final KeyGenerator       KEY_GENERATOR       = new KeyGenerator();
  private static final PassphraseProvider PASSPHRASE_PROVIDER = new FixedPassphraseProvider();

  @Column(length = 50, name = "first_name")
  @NotNull
  private String firstName;

  @Column(length = 50, name = "last_name")
  @NotNull
  private String lastName;

  @Column(length = 1000, name = "secret")
  @NotNull
  private String secret;

  @Column(length = 1000, name = "social_benefits_number")
  @NotNull
  private String socialBenefitsNumber;

  @Transient
  private transient Encrypter encrypter;

  /**
   * Deliberately hidden to prevent direct instantiation.
   */
  Person()
  {
    super();
  }

  /**
   * Creates a person with specified first and last names.
   *
   * @param firstName            The person's first name.
   * @param lastName             The person's last name.
   * @param socialBenefitsNumber The person's social benefits number, such as
   *                             a {@code US Social Security Number}.
   */
  public Person(final String firstName, final String lastName, final String socialBenefitsNumber)
  {
    this();

    setFirstName(firstName);
    setLastName(lastName);
    setSocialBenefitsNumber(socialBenefitsNumber);
  }

  /**
   * Gets the person's first name.
   *
   * @return The person's first name.
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * Gets the person's last name.
   *
   * @return The person's last name.
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * Gets the person's social benefits number, such as a
   * {@code US Social Security Number}.
   *
   * @return The person's social benefits number, such as a
   * {@code US Social Security Number}.
   */
  public String getSocialBenefitsNumber()
  {
    return getEncrypter().decrypt(socialBenefitsNumber);
  }

  /**
   * Sets the person's first name.
   *
   * @param firstName The person's first name.
   */
  public void setFirstName(final String firstName)
  {
    this.firstName = firstName;
  }

  /**
   * Sets the person's last name.
   *
   * @param lastName The person's last name.
   */
  public void setLastName(final String lastName)
  {
    this.lastName = lastName;
  }

  /**
   * Sets the person's social benefits number, such as a
   * {@code US Social Security Number}.
   *
   * @param socialBenefitsNumber The person's social benefits number, such as
   *                             a {@code US Social Security Number}.
   */
  public void setSocialBenefitsNumber(String socialBenefitsNumber)
  {
    this.socialBenefitsNumber = getEncrypter().encrypt(socialBenefitsNumber);
  }

  /**
   * Gets an encrypter that can encrypt and decrypt sensitive personal
   * information.
   *
   * @return An encrypter that can encrypt and decrypt sensitive personal
   * information.
   */
  private Encrypter getEncrypter()
  {
    return new Encrypter(KEY_GENERATOR.generateKey(getPassphrase(), getSecret()).getItem1());
  }

  /**
   * Gets a passphrase to use for generating the encryption key required for
   * encrypting and decrypting sensitive personal information.
   *
   * @return A passphrase to use for generating the encryption key required for
   * encrypting and decrypting sensitive personal information.
   */
  private String getPassphrase()
  {
    return PASSPHRASE_PROVIDER.getPassphrase();
  }

  /**
   * Gets a secret that is specific to this person and not shared with any
   * other person.  The secret is guaranteed to be cryptographically strong,
   * that is, it cannot be guessed.
   *
   * @return A secret that is specific to this person.
   */
  private String getSecret()
  {
    if (secret == null)
    {
      secret = KEY_GENERATOR.generateKey(getPassphrase()).getItem2();
    }

    return secret;
  }
}
