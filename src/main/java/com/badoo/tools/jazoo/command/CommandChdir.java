package com.badoo.tools.jazoo.command;

import com.badoo.tools.jazoo.PathResolver;
import com.badoo.tools.jazoo.ZooKeeperConnection;
import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class CommandChdir {

    private ZooKeeperConnection connection;
    private PathResolver resolver;

    public CommandChdir(ZooKeeperConnection connection, PathResolver resolver) {
        this.connection = connection;
        this.resolver = resolver;
    }

    @ShellMethod(key = "cd", value = "Return list of sub-nodes from the given node")

    public void cd(String path) throws KeeperException, InterruptedException {
        String real_path = resolver.resolve(path);
        if (connection.nodeExists(real_path)) {
            resolver.set(real_path);
        } else {
            throw new RuntimeException("Node '" + real_path + "' doesn't exist");
        }
    }
}
