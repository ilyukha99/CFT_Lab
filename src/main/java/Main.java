public class Main {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Termination.")));
        try {
            Validator.validate(Parser.parseArgs(args));
            System.out.println(Runtime.getRuntime().freeMemory());
            if (Validator.options.contains("-d")) {
                Sorter.flag = -1;
            }
            if (Validator.options.contains("-bf")) {
                Worker.workWithBigFiles();
            }
            else Worker.startRoutine();
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }
}