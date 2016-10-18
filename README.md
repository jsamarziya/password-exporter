# Password Exporter

Exports a Codebook database (i.e. a `strip.db` file) to JSON.


# Requirements

This tool requires Java 1.8 or later and a 64-bit version of OS X.


# Usage

```
java -jar lib/password-exporter.jar <database-file> <password>
```


# Acknowledgements

This tool makes use of Philip DeCamp's JDBC Driver for SQLCipher, [sqlcipher-jdbc](https://github.com/decamp/sqlcipher-jdbc).
