package org.example.lang;

/**
 * Represents a pair of items.
 */
public class Pair<T, U>
{
  private final T item1;
  private final U item2;

  /**
   * Sets the items in the pair.
   *
   * @param item1 The first item in the pair.
   * @param item2 The second item in the pair.
   */
  private Pair(final T item1, final U item2)
  {
    this.item1 = item1;
    this.item2 = item2;
  }

  /**
   * Creates a pair of two items.
   *
   * @param item1 The first item in the pair.
   * @param item2 The second item in the pair.
   * @param <T>   The type of the first item.
   * @param <U>   The type of the second item.
   * @return A {@link Pair} of the specified items.
   */
  public static <T, U> Pair<T, U> of(final T item1, final U item2)
  {
    return new Pair<>(item1, item2);
  }

  /**
   * Gets the first item in the pair.
   *
   * @return The first item in the pair.
   */
  public final T getItem1()
  {
    return this.item1;
  }

  /**
   * Gets the second item in the pair.
   *
   * @return The second item in the pair.
   */
  public final U getItem2()
  {
    return this.item2;
  }

  /**
   * {@inheritDoc}
   */
  public boolean equals(final Object obj)
  {
    if (this == obj)
    {
      return true;
    }

    if (obj == null || !(obj instanceof Pair))
    {
      return false;
    }

    final Pair<?, ?> that = (Pair) obj;

    return this.item1 != null && that.item1 != null && this.item1.equals(that.item1)
        && this.item2 != null && that.item2 != null && this.item2.equals(that.item2);
  }

  /**
   * {@inheritDoc}
   */
  public int hashCode()
  {
    return (item1 != null
        ? item1.hashCode()
        : super.hashCode())
        + 29 * (item2 != null
        ? item2.hashCode()
        : 0);
  }

  /**
   * {@inheritDoc}
   */
  public String toString()
  {
    return String.format("(%s, %s)"
        , item1 != null ? item1.toString() : ""
        , item2 != null ? item2.toString() : "");
  }
}
