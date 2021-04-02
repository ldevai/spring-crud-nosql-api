package io.devai.tutorials.app.util;

import io.devai.tutorials.app.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagUtil {
    public static List<Tag> convert(List<String> input) {
        List<Tag> output = new ArrayList<>();
        input.stream().forEach(i -> output.add(new Tag(null, i, null)));
        return output;
    }
}
