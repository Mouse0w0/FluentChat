package fluentchat.server.command;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    public static Result parse(String command) {
        String label = null;
        List<String> args = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inputString = false;
        boolean escape = false;
        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);
            switch (c) {
                case '\\':
                    escape = true;
                    break;
                case '"':
                    if (escape) {
                        sb.append(escapeChar(c));
                        escape = false;
                    } else {
                        inputString = !inputString;
                    }
                    break;
                case ' ':
                    if (inputString) {
                        sb.append(c);
                    } else {
                        if (label == null) {
                            label = sb.toString();
                        } else {
                            args.add(sb.toString());
                        }
                        sb.delete(0, sb.length());
                    }
                    break;
                default:
                    if (escape) {
                        sb.append(escapeChar(c));
                        escape = false;
                    } else {
                        sb.append(c);
                    }
            }
        }

        if (label == null) {
            label = sb.toString();
        } else {
            args.add(sb.toString());
        }

        return new Result(label, args.toArray(new String[0]));
    }

    private static char escapeChar(char c) {
        switch (c) {
            case '\"':
                return '\"';
            case '\'':
                return '\'';
            case '\\':
                return '\\';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static class Result {
        public final String label;
        public final String[] args;

        public Result(String label, String[] args) {
            this.label = label;
            this.args = args;
        }
    }
}
