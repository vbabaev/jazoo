package dev.vbabaev.tools.jazoo.command;

import dev.vbabaev.tools.jazoo.PathResolver;
import dev.vbabaev.tools.jazoo.ZooKeeperConnection;
import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
