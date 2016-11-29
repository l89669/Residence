/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.yaml.snakeyaml.DumperOptions
 *  org.yaml.snakeyaml.DumperOptions$FlowStyle
 *  org.yaml.snakeyaml.Yaml
 *  org.yaml.snakeyaml.reader.ReaderException
 */
package com.bekvon.bukkit.residence.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.ReaderException;

public class YMLSaveHelper {
    File f;
    Yaml yml;
    Map<String, Object> root;

    public YMLSaveHelper(File ymlfile) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        this.yml = new Yaml(options);
        this.root = new LinkedHashMap<String, Object>();
        if (ymlfile == null) {
            throw new IOException("YMLSaveHelper: null file...");
        }
        this.f = ymlfile;
    }

    public void save() throws IOException {
        if (this.f.isFile()) {
            this.f.delete();
        }
        FileOutputStream fout = new FileOutputStream(this.f);
        OutputStreamWriter osw = new OutputStreamWriter((OutputStream)fout, "UTF8");
        this.yml.dump(this.root, (Writer)osw);
        osw.close();
    }

    public void load() throws IOException {
        FileInputStream fis = new FileInputStream(this.f);
        InputStreamReader isr = new InputStreamReader((InputStream)fis, "UTF8");
        try {
            this.root = (Map)this.yml.load((Reader)isr);
        }
        catch (ReaderException e) {
            System.out.println("[Residence] - Failed to load " + this.yml.getName() + " file!");
        }
        isr.close();
    }

    public Map<String, Object> getRoot() {
        return this.root;
    }
}

