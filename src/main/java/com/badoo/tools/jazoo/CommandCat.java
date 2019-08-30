package com.badoo.tools.jazoo;

import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class CommandCat {

    private ZooKeeperConnection connection;
    private PathResolver resolver;

    public CommandCat(ZooKeeperConnection connection, PathResolver resolver) {
        this.connection = connection;
        this.resolver = resolver;
    }

    @ShellMethod(key = "cat", value = "Returns contents of the given node")
    public String cat(@ShellOption(value = "") String path
    ) throws KeeperException, InterruptedException {
        return this.connection.data(resolver.resolve(path));
    }
}
