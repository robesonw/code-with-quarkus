# 🚀 Comprehensive CLI Application with Quarkus & Picocli

This project demonstrates a powerful command-line interface built with **Qua### **Standard JAR Package\*\*
The application can be packaged using:

```shell script
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn package
```

This produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Dependencies are copied into the `target/quarkus-app/lib/` directory.

Run with: `java -jar target/quarkus-app/quarkus-run.jar [command] [options]`

### **Uber JAR Package**

To build an _über-jar_ with all dependencies included:

```shell script
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn package -Dquarkus.package.jar.type=uber-jar
```

Run with: `java -jar target/*-runner.jar [command] [options]`onic Subatomic Java Framework) and **Picocli** (a modern framework for building feature-rich command line applications).

## 🎯 Application Overview

A multi-command CLI application showcasing various enterprise patterns:

- **Multi-command architecture** with organized subcommands
- **Rich option support** including enums, required parameters, and boolean flags
- **Multiple output formats** (JSON, CSV, Table)
- **Interactive features** with progress indicators and styled output
- **Comprehensive help system** with automatic documentation

## 📋 Available Commands

### 1. **Greet Command** - Interactive greeting with styling options

```bash
java -jar target/quarkus-app/quarkus-run.jar greet Alice --style WOW --timestamp
```

**Features:**

- Style options: `NORMAL`, `EXCITED`, `WOW`
- Optional timestamp display
- Personalized messaging

### 2. **User Management** - CRUD operations with multiple output formats

```bash
# List users in different formats
java -jar target/quarkus-app/quarkus-run.jar user list --format JSON
java -jar target/quarkus-app/quarkus-run.jar user list --format CSV
java -jar target/quarkus-app/quarkus-run.jar user list --format TABLE

# Create new user
java -jar target/quarkus-app/quarkus-run.jar user create johndoe --email john@example.com --role ADMIN

# Delete user
java -jar target/quarkus-app/quarkus-run.jar user delete alice
```

**Features:**

- Multiple output formats (JSON/CSV/Table)
- Role-based user creation (`USER`, `ADMIN`, `MODERATOR`)
- Simulated database operations

### 3. **File Operations** - File processing and management tools

```bash
# Analyze file contents
java -jar target/quarkus-app/quarkus-run.jar file analyze document.txt --lines --words --chars

# Process files with operations
java -jar target/quarkus-app/quarkus-run.jar file process --input data.csv --operation TRANSFORM --output result.csv

# Backup multiple files
java -jar target/quarkus-app/quarkus-run.jar file backup "file1.txt" "file2.txt" "config.json" -d "C:\backups"
```

**Features:**

- File analysis with statistics
- Processing pipeline with operations: `COPY`, `MOVE`, `TRANSFORM`, `COMPRESS`, `DECOMPRESS`, `ENCRYPT`, `DECRYPT`
- Batch backup operations with progress indicators

### 4. **System Information** - System monitoring and diagnostics

```bash
# Show system information
java -jar target/quarkus-app/quarkus-run.jar system --info

# Display memory usage
java -jar target/quarkus-app/quarkus-run.jar system --memory

# Show system properties
java -jar target/quarkus-app/quarkus-run.jar system --properties

# Combine multiple options
java -jar target/quarkus-app/quarkus-run.jar system --info --memory --properties
```

**Features:**

- OS and Java version information
- Real-time memory usage monitoring
- System properties inspection

## 🏗️ Architecture Patterns Demonstrated

### **Command Structure**

- **Top-level command** with `@TopCommand` annotation
- **Subcommands** organized by functional domain
- **Nested subcommands** for complex operations
- **Help integration** with automatic documentation

### **Option Types**

- **Boolean flags** (`--verbose`, `--timestamp`)
- **Required options** (`--input`, `--email`)
- **Optional parameters** with defaults
- **Enum choices** for controlled input
- **Multi-value parameters** for file lists

### **Output Formatting**

- **Structured data** with JSON/CSV export
- **Tabular display** with aligned columns
- **Progress indicators** for long operations
- **Styled output** with emojis and colors
- **Error handling** with user-friendly messages

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## 🚀 Running the Application

