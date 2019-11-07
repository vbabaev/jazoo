package dev.vbabaev.tools.jazoo.command;

import dev.vbabaev.tools.jazoo.PathResolver;
import dev.vbabaev.tools.jazoo.ZooKeeperConnection;
import org.apache.zookeeper.KeeperException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CommandTouch {

    private ZooKeeperConnection connection;
    private PathResolver resolver;

    public CommandTouch(ZooKeeperConnection connection, PathResolver resolver) {
        this.connection = connection;
        this.resolver = resolver;
    }

    @ShellMethod(key = "touch", value = "Create new file or updates mtime for the existing file")
    public void touch(
            @ShellOption(value = {"-e", "--ephemeral"}, arity = 0, defaultValue = "false") boolean ephemeral,
            @ShellOption(value = {"-s", "--sequential"}, arity = 0, defaultValue = "false") boolean sequential,
            @ShellOption(value = "") String path
    ) throws KeeperException, InterruptedException {
        this.connection.touch(resolver.resolve(path), ephemeral, sequential);
    }
}
