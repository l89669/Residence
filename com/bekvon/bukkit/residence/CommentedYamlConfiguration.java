/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 *  org.apache.commons.lang.StringEscapeUtils
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.bekvon.bukkit.residence;

import com.google.common.io.Files;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.configuration.file.YamlConfiguration;

public class CommentedYamlConfiguration
extends YamlConfiguration {
    private HashMap<String, String> comments = new HashMap();

    public void save(String file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.save(new File(file));
    }

    public void save(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        Files.createParentDirs((File)file);
        String data = this.insertComments(this.saveToString());
        data = data.replace("\\x", "\\u00");
        data = StringEscapeUtils.unescapeJava((String)data);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file), StandardCharsets.UTF_8));
        try {
            writer.write(data);
        }
        finally {
            writer.close();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private String insertComments(String yaml) {
        if (this.comments.isEmpty() != false) return yaml;
        yamlContents = yaml.split("[" + System.getProperty("line.separator") + "]");
        newContents = new StringBuilder();
        currentPath = new StringBuilder();
        commentedPath = false;
        node = false;
        depth = 0;
        firstLine = true;
        var12_9 = yamlContents;
        var11_10 = var12_9.length;
        var10_11 = 0;
        while (var10_11 < var11_10) {
            line = var12_9[var10_11];
            if (!firstLine) ** GOTO lbl17
            firstLine = false;
            if (line.startsWith("#")) ** GOTO lbl70
lbl17: // 2 sources:
            if (!line.contains(": ") && (line.length() <= 1 || line.charAt(line.length() - 1) != ':')) ** GOTO lbl30
            commentedPath = false;
            node = true;
            index = 0;
            index = line.indexOf(": ");
            if (index < 0) {
                index = line.length() - 1;
            }
            if (!currentPath.toString().isEmpty()) ** GOTO lbl27
            currentPath = new StringBuilder(line.substring(0, index));
            ** GOTO lbl61
lbl27: // 1 sources:
            whiteSpace = 0;
            n = 0;
            ** GOTO lbl35
lbl30: // 1 sources:
            node = false;
            ** GOTO lbl61
            while (line.charAt(n) == ' ') {
                ++whiteSpace;
                ++n;
lbl35: // 2 sources:
                if (n < line.length()) continue;
            }
            if (whiteSpace / 2 <= depth) ** GOTO lbl40
            currentPath.append(".").append(line.substring(whiteSpace, index));
            ++depth;
            ** GOTO lbl61
lbl40: // 1 sources:
            if (whiteSpace / 2 >= depth) ** GOTO lbl44
            newDepth = whiteSpace / 2;
            i = 0;
            ** GOTO lbl53
lbl44: // 1 sources:
            lastIndex = currentPath.lastIndexOf(".");
            if (lastIndex < 0) {
                currentPath = new StringBuilder();
            } else {
                currentPath.replace(currentPath.lastIndexOf("."), currentPath.length(), "").append(".");
            }
            currentPath.append(line.substring(whiteSpace, index));
            ** GOTO lbl61
lbl-1000: // 1 sources:
            {
                currentPath.replace(currentPath.lastIndexOf("."), currentPath.length(), "");
                ++i;
lbl53: // 2 sources:
                ** while (i < depth - newDepth)
            }
lbl54: // 1 sources:
            lastIndex = currentPath.lastIndexOf(".");
            if (lastIndex < 0) {
                currentPath = new StringBuilder();
            } else {
                currentPath.replace(currentPath.lastIndexOf("."), currentPath.length(), "").append(".");
            }
            currentPath.append(line.substring(whiteSpace, index));
            depth = newDepth;
lbl61: // 5 sources:
            newLine = new StringBuilder(line);
            if (node) {
                comment = null;
                if (!commentedPath && (comment = this.comments.get(currentPath.toString())) != null && !comment.isEmpty()) {
                    newLine.insert(0, System.getProperty("line.separator")).insert(0, comment);
                    comment = null;
                    commentedPath = true;
                }
            }
            newLine.append(System.getProperty("line.separator"));
            newContents.append(newLine.toString());
lbl70: // 2 sources:
            ++var10_11;
        }
        return newContents.toString();
    }

    public /* varargs */ void addComment(String path, String ... commentLines) {
        StringBuilder commentstring = new StringBuilder();
        String leadingSpaces = "";
        int n = 0;
        while (n < path.length()) {
            if (path.charAt(n) == '.') {
                leadingSpaces = String.valueOf(leadingSpaces) + "  ";
            }
            ++n;
        }
        String[] arrstring = commentLines;
        int n2 = arrstring.length;
        int n3 = 0;
        while (n3 < n2) {
            String line = arrstring[n3];
            if (!line.isEmpty()) {
                line = String.valueOf(leadingSpaces) + "# " + line;
            }
            if (commentstring.length() > 0) {
                commentstring.append(System.getProperty("line.separator"));
            }
            commentstring.append(line);
            ++n3;
        }
        this.comments.put(path, commentstring.toString());
    }
}

