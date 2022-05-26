package store.seasonlabs.clicker.parser;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import store.seasonlabs.api.libraries.inventory.ItemLibrary;
import store.seasonlabs.api.libraries.text.ColorLibrary;
import store.seasonlabs.clicker.ClickerPlugin;
import store.seasonlabs.clicker.data.product.Product;

import java.util.List;

public class ProductParser {

    @Getter private final List<Product> products = Lists.newArrayList();

    public void parseProductConfiguration(ConfigurationSection section) {

        try {

            ItemLibrary itemLibrary = new ItemLibrary(Material.matchMaterial(section.getString("view.material")));

            itemLibrary.name(ColorLibrary.colored(section.getString("view.name")));
            itemLibrary.lore(section.getStringList("view.lore"));
            itemLibrary.data(section.getInt("view.data"));
            itemLibrary.amount(section.getInt("view.amount"));

            if(section.getBoolean("view.hide-attributes")) itemLibrary.hideAttributes();

            Product product = Product.builder()
                    .name(section.getString("product.name"))
                    .itemStack(itemLibrary.build())
                    .command(section.getStringList("product.command"))
                    .build();

            products.add(product);
            ClickerPlugin.getInstance().getLogger().info("Produto '" + product.getName() + "' carregado.");

        } catch (Exception e) {
            ClickerPlugin.getInstance().getLogger().severe("Não foi possível carregar os produtos.");
            e.printStackTrace();

            Bukkit.getPluginManager().disablePlugin(ClickerPlugin.getInstance());

        }

    }

}
