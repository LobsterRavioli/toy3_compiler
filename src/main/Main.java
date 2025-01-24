package main;

import main.java.compiler.Lexer;
import main.java.compiler.Parser;
import main.node.program.ProgramOperationNode;
import main.visitors.ScopeVisitor;
import main.visitors.TranslationVisitor;
import main.visitors.TypeCheckerVisitor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

public class Main {
    //ciao
    public static void main(String[] args) throws Exception {
        String outputFolder = "test_files/c_out";
        Path inputPath = Paths.get(args[0]);
        String inputFileName = inputPath.getFileName().toString();
        String CinputFileName = FilenameUtils.removeExtension(inputFileName) + ".c";

        Lexer lexer = new Lexer(new FileReader(inputPath.toString()));
        Parser p = new Parser(lexer);
        ProgramOperationNode pr = (ProgramOperationNode) p.parse().value;
        ScopeVisitor scopeVisitor = new ScopeVisitor();
        pr.accept(scopeVisitor);
        TypeCheckerVisitor visitor = new TypeCheckerVisitor();
        pr.accept(visitor);
        FileWriter fileWriter = new FileWriter(outputFolder + File.separator + CinputFileName);
        TranslationVisitor translationVisitor = new TranslationVisitor();
        String codeC = (String) pr.accept(translationVisitor);
        fileWriter.append(codeC);
        fileWriter.close();

    }

}