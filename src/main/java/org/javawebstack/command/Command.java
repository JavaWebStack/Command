package org.javawebstack.command;

import java.util.List;
import java.util.Map;

public interface Command {

    CommandResult execute(List<String> args, Map<String, String> params);

}
