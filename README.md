# pmd maven plugin bug

When using the 3.8 version of the maven-pmd-plugin and telling it to use pmd 5.8.1 instead of 5.6.1, it is failing to process certain files and not reporting pmd errors (and so the build succeeds).

```
./mvnw clean install
```

Notice that example1 and example2 modules have warnings about PMD processing errors:

```
[INFO] >>> maven-pmd-plugin:3.8:check (default) > :pmd @ example1 >>>
[INFO] 
[INFO] --- maven-pmd-plugin:3.8:pmd (pmd) @ example1 ---
[WARNING] There are 1 PMD processing errors:
[WARNING] /Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java: Error while processing /Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java
[INFO] 
[INFO] <<< maven-pmd-plugin:3.8:check (default) < :pmd @ example1 <<<
...
[INFO] >>> maven-pmd-plugin:3.8:check (default) > :pmd @ example2 >>>
[INFO] 
[INFO] --- maven-pmd-plugin:3.8:pmd (pmd) @ example2 ---
[WARNING] There are 1 PMD processing errors:
[WARNING] /Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java: Error while processing /Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java
[INFO] 
[INFO] <<< maven-pmd-plugin:3.8:check (default) < :pmd @ example2 <<<
```

The example3 module, however, fails properly:

```
[INFO] >>> maven-pmd-plugin:3.8:check (default) > :pmd @ example3 >>>
[INFO] 
[INFO] --- maven-pmd-plugin:3.8:pmd (pmd) @ example3 ---
[INFO] 
[INFO] <<< maven-pmd-plugin:3.8:check (default) < :pmd @ example3 <<<
[INFO] 
[INFO] 
[INFO] --- maven-pmd-plugin:3.8:check (default) @ example3 ---
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] app-parent ......................................... SUCCESS [  0.241 s]
[INFO] common ............................................. SUCCESS [  2.179 s]
[INFO] example1 ........................................... SUCCESS [  0.222 s]
[INFO] example2 ........................................... SUCCESS [  0.163 s]
[INFO] example3 ........................................... FAILURE [  1.238 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.138 s
[INFO] Finished at: 2017-12-14T11:33:00-05:00
[INFO] Final Memory: 32M/489M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-pmd-plugin:3.8:check (default) on project example3: You have 3 PMD violations. For more details see: /Users/jbard/projects/pmd-plugin-bug/example3/target/pmd.xml -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
[ERROR] 
[ERROR] After correcting the problems, you can resume the build with the command
[ERROR]   mvn <goals> -rf :example3
```

Nothing fails to process or warns when using the pmd tool in either 5.8.1 or 5.6.1 (they both successfully report the pmd violations):

```
jbard-M01:pmd-bin-5.8.1 jbard$ ./bin/run.sh pmd -d ~/projects/pmd-plugin-bug/ -f text -R ~/projects/pmd-plugin-bug/pmdrules.xml -version 1.8 -language java
/Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java:7:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java:12:	Avoid using if statements without curly braces
/Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java:12:	Do not use if statements that are always true or always false
/Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java:7:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java:12:	Avoid using if statements without curly braces
/Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java:12:	Do not use if statements that are always true or always false
/Users/jbard/projects/pmd-plugin-bug/example3/src/main/java/org/apache/maven/pmd/bug/Example3.java:7:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/Users/jbard/projects/pmd-plugin-bug/example3/src/main/java/org/apache/maven/pmd/bug/Example3.java:12:	Avoid using if statements without curly braces
/Users/jbard/projects/pmd-plugin-bug/example3/src/main/java/org/apache/maven/pmd/bug/Example3.java:12:	Do not use if statements that are always true or always false
jbard-M01:pmd-bin-5.8.1 jbard$ 
```

```
jbard-M01:pmd-bin-5.6.1 jbard$ ./bin/run.sh pmd -d ~/projects/pmd-plugin-bug/ -f text -R ~/projects/pmd-plugin-bug/pmdrules.xml -version 1.8 -language java
/Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java:7:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java:12:	Avoid using if statements without curly braces
/Users/jbard/projects/pmd-plugin-bug/example1/src/main/java/org/apache/maven/pmd/bug/Example1.java:12:	Do not use if statements that are always true or always false
/Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java:7:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java:12:	Avoid using if statements without curly braces
/Users/jbard/projects/pmd-plugin-bug/example2/src/main/java/org/apache/maven/pmd/bug/Example2.java:12:	Do not use if statements that are always true or always false
/Users/jbard/projects/pmd-plugin-bug/example3/src/main/java/org/apache/maven/pmd/bug/Example3.java:7:	All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/Users/jbard/projects/pmd-plugin-bug/example3/src/main/java/org/apache/maven/pmd/bug/Example3.java:12:	Avoid using if statements without curly braces
/Users/jbard/projects/pmd-plugin-bug/example3/src/main/java/org/apache/maven/pmd/bug/Example3.java:12:	Do not use if statements that are always true or always false
jbard-M01:pmd-bin-5.6.1 jbard$ 
```
