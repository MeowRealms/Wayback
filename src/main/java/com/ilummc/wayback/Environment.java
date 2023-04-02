package com.ilummc.wayback;

import com.ilummc.wayback.helper.LibraryLoader;

final class Environment {

    static void check() {
        LibraryLoader.load("com.github.asbachb", "ftp4j", "1.7.3", "https://raw.github.com/asbachb/mvn-repo/master/releases");
        LibraryLoader.load("commons-collections", "commons-collections", "3.2.2", "https://maven.aliyun.com/repository/public");
        LibraryLoader.load("org.codehaus.jackson", "jackson-core-asl", "1.9.13", "https://maven.aliyun.com/repository/public");
        LibraryLoader.load("org.codehaus.jackson", "jackson-mapper-asl", "1.9.13", "https://maven.aliyun.com/repository/public");
        LibraryLoader.load("net.lingala.zip4j", "zip4j", "1.3.2", "https://maven.aliyun.com/repository/public");
    }

}
