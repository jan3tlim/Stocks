import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * cleint.
 */
public class ClientTest {
  private Client client;

  /**
   * set up the client list.
   */
  @Before
  public void setUp() {
    client = new Client("Janet Lim");
  }

  /**
   * set up the client list.
   */
  @Test
  public void testGetPortfolios() {
    client.createPortfolio("Tech Portfolio");
    assertTrue(client.getPortfolios().containsKey("Tech Portfolio"));
  }

  /**
   * test creating a portfolio.
   */
  @Test
  public void testCreatePortfolio() {
    assertEquals(new Portfolio("Tech Portfolio", client),
            client.createPortfolio("Tech Portfolio"));
  }


}