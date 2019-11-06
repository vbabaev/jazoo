package com.badoo.tools.jazoo.command;

import com.badoo.tools.jazoo.PathResolver;
import com.badoo.tools.jazoo.ZooKeeperConnection;
import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ShellComponent
public class CommandFind {

    enum Type {
        a,
        d,
        f
    }

    private ZooKeeperConnection connection;
    private PathResolver resolver;

    public CommandFind(ZooKeeperConnection connection, PathResolver resolver) {
        this.connection = connection;
        this.resolver = resolver;
    }

    @ShellMethod(key = "find", value = "Find nodes")
    public void find(
            @ShellOption(value = {"-type"}, defaultValue = "a") Type type,
            @ShellOption(value = "", defaultValue = ".") String path
    ) throws KeeperException, InterruptedException {
        final String resolvedPath = resolver.resolve(path);
        final ArrayList<String> result = new ArrayList<>();
        Predicate<Boolean> typePredicate = null;

        switch (type.toString()) {
            case "d":
                typePredicate = (b) -> !b;
                break;
            case "f":
                typePredicate = (b) -> b;
                break;
            case "a":
                typePredicate = (b) -> true;
                break;
            default:
        }

        fillList(resolvedPath, 0, 3, typePredicate);
    }

    private void fillList(final String path, int depth, int max_depth, Predicate<Boolean> typePredicate) throws KeeperException, InterruptedException {
        if (max_depth != -1 && max_depth < depth) {
            return;
        }
        List<String> list = connection.listChildren(path);
        if (typePredicate.test(list.size() == 0)) {
            System.out.println(path);
        }
        for (String child : list) {
            fillList(child, depth + 1, max_depth, typePredicate);
        }
    }
}
