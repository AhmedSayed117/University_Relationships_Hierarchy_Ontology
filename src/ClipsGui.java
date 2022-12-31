import net.sf.clipsrules.jni.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClipsGui extends JFrame{

    private JCheckBox B1;
    private JCheckBox B2;
    private JCheckBox B3;

    private JCheckBox B4;
    private JCheckBox B5;
    private JButton Run;
    private JPanel MainPanel;
    popup p1 ;
    popup p2 ;
    popup p3 ;
    popup p4 ;
    popup p5 ;
    popupOutput o1 ;
    popupOutput o2 ;
    popupOutput o3 ;
    popupOutput o5 ;
    popupOutput o4 ;

    public void clearFile(String str){
        try {
            new PrintWriter(str).close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String readfile(String str){
        StringBuilder data= new StringBuilder();
        try {
            File myObj = new File(str);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                 data.append(myReader.nextLine());
                 data.append("\n");
            }
            myReader.close();
            return data.toString();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data.toString();
    }

    public ClipsGui(){
        super();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(MainPanel);

        MainPanel.setPreferredSize( new Dimension( 600, 150 ) );
        this.pack();

        B1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (B1.isSelected()){
                    p1 = new popup("Department name","Enter Department Name To get All TAs ");
                    if (o1!=null) o1.dispose();
                }
            }
        });

        B2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (B2.isSelected()){
                    p2 = new popup("Student name","Enter Student name To get All courses ");
                    if (o2!=null) o2.dispose();
                }
            }
        });

        B3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (B3.isSelected()){
                    p3 = new popup("Faculty name","Enter Faculty name To get all courses");
                    if (o3!=null) o3.dispose();
                }
            }
        });

        B4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (B4.isSelected()){
                    p4 = new popup("Faculty name","Enter Faculty name to get All Lecturers ");
                    if (o4!=null) o4.dispose();
                }
            }
        });

        B5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (B5.isSelected()){
                    p5 = new popup("University name","Enter University name To get All faculties ");
                    if (o5!=null) o5.dispose();
                }
            }
        });

        Run.addActionListener(new ActionListener(){
            final Environment clips = new Environment();

            @Override
            public void actionPerformed(ActionEvent e) {
                clips.clear();
                clips.load("clips.clp");
                clips.reset();

                if (p1!=null && !p1.ReturnInput().isEmpty()){
                    clips.assertString("(dep " + p1.ReturnInput()  + ")");
                    clearFile("GUI1.txt");
                }
                if (p2!=null && !p2.ReturnInput().isEmpty()){
                    clips.assertString("(stu " + p2.ReturnInput()  + ")");
                    clearFile("GUI2.txt");

                }
                if (p3!=null && !p3.ReturnInput().isEmpty()){
                    clips.assertString("(Fac " +"\""+ p3.ReturnInput() +"\"" + ")");
                    clearFile("GUI3.txt");
                }
                if (p4!=null && !p4.ReturnInput().isEmpty()){
                    clips.assertString("(FacL " +"\""+ p4.ReturnInput() +"\"" + ")");
                    clearFile("GUI4.txt");
                }
                if (p5!=null && !p5.ReturnInput().isEmpty()){
                    clips.assertString("(University " +"\""+ p5.ReturnInput() +"\"" + ")");
                    clearFile("GUI5.txt");
                }
                if (p1!=null && p2!=null && p3!=null && p4!=null && p5!=null && !p1.ReturnInput().isEmpty()&& !p2.ReturnInput().isEmpty()&& !p3.ReturnInput().isEmpty()&& !p4.ReturnInput().isEmpty()&& !p5.ReturnInput().isEmpty()){
                    clips.run();

                    String t = readfile("GUI1.txt");
                    o1 = new popupOutput("All TAs in "+ p1.ReturnInput() +" Department");
                    o1.SetText(t);

                    String t2 = readfile("GUI2.txt");
                    o2 = new popupOutput("All courses that "+p2.ReturnInput()+ " currently studying");
                    o2.SetText(t2);

                    String t3 = readfile("GUI3.txt");
                    o3 = new popupOutput("All courses offered by "+p3.ReturnInput());
                    o3.SetText(t3);

                    String t4 = readfile("GUI4.txt");
                    o4 = new popupOutput("All Lecturers in "+p4.ReturnInput());
                    o4.SetText(t4);

                    String t5 = readfile("GUI5.txt");
                    o5 = new popupOutput("All faculties in "+p5.ReturnInput());
                    o5.SetText(t5);

                }
            }
        });
        }
}