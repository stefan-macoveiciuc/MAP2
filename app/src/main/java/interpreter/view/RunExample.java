package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;

import java.util.function.Supplier;

public class RunExample extends Command {

    private final Supplier<Controller> controllerFactory;

    public RunExample(
            String key,
            String description,
            Supplier<Controller> controllerFactory
    ) {
        super(key, description);
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void execute() {

        Controller controller = controllerFactory.get();

        try {
            controller.allStep();

            System.out.println();
            System.out.println("Program executed successfully.");

        } catch (MyException e) {
            System.out.println(
                    "Interpreter error: " + e.getMessage()
            );
        }
    }
}