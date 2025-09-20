package org.acme;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

@TopCommand

@Command(name = "myapp", description = "A powerful CLI application built with Quarkus and Picocli", mixinStandardHelpOptions = true, version = "1.0.0", subcommands = {
        GreetingCommand.GreetCommand.class,
        GreetingCommand.UserCommand.class,
        GreetingCommand.FileCommand.class,
        GreetingCommand.SystemCommand.class
})
public class GreetingCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("Welcome to MyApp CLI!");
        System.out.println("Use --help to see available commands.");
        CommandLine.usage(this, System.out);
    }

    @Command(name = "greet", description = "Greet someone with various options")
    static class GreetCommand implements Runnable {

        @Parameters(paramLabel = "<name>", defaultValue = "World", description = "Name to greet")
        String name;

        @Option(names = { "-u", "--uppercase" }, description = "Print greeting in uppercase")
        boolean uppercase;

        @Option(names = { "-t", "--time" }, description = "Include current time")
        boolean includeTime;

        @Option(names = { "-c", "--count" }, description = "Number of times to greet", defaultValue = "1")
        int count;

        @Option(names = { "-s",
                "--style" }, description = "Greeting style: ${COMPLETION-CANDIDATES}", defaultValue = "FRIENDLY")
        GreetingStyle style;

        @Override
        public void run() {
            String greeting = generateGreeting();

            for (int i = 0; i < count; i++) {
                System.out.println(uppercase ? greeting.toUpperCase() : greeting);
            }
        }

        private String generateGreeting() {
            String baseGreeting = switch (style) {
                case FORMAL -> String.format("Good day, %s.", name);
                case CASUAL -> String.format("Hey %s!", name);
                case FRIENDLY -> String.format("Hello %s, nice to meet you!", name);
                case EXCITED -> String.format("WOW! Hi there %s! 🎉", name);
            };

            if (includeTime) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                baseGreeting += String.format(" (at %s)", timestamp);
            }

            return baseGreeting;
        }
    }

    enum GreetingStyle {
        FORMAL, CASUAL, FRIENDLY, EXCITED
    }

    @Command(name = "user", description = "User management commands", subcommands = {
            GreetingCommand.UserCommand.CreateUser.class,
            GreetingCommand.UserCommand.ListUsers.class
    })
    static class UserCommand implements Runnable {

        @Command(name = "create", description = "Create a new user")
        static class CreateUser implements Runnable {
            @Parameters(paramLabel = "<username>", description = "Username for the new user")
            String username;

            @Option(names = { "-e", "--email" }, description = "User email address")
            String email;

            @Option(names = { "-r", "--role" }, description = "User role", defaultValue = "USER")
            String role;

            @Override
            public void run() {
                System.out.printf("Creating user: %s%n", username);
                if (email != null)
                    System.out.printf("  Email: %s%n", email);
                System.out.printf("  Role: %s%n", role);
                System.out.println("✅ User created successfully!");
            }
        }

        @Command(name = "list", description = "List all users")
        static class ListUsers implements Runnable {
            @Option(names = { "-f",
                    "--format" }, description = "Output format: ${COMPLETION-CANDIDATES}", defaultValue = "table")
            OutputFormat format;

            @Override
            public void run() {
                System.out.printf("Listing users (format: %s):%n", format);
                String[] users = { "alice", "bob", "charlie" };

                switch (format) {
                    case TABLE -> {
                        System.out.println("┌──────────┬─────────────────────┬────────┐");
                        System.out.println("│ Username │ Email               │ Role   │");
                        System.out.println("├──────────┼─────────────────────┼────────┤");
                        for (String user : users) {
                            System.out.printf("│ %-8s │ %-19s │ %-6s │%n", user, user + "@example.com", "USER");
                        }
                        System.out.println("└──────────┴─────────────────────┴────────┘");
                    }
                    case JSON -> {
                        System.out.println("[");
                        for (int i = 0; i < users.length; i++) {
                            System.out.printf(
                                    "  {\"username\": \"%s\", \"email\": \"%s@example.com\", \"role\": \"USER\"}%s%n",
                                    users[i], users[i], i < users.length - 1 ? "," : "");
                        }
                        System.out.println("]");
                    }
                    case CSV -> {
                        System.out.println("username,email,role");
                        for (String user : users) {
                            System.out.printf("%s,%s@example.com,USER%n", user, user);
                        }
                    }
                }
            }
        }

        @Override
        public void run() {
            System.out.println("User management commands. Use --help to see subcommands.");
        }
    }

    enum OutputFormat {
        TABLE, JSON, CSV
    }

    @Command(name = "file", description = "File operations", subcommands = {
            GreetingCommand.FileCommand.AnalyzeFile.class,
            GreetingCommand.FileCommand.ProcessFile.class,
            GreetingCommand.FileCommand.BackupFile.class
    })
    static class FileCommand implements Runnable {

        @Command(name = "analyze", description = "Analyze file contents")
        static class AnalyzeFile implements Runnable {
            @Parameters(paramLabel = "<file>", description = "File to analyze")
            String fileName;

            @Option(names = { "-l", "--lines" }, description = "Count lines")
            boolean countLines;

            @Option(names = { "-w", "--words" }, description = "Count words")
            boolean countWords;

            @Option(names = { "-c", "--chars" }, description = "Count characters")
            boolean countChars;

            @Override
            public void run() {
                System.out.printf("Analyzing file: %s%n", fileName);

                if (!countLines && !countWords && !countChars) {
                    // Default behavior if no options specified
                    countLines = countWords = countChars = true;
                }

                // Simulate analysis
                if (countLines)
                    System.out.println("  Lines: 42");
                if (countWords)
                    System.out.println("  Words: 156");
                if (countChars)
                    System.out.println("  Characters: 892");
            }
        }

        @Command(name = "process", description = "Process file with various operations")
        static class ProcessFile implements Runnable {
            @Option(names = { "-i", "--input" }, required = true, description = "Input file")
            String inputFile;

            @Option(names = { "-o", "--output" }, description = "Output file (default: stdout)")
            String outputFile;

            @Option(names = { "--operation" }, description = "Operation to perform")
            FileOperation operation;

            @Override
            public void run() {
                System.out.printf("🔄 Processing file: %s%n", inputFile);
                System.out.printf("   Operation: %s%n", operation != null ? operation : "COPY");
                if (outputFile != null) {
                    System.out.printf("   Output: %s%n", outputFile);
                } else {
                    System.out.println("   Output: stdout");
                }

                // Simulate processing
                System.out.println("   Progress: [████████████████████] 100%");
                System.out.println("✅ File processed successfully!");
            }
        }

        @Command(name = "backup", description = "Create backup of files")
        static class BackupFile implements Runnable {
            @Parameters(description = "Files to backup")
            List<String> files = new ArrayList<>();

            @Option(names = { "-d", "--destination" }, description = "Backup destination")
            String destination;

            @Override
            public void run() {
                if (files.isEmpty()) {
                    System.out.println("❌ No files specified for backup");
                    return;
                }

                System.out.printf("💾 Creating backup of %d file(s)%n", files.size());
                System.out.printf("   Destination: %s%n", destination != null ? destination : "./backup/");

                for (String file : files) {
                    System.out.printf("   Backing up: %s ✅%n", file);
                }
                System.out.println("🎯 Backup completed successfully!");
            }
        }

        @Override
        public void run() {
            System.out.println("File operations. Use --help to see subcommands.");
        }
    }

    @Command(name = "system", description = "System information and utilities")
    static class SystemCommand implements Runnable {

        @Option(names = { "-i", "--info" }, description = "Show system information")
        boolean showInfo;

        @Option(names = { "-m", "--memory" }, description = "Show memory usage")
        boolean showMemory;

        @Option(names = { "-p", "--properties" }, description = "Show system properties")
        boolean showProperties;

        @Override
        public void run() {
            if (showInfo || (!showMemory && !showProperties)) {
                System.out.println("🖥️  System Information:");
                System.out.printf("  OS: %s %s%n", System.getProperty("os.name"), System.getProperty("os.version"));
                System.out.printf("  Java: %s%n", System.getProperty("java.version"));
                System.out.printf("  User: %s%n", System.getProperty("user.name"));
            }

            if (showMemory) {
                Runtime runtime = Runtime.getRuntime();
                long totalMemory = runtime.totalMemory();
                long freeMemory = runtime.freeMemory();
                long usedMemory = totalMemory - freeMemory;

                System.out.println("\n💾 Memory Usage:");
                System.out.printf("  Total: %d MB%n", totalMemory / 1024 / 1024);
                System.out.printf("  Used:  %d MB%n", usedMemory / 1024 / 1024);
                System.out.printf("  Free:  %d MB%n", freeMemory / 1024 / 1024);
            }

            if (showProperties) {
                System.out.println("\n⚙️  Key System Properties:");
                String[] keys = { "java.home", "user.home", "user.dir", "file.separator" };
                for (String key : keys) {
                    System.out.printf("  %s: %s%n", key, System.getProperty(key));
                }
            }
        }
    }

    enum FileOperation {
        COPY, MOVE, TRANSFORM, COMPRESS, DECOMPRESS, ENCRYPT, DECRYPT
    }
}
