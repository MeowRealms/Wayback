package com.ilummc.wayback.policy;

import com.ilummc.wayback.Wayback;
import com.ilummc.wayback.WaybackConf;
import com.ilummc.wayback.storage.LocalStorage;
import com.ilummc.wayback.tasks.Executable;
import com.ilummc.wayback.util.Jsons;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CleanOldestPolicy implements ConfigurationSerializable, Policy {

    public static CleanOldestPolicy valueOf(Map<String, Object> map) {
        return Jsons.mapTo(map, CleanOldestPolicy.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> serialize() {
        return new ObjectMapper().convertValue(this, Map.class);
    }

    @Override
    public void accept(Executable task) {
        LocalStorage storage = (LocalStorage) WaybackConf.getConf().getStorage("local_storage");
        Optional<File> optional = storage.oldestZip();
        if (optional.isPresent()) {
            File file = optional.get();
            if (!file.delete()) {
                Wayback.logger().warn("POLICY.FILE_DELETE_FAIL", file.getName());
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
