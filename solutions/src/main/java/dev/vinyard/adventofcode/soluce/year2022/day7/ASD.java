package dev.vinyard.adventofcode.soluce.year2022.day7;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class ASD {

    public static class Root {

        private final LinkedList<Command> commands;

        public Root(LinkedList<Command> commands) {
            this.commands = commands;
        }

        public long part1() {
            PromptVisitor promptVisitor = new PromptVisitor();
            commands.forEach(command -> command.accept(promptVisitor));

            Folder root = promptVisitor.getRoot();

            Set<Folder> folders = root.getFolders();

            return folders.stream().filter(f -> f.getSize() <= 100000).mapToLong(Folder::getSize).sum();
        }

        public long part2() {
            PromptVisitor promptVisitor = new PromptVisitor();
            commands.forEach(command -> command.accept(promptVisitor));

            Folder root = promptVisitor.getRoot();

            long totalSpace = 70_000_000;
            long neededSpace = 30_000_000;
            long usedSpace = root.getSize();
            long freeSpace = totalSpace - usedSpace;
            long needToFree = neededSpace - freeSpace;

            Set<Folder> folders = root.getFolders();

            return folders.stream().filter(f -> f.getSize() >= needToFree).mapToLong(Folder::getSize).min().orElseThrow();
        }
    }

    public static class Folder {

        private final String name;

        @Getter
        private final Folder parent;

        private final LinkedList<Folder> folders = new LinkedList<>();
        private final LinkedList<File> files = new LinkedList<>();

        public Folder(Folder parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        public void addFile(File file) {
            this.files.add(file);
        }

        public void addFolder(Folder folder) {
            this.folders.add(folder);
        }

        public Folder getFolder(String name) {
            return this.folders.stream().filter(f -> f.name.equals(name)).findFirst().orElseThrow();
        }

        public long getSize() {
            return files.stream().mapToLong(f -> f.size).sum() + folders.stream().mapToLong(Folder::getSize).sum();
        }

        public Set<Folder> getFolders() {
            Set<Folder> allFolders = new HashSet<>(folders);
            for (Folder folder : folders) {
                allFolders.addAll(folder.getFolders());
            }
            return allFolders;
        }
    }

    public static class File {
        private final long size;

        public File(String name, long size) {
            this.size = size;
        }
    }

    public static class PromptVisitor {

        @Getter
        private final Folder root = new Folder(null, "/");

        private Folder currentFolder;

        public void visit(CdCommand cdCommand) {
            switch (cdCommand.getPath()) {
                case "/" -> currentFolder = root;
                case ".." -> currentFolder = currentFolder.getParent();
                default -> currentFolder = currentFolder.getFolder(cdCommand.getPath());
            }
        }

        public void visit(LsCommand lsCommand) {
            // No action needed for ls command itself
        }

        public void visit(DirCommand dirCommand) {
            currentFolder.addFolder(new Folder(currentFolder, dirCommand.getName()));
        }

        public void visit(FileCommand fileCommand) {
            currentFolder.addFile(new File(fileCommand.getName(), fileCommand.getSize()));
        }
    }

    public interface CommandVisitor {
        void accept(PromptVisitor promptVisitor);
    }

    public static abstract class Command implements CommandVisitor {
        public abstract void accept(PromptVisitor promptVisitor);
    }

    public static class CdCommand extends Command {

        @Getter
        private final String path;

        public CdCommand(String path) {
            this.path = path;
        }

        @Override
        public void accept(PromptVisitor promptVisitor) {
            promptVisitor.visit(this);
        }
    }

    public static class LsCommand extends Command {

        @Override
        public void accept(PromptVisitor promptVisitor) {
            promptVisitor.visit(this);
        }
    }

    public static class FileCommand extends Command {

        @Getter
        private final long size;

        @Getter
        private final String name;

        public FileCommand(long size, String name) {
            this.size = size;
            this.name = name;
        }

        @Override
        public void accept(PromptVisitor promptVisitor) {
            promptVisitor.visit(this);
        }
    }

    public static class DirCommand extends Command {

        @Getter
        private final String name;

        public DirCommand(String name) {
            this.name = name;
        }

        @Override
        public void accept(PromptVisitor promptVisitor) {
            promptVisitor.visit(this);
        }
    }

}
