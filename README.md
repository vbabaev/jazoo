# jazoo - Zookeeper Client based on Spring-Shell

jazoo - is a modern cli for zookeeper base on spring-shell.

## Debug run (for developers)
```
mvn spring-boot:run -Dspring-boot.run.arguments=--server=localhost:2182
```

## Build
```
git clone https://github.com/vbabaev/jazoo.git
cd jazoo
mvn clean package
java -jar target/jazoo-*.jar --server=localhost:2181
jazoo@localhost:2181:/$ pwd
/
```

## Supported functionality

### cat $path
Prints contents of the given znode
```
jazoo@localhost:2181:/$ cat logs/log-001
log_line_1: err
log_line_1: inf
```

### cd $path
Sets current directory to $path
```
jazoo@localhost:2181:/$ pwd
/
jazoo@localhost:2181:/$ cd dir1
jazoo@localhost:2181:/dir1$ pwd
/dir1
```

### pwd (no arguments)
Returns current directory
```
jazoo@localhost:2181:/$ pwd
/
```

### ls [-l:--long] $path
Returns list of nodes by path
```
jazoo@localhost:2181:/$ ls -l
172	0	2019-08-30 15:54:37   /dir1
172	0	2019-08-30 15:54:37   /dir2
172	0	2019-08-30 15:54:37   /dir3


jazoo@localhost:2181:/$ ls
dir1
dir2
dir3
```
