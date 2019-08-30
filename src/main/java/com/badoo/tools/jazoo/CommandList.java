package com.badoo.tools.jazoo;

import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class CommandList {

    private ZooKeeperConnection connection;
    private PathResolver resolver;

    public CommandList(ZooKeeperConnection connection, PathResolver resolver) {
        this.connection = connection;
        this.resolver = resolver;
    }

    @ShellMethod(key = "ls", value = "Returns a list of sub-nodes from the given node")
    public List<String> list(@ShellOption(value = {"-l", "--long"}, arity = 0) boolean fullNames,
                             @ShellOption(value = "", defaultValue = "") String path
    ) throws KeeperException, InterruptedException {
        if (path.equals("")) {
            path = resolver.getCurrent();
        }
        String resolved_name = resolver.resolve(path);
        this.connection.list(resolved_name).forEach(n -> {
            System.out.println("DEBUG: " + resolved_name + " " + n);
        });
        Stream<String> stringStream = this.connection.list(resolved_name).stream().sorted();
        if (fullNames) {
            stringStream = stringStream
                    .map(name -> resolved_name + (resolved_name.equals("/") ? "" : "/") + name)
                    .map(full_name -> {
                        try {
                            return connection.stat(full_name) + "\t" + full_name;
                        } catch (KeeperException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        return stringStream.collect(Collectors.toList());
    }
}