### **Development Mode (Hot Reloading)**

For development with live coding enabled:

```shell script
# Use regular Maven (recommended)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn quarkus:dev

# Or use Maven wrapper (may have Java version conflicts)
./mvnw quarkus:dev
```

> **_NOTE:_** Quarkus ships with a Dev UI available in dev mode at <http://localhost:8080/q/dev/>

### **Running with Arguments in Dev Mode**

Pass CLI arguments during development:

```shell script
mvn quarkus:dev -Dquarkus.args='greet Alice --style WOW'
mvn quarkus:dev -Dquarkus.args='user list --format JSON'
```

### **Production Build & Run**

Build and run the optimized application:

```shell script
# Build the application
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn package -DskipTests

# Run the CLI application
java -jar target/quarkus-app/quarkus-run.jar [command] [options]

# Show all available commands
java -jar target/quarkus-app/quarkus-run.jar --help
```

## 📦 Building Different Package Types

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## ⚡ Native Executable (GraalVM)

### **With GraalVM Installed**

Create a native executable for ultra-fast startup:

```shell script
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn package -Dnative
```

### **Using Container Build**

Build native executable without local GraalVM installation:

```shell script
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn package -Dnative -Dquarkus.native.container-build=true
```

Execute native binary: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner [command] [options]`

## 🛠️ Troubleshooting

### **Java Version Issues**

If you encounter dependency injection errors:

- Ensure Java 21 is installed and `JAVA_HOME` is set correctly
- Use regular Maven instead of Maven wrapper for better compatibility:
  ```shell script
  $env:JAVA_HOME = "C:\Program Files\Java\jdk-21"; mvn [command]
  ```

### **Common Issues**

- **Maven wrapper conflicts**: Use `mvn` instead of `./mvnw`
- **Plugin compatibility**: Ensure Quarkus version matches plugin versions
- **Native build requirements**: GraalVM or Docker required for native builds

## 💡 Extension Ideas

This CLI application can be extended with:

- **Configuration Management** - Load/save settings from YAML/JSON files
- **Database Integration** - JPA entities with H2/PostgreSQL persistence
- **REST API Client** - Commands for calling external services
- **Workflow Automation** - Multi-step process orchestration
- **Plugin System** - Dynamically loadable command modules
- **Interactive Mode** - REPL-style command interface
- **Scheduled Tasks** - Cron-like job execution
- **Report Generation** - PDF/Excel export capabilities

## 📚 Technical Stack

- **Quarkus 3.8.6** - Supersonic Subatomic Java Framework
- **Picocli 4.7.6** - Modern CLI framework with rich features
- **Jakarta CDI** - Dependency injection and context management
- **Java 21** - Latest LTS version with modern language features
- **Maven 3.9.6** - Build automation and dependency management

## 📖 Related Guides

- [Quarkus Picocli Guide](https://quarkus.io/guides/picocli) - Develop command line applications with Picocli
- [Quarkus Maven Tooling](https://quarkus.io/guides/maven-tooling) - Learn about building native executables
- [Picocli Documentation](https://picocli.info/) - Comprehensive Picocli framework documentation

## 🎯 Example Usage Scenarios

### **Data Processing Pipeline**

```bash
# Process and transform data files
java -jar target/quarkus-app/quarkus-run.jar file process --input raw-data.csv --operation TRANSFORM --output clean-data.csv

# Analyze results
java -jar target/quarkus-app/quarkus-run.jar file analyze clean-data.csv --lines --words --chars
```

### **User Management Workflow**

```bash
# Create multiple users
java -jar target/quarkus-app/quarkus-run.jar user create admin --email admin@company.com --role ADMIN
java -jar target/quarkus-app/quarkus-run.jar user create manager --email manager@company.com --role MODERATOR

# Export user list
java -jar target/quarkus-app/quarkus-run.jar user list --format JSON > users-backup.json
```

### **System Monitoring**

```bash
# Quick system check
java -jar target/quarkus-app/quarkus-run.jar system --info --memory

# Detailed diagnostics
java -jar target/quarkus-app/quarkus-run.jar system --info --memory --properties
```

---

**Built with ❤️ using Quarkus and Picocli**
