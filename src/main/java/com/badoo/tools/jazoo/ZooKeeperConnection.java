package com.badoo.tools.jazoo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Scope("singleton")
public class ZooKeeperConnection {

    private final SimpleDateFormat formatter_ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public String stat(String path) throws KeeperException, InterruptedException {
        Stat stat = this.keeper.exists(path, (w) -> {
        });

        return stat.getDataLength() + "\t" + stat.getNumChildren() + "\t" + formatter_ts.format(new Date(stat.getMtime()));
    }

    public boolean nodeExists(String path) throws KeeperException, InterruptedException {
        return null != this.keeper.exists(path, (x) -> {
        });
    }

    public String data(String path)throws KeeperException, InterruptedException {
        return new String(this.keeper.getData(path, false, null));
    }
}
