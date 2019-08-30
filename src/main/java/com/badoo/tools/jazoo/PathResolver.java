package com.badoo.tools.jazoo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
@Scope("singleton")
public class PathResolver {
    private String current;

    public PathResolver() {
        this.current = "/";
    }

    public String resolveParts(String path) {
        String[] parts = path.split("/");
        Stack<String> stack = new Stack<>();
        for (String part: parts) {
            if (part.isEmpty()) continue;
            if (part.equals("\\.")) continue;
            if (part.equals("..")) {
                stack.pop();
                continue;
            }
            stack.push(part);
        }
        return "/" + String.join("/", stack);
    }

    public String resolve(String path) {
        String resolved;
        if (path.startsWith("/")) {
            resolved = trim(path);
        } else {
            resolved = current + (current.equals("/") ? "" : "/") + path;
        }
        return resolveParts(resolved);
    }

    public String set(String path) {
        this.current = resolve(path);
        return this.current;
    }

    public String getCurrent() {
        return this.current;
    }

    private String trim(String path) {
        if (path.equals("/")) return path;
        if (path.endsWith("/")) return trim(path.substring(0, path.length() - 1));
        return path;
    }
}
