package dev.vbabaev.tools.jazoo.command;

import dev.vbabaev.tools.jazoo.PathResolver;
import dev.vbabaev.tools.jazoo.ZooKeeperConnection;
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
        Stream<String> stringStream = this.connection.list(resolved_name).stream().sorted();
        if (fullNames) {
            stringStream = stringStream
                    .parallel()
                    .map(full_name -> {
                        try {
                            return connection.stat(full_name) + "\t" + full_name;
                        } catch (KeeperException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } else {
            stringStream = stringStream.map(PathResolver::filename);
        }
        return stringStream.collect(Collectors.toList());
    }
}
