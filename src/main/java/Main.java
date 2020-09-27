public class Main {

    public static void main(String[] args) {
        try {
            Validator.validate(Parser.parseArgs(args));
            if (Validator.options.contains("-bf")) {
                Worker.workWithBigFiles();
            }
            else Worker.startRoutine();
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }
}