package store.seasonlabs.clicker;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import store.seasonlabs.api.libraries.connector.SQLConnector;
import store.seasonlabs.clicker.database.connection.ConnectionProvider;
import store.seasonlabs.clicker.parser.ProductParser;

public final class ClickerPlugin extends JavaPlugin {

    @Getter private static ClickerPlugin instance;
    @Getter private ProductParser productParser;
    @Getter private SQLConnector databaseConnector;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        instance = this;

    }

    @Override
    public void onEnable() {

        databaseConnector = new ConnectionProvider(this).connector();
        productParser = new ProductParser();



        for(String path : getConfig().getConfigurationSection("shop").getKeys(false)) {

            ConfigurationSection section = getConfig().getConfigurationSection("shop." + path);
            productParser.parseProductConfiguration(section);

        }



    }
}

