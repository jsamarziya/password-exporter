package net.sixcats.passwordexport;

/**
 * The Codebook database export application.
 */
public class ExportDatabase {
    public static void main(String[] args) {
        if (args.length != 2) {
            usage();
            System.exit(-1);
        }
        try {
            System.out.println(new PasswordDatabaseExporter(args[0], args[1]).export().toString(4));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    private static void usage() {
        System.err.println("Usage: java -jar password-exporter.jar <database-file> <password>");
    }
}
