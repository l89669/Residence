/*
 * Decompiled with CFR 0_119.
 */
package com.bekvon.bukkit.residence;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.CommandStatus;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CommandFiller {
    public final String packagePath = "com.bekvon.bukkit.residence.commands";
    public Map<String, CommandStatus> CommandList = new HashMap<String, CommandStatus>();

    public List<String> getCommands(Boolean simple) {
        Map cmd2 = new HashMap<String, Integer>();
        for (Map.Entry<String, CommandStatus> one : this.CommandList.entrySet()) {
            if (simple.booleanValue() && !one.getValue().getSimple().booleanValue() || !simple.booleanValue() && one.getValue().getSimple().booleanValue()) continue;
            cmd2.put(one.getKey(), one.getValue().getPriority());
        }
        cmd2 = Residence.getSortingManager().sortByValueASC(cmd2);
        ArrayList<String> cmdList = new ArrayList<String>();
        for (Map.Entry one : cmd2.entrySet()) {
            cmdList.add((String)one.getKey());
        }
        return cmdList;
    }

    public List<String> getCommands() {
        Map cmd2 = new HashMap<String, Integer>();
        for (Map.Entry<String, CommandStatus> one : this.CommandList.entrySet()) {
            cmd2.put(one.getKey(), one.getValue().getPriority());
        }
        cmd2 = Residence.getSortingManager().sortByValueASC(cmd2);
        ArrayList<String> cmdList = new ArrayList<String>();
        for (Map.Entry one : cmd2.entrySet()) {
            cmdList.add((String)one.getKey());
        }
        return cmdList;
    }

    public Map<String, CommandStatus> fillCommands() {
        List lm2 = new ArrayList();
        HashMap classes = new HashMap();
        try {
            lm2 = CommandFiller.getClassesFromPackage("com.bekvon.bukkit.residence.commands");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (String one : lm2) {
            Class newclass = this.getClass(one);
            if (newclass == null) continue;
            classes.put(one, newclass);
        }
        for (Map.Entry OneClass : classes.entrySet()) {
            boolean found = false;
            Method[] arrmethod = ((Class)OneClass.getValue()).getMethods();
            int n = arrmethod.length;
            int n2 = 0;
            while (n2 < n) {
                Method met = arrmethod[n2];
                if (met.isAnnotationPresent(CommandAnnotation.class)) {
                    found = true;
                    Boolean simple = met.getAnnotation(CommandAnnotation.class).simple();
                    int Priority = met.getAnnotation(CommandAnnotation.class).priority();
                    String cmd2 = (String)OneClass.getKey();
                    this.CommandList.put(cmd2, new CommandStatus(simple, Priority));
                    break;
                }
                ++n2;
            }
            if (found) continue;
            this.CommandList.put((String)OneClass.getKey(), new CommandStatus(true, 1000));
        }
        return this.CommandList;
    }

    public static List<String> getClassesFromPackage(String pckgname) throws ClassNotFoundException {
        ArrayList<String> result = new ArrayList<String>();
        try {
            URL[] arruRL = ((URLClassLoader)Residence.class.getClassLoader()).getURLs();
            int n = arruRL.length;
            int n2 = 0;
            while (n2 < n) {
                URL jarURL = arruRL[n2];
                try {
                    result.addAll(CommandFiller.getClassesInSamePackageFromJar(pckgname, jarURL.toURI().getPath()));
                }
                catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
                ++n2;
            }
        }
        catch (NullPointerException x) {
            throw new ClassNotFoundException(String.valueOf(pckgname) + " does not appear to be a valid package (Null pointer exception)");
        }
        return result;
    }

    private static List<String> getClassesInSamePackageFromJar(String packageName, String jarPath) {
        ArrayList<String> listOfCommands;
        block14 : {
            JarFile jarFile = null;
            listOfCommands = new ArrayList<String>();
            try {
                try {
                    jarFile = new JarFile(jarPath);
                    Enumeration<JarEntry> en = jarFile.entries();
                    while (en.hasMoreElements()) {
                        JarEntry entry = en.nextElement();
                        String entryName = entry.getName();
                        packageName = packageName.replace(".", "/");
                        if (entryName == null || !entryName.endsWith(".class") || !entryName.startsWith(packageName)) continue;
                        String name = entryName.replace(packageName, "").replace(".class", "").replace("/", "");
                        if (name.contains("$")) {
                            name = name.split("\\$")[0];
                        }
                        listOfCommands.add(name);
                    }
                }
                catch (Exception en) {
                    if (jarFile == null) break block14;
                    try {
                        jarFile.close();
                    }
                    catch (Exception exception) {}
                }
            }
            finally {
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    }
                    catch (Exception exception) {}
                }
            }
        }
        return listOfCommands;
    }

    private Class<?> getClass(String cmd2) {
        Class nmsClass = null;
        try {
            nmsClass = Class.forName("com.bekvon.bukkit.residence.commands." + cmd2.toLowerCase());
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return nmsClass;
    }
}

