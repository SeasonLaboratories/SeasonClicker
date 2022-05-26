package store.seasonlabs.clicker.data.product;

import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Builder
@Data
public class Product {

    private String name;
    private List<String> command;
    private ItemStack itemStack;

}
