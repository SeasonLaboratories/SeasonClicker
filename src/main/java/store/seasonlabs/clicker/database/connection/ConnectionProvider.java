package store.seasonlabs.clicker.database.connection;

import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import store.seasonlabs.api.libraries.connector.SQLConnector;
import store.seasonlabs.api.libraries.connector.type.SQLDatabaseType;
import store.seasonlabs.api.libraries.connector.type.impl.MySQLDatabaseType;
import store.seasonlabs.api.libraries.connector.type.impl.SQLiteDatabaseType;

import java.io.File;
import java.util.logging.Logger;

@AllArgsConstructor
public class ConnectionProvider {

    private final Plugin plugin;

    public SQLConnector connector() {

        FileConfiguration configuration = plugin.getConfig();
        ConfigurationSection databaseSection = configuration.getConfigurationSection("database");

        String sqlType = databaseSection.getString("type");
        Logger logger = plugin.getLogger();

        SQLConnector sqlConnector;

        if (sqlType.equalsIgnoreCase("mysql")) {

            ConfigurationSection mysqlSection = databaseSection.getConfigurationSection("mysql");
            sqlConnector = mySqlDatabaseType(mysqlSection).connect();
            logger.info("Connection to MySQL started.");

        } else if (sqlType.equalsIgnoreCase("sqlite")) {

            ConfigurationSection sqliSection = databaseSection.getConfigurationSection("sqlite");
            sqlConnector = sqLiteDatabase(sqliSection).connect();

            logger.info("Connection to SQLite started.");
            logger.warning("We recommend using MySQL.");

        } else {

            logger.severe("Database type is invalid");
            return null;

        }

        return sqlConnector;

    }

    private SQLDatabaseType sqLiteDatabase(ConfigurationSection section) {
        return SQLiteDatabaseType.builder()
                .file(new File(plugin.getDataFolder(), section.getString("file")))
                .build();

    }

    private SQLDatabaseType mySqlDatabaseType(ConfigurationSection section) {

        return MySQLDatabaseType.builder()
                .address(section.getString("address"))
                .username(section.getString("username"))
                .password(section.getString("password"))
                .database(section.getString("database"))
                .build();

    }

}

