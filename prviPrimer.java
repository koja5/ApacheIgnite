import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
public class prviPrimer {
  public static void main(String[] args) throws IgniteException {
    try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
      // Put values in cache.
      IgniteCache cache = ignite.getOrCreateCache("mojKes");
      cache.put(1, "Hello");
      cache.put(2, "World!");
      // Uzima vrednost iz keša
      // Prosleđuje "Hello, World" svim cvorovima u klasteru.
      ignite.compute().broadcast(()->System.out.println(cache.get(1) + ", " + cache.get(2)));
    }
  }
}