package com.badoo.tools.jazoo;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CommandPwd {

    private PathResolver resolver;

    public CommandPwd(PathResolver resolver) {
        this.resolver = resolver;
    }

    @ShellMethod(key = "pwd", value = "Returns current directory")
    public String pwd() {
        return resolver.getCurrent();
    }
}
