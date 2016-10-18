# Password Exporter

Exports a Codebook database (i.e. a `strip.db` file) to JSON.


# Requirements

This tool requires Java 1.8 or later and a 64-bit version of OS X.


# Usage

```
java -jar lib/password-exporter.jar <database-file> <password>
```


# Acknowledgements

This tool makes use of the SQLite JDBC driver that Philip DeCamp modified to work with SQLCipher, [sqlcipher-jdbc](https://github.com/decamp/sqlcipher-jdbc).
