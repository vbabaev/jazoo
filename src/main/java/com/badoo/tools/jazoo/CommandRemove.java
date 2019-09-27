package com.badoo.tools.jazoo;

import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class CommandRemove {
    private PathResolver resolver;
    private ZooKeeperConnection connection;

    public CommandRemove(ZooKeeperConnection connection, PathResolver resolver) {
        this.connection = connection;
        this.resolver = resolver;
    }

    @ShellMethod(key = "rm", value = "Create new file or updates mtime for the existing file")
    public void remove(
            @ShellOption(value = {"-r", "--recursive"}, arity = 0, defaultValue = "false") boolean recursive,
            @ShellOption(value = {"-v", "--verbose"}, arity = 0, defaultValue = "false") boolean verbose,
            @ShellOption(value = "") String path
    ) throws KeeperException, InterruptedException {
        String resolvedPath = resolver.resolve(path);
        if (!recursive) {
            removeSingle(resolvedPath, verbose);
        } else {
            removeRecursive(resolvedPath, verbose);
        }
    }

    private void removeSingle(String resolvedPath, boolean verbose) throws KeeperException, InterruptedException {
        if (connection.listChildren(resolvedPath).size() > 0) {
            throw new RuntimeException("Node " + resolvedPath + " is not empty");
        }
        connection.delete(resolvedPath);
        if (verbose) {
            System.out.println("Deleted " + resolvedPath);
        }
    }

    private void removeRecursive(String resolvedPath, boolean verbose) throws KeeperException, InterruptedException {
        List<String> children = connection.listChildren(resolvedPath);
        for (String child: children) {
            removeRecursive(child, verbose);
        }
        removeSingle(resolvedPath, verbose);
    }

}
