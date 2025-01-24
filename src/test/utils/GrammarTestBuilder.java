package test.utils;

public class GrammarTestBuilder {
    private final StringBuilder builder = new StringBuilder();

    // Add a token to the string being built
    public GrammarTestBuilder add(String token) {
        if (builder.length() > 0) {
            builder.append(" "); // Add a space between tokens
        }
        builder.append(token);
        return this;
    }

    // Add multiple tokens at once
    public GrammarTestBuilder addAll(String... tokens) {
        for (String token : tokens) {
            add(token);
        }
        return this;
    }

    // Build the final string
    public String build() {
        return builder.toString();
    }

    // Clear the builder for reuse
    public GrammarTestBuilder clear() {
        builder.setLength(0);
        return this;
    }
}
