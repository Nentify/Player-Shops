package me.nentify.playershops.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode config;

    public double creationCost;

    public Configuration(Path configPath) throws IOException {
        loader = HoconConfigurationLoader.builder().setPath(configPath).build();

        if (!Files.exists(configPath))
            Files.createFile(configPath);

        config = loader.load();

        creationCost = check(config.getNode("creation-cost"), 0.0, "Cost to create a shop").getDouble();
    }

    private CommentedConfigurationNode check(CommentedConfigurationNode node, Object defaultValue, String comment) {
        if (node.isVirtual())
            node.setValue(defaultValue).setComment(comment);

        return node;
    }
}