package ObjectClasses.GUI;



import ObjectClasses.ScheduleAlgorithm.GAFunctions;
import ObjectClasses.ScheduleAlgorithm.Individual;
import ObjectClasses.TimeTable.ClassSchedule;
import ObjectClasses.Users.GlobalsTemp;

import javax.swing.*;
import java.awt.*;

public class TestGUI {

    public static void main(String[] args) {

        GAFunctions gaFunctions = new GAFunctions(GlobalsTemp.POPULATION_SIZE, 1);
        gaFunctions.initializePopulation();
        gaFunctions.evolve();
        for (int i = 0; i < GlobalsTemp.POPULATION_SIZE; i++)
        {
            System.out.println("Individual number:" + i + " grade:" + gaFunctions.returnFitness(i) + " pick probability:" + gaFunctions.getPickProbability(i));
        }




        Individual child = new Individual(gaFunctions.getBestIndividual());




        ClassSchedule scheduleExample = new ClassSchedule(child.getClassSchedule());
        //scheduleExample.fillScheduleRandomly();
        String[][] schedule = scheduleExample.returnDisplaySchedule();

        NoEditTableModel model = new NoEditTableModel(schedule, new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"});

        JTable table = new JTable(model);
        table.setBounds(0, 0, 550, 750);
        table.setRowHeight(50);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setLayout(null);
        table.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(210, 0, 550, 750);



        JPanel menupanel = new JPanel();
        menupanel.setBackground(Color.gray);
        menupanel.setBounds(0, 0, 200, 750);
        menupanel.setLayout(null);

        JButton classView = new JButton("Class View");
        classView.setBounds(10, 25, 180, 50);
        classView.setBackground(Color.white);
        classView.setBorderPainted(true);
        classView.setFocusPainted(false);

        JButton teacherView = new JButton("Teacher View");
        teacherView.setBounds(10, 100, 180, 50);
        teacherView.setBackground(Color.white);
        teacherView.setBorderPainted(true);
        teacherView.setFocusPainted(false);


        menupanel.add(classView);
        menupanel.add(teacherView);



        JFrame frame = new JFrame();
        frame.setSize(790, 750  );
        frame.setTitle("Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.add(menupanel);
        frame.add(scrollPane);
        frame.setVisible(true);
    }



}