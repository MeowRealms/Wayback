package com.ilummc.wayback.policy;

import com.google.gson.annotations.SerializedName;
import com.ilummc.wayback.Wayback;
import com.ilummc.wayback.WaybackConf;
import com.ilummc.wayback.storage.LocalStorage;
import com.ilummc.wayback.tasks.Executable;
import com.ilummc.wayback.util.Jsons;
import com.ilummc.wayback.util.Pair;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CleanExceedPolicy implements ConfigurationSerializable, Policy {

    @SerializedName("max_backups")
    private int maxBackups = 10;

    public static CleanExceedPolicy valueOf(Map<String, Object> map) {
        return Jsons.mapTo(map, CleanExceedPolicy.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> serialize() {
        return new ObjectMapper().convertValue(this, Map.class);
    }

    @Override
    public void accept(Executable task) {
        LocalStorage storage = (LocalStorage) WaybackConf.getConf().getStorage("local_storage");
        List<File> files = storage.getFilesStream()
                .sorted(Comparator.comparing(Pair::getValue))
                .map(Pair::getKey)
                .collect(Collectors.toList());
        if (files.size() > maxBackups) {
            for (int i = 0; i < (files.size() - maxBackups); i++) {
                File file = files.get(i);
                if (!file.delete()) {
                    Wayback.logger().warn("POLICY.FILE_DELETE_FAIL", file.getName());
                }
            }
        }
    }

    @Override
    public Policy create() {
        return this;
    }

    @Override
    public void reset() {

    }
}
