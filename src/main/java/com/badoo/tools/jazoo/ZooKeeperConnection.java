package com.badoo.tools.jazoo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Scope("singleton")
public class ZooKeeperConnection {

    private final ZooKeeper keeper;

    public ZooKeeperConnection(@Value("${server}") String server, @Value("${timeout:10000}") int timeout) throws IOException {
        System.out.println("Zookeeper server:" + server);
        System.out.println("Zookeeper connection timeout:" + timeout);
        this.keeper = new ZooKeeper(server, timeout, (a) -> {
        });
    }

    public List<String> list(String path) throws KeeperException, InterruptedException {
        return this.keeper.getChildren(path, (w) -> {
        });
    }

    public boolean nodeExists(String path) throws KeeperException, InterruptedException {
        return null != this.keeper.exists(path, (x) -> {
        });
    }
}
