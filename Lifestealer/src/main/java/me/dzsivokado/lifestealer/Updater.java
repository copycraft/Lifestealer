package me.dzsivokado.lifestealer;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class Updater {
    private final Lifestealer plugin;
    private final Logger logger;

    // URL pointing to the latest plugin version (example using GitHub Releases)
    private final String updateUrl = "https://github.com/yourusername/yourrepository/releases/latest/download/plugin.jar";
    // Path to the plugin jar file on the server
    private final File pluginFile;

    public Updater(Lifestealer plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.pluginFile = new File(plugin.getDataFolder().getParentFile(), plugin.getName() + ".jar");
    }

    public void checkForUpdates() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(updateUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    logger.info("Update found, downloading...");
                    FileUtils.copyURLToFile(new URL(updateUrl), pluginFile);
                    logger.info("Update downloaded, please restart the server to apply the update.");
                } else {
                    logger.info("No updates found.");
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to check for updates: " + e.getMessage());
        }
    }
}
