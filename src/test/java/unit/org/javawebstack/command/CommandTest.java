package unit.org.javawebstack.command;

import org.javawebstack.command.CommandResult;
import org.javawebstack.command.CommandSystem;
import org.javawebstack.command.MultiCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    private final CommandSystem system;

    public CommandTest(){
        system = new CommandSystem()
                .addCommand("sub", new MultiCommand()
                    .add("inner", (args, params) -> {
                        if(!params.containsKey("r"))
                            return CommandResult.error("r");
                        if(params.containsKey("string") && !params.get("string").equals("test"))
                            return CommandResult.error("string");
                        return CommandResult.success();
                    })
                )
                .addCommand("simple", (args, params) -> CommandResult.success());
    }

    @Test
    public void testNotFound(){
        CommandResult result = system.eval(new String[]{"unknown"});
        assertEquals("notFound", result.getState());
        assertEquals("unknown", result.getError());
    }

    @Test
    public void testSimple(){
        CommandResult result = system.eval(new String[]{"simple"});
        assertEquals("success", result.getState());
    }

    @Test
    public void testParam(){
        CommandResult result = system.eval(new String[]{"sub", "inner", "-r", "--string", "test"});
        assertEquals("success", result.getState());
        result = system.eval(new String[]{"sub", "inner", "-r", "--string"});
        assertEquals("error", result.getState());
        assertEquals("string", result.getError());
    }

    @Test
    public void testFlag(){
        CommandResult result = system.eval(new String[]{"sub", "inner", "-r"});
        assertEquals("success", result.getState());
        result = system.eval(new String[]{"sub", "inner"});
        assertEquals("error", result.getState());
        assertEquals("r", result.getError());
    }

}
