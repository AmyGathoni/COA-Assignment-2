/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decimalbinary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.Random;
/**
 *
 * @author g.k
 */

public class DecimalBinary extends JFrame {
    private final JTable table;
    private final DefaultTableModel tableModel;

    /**
     */

    public DecimalBinary() {
        setTitle("Decimal to Binary Converter");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"Decimal Notation", "Binary Notation", "Remarks"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        generateAndDisplayResults();
    }

    private void generateAndDisplayResults() {
        int numberOfNumbers = 30;
        double[] randomNumbers = generateRandomNumbers(numberOfNumbers);

        for (double number : randomNumbers) {
            String binary = decimalToBinary(number);
            String formattedNumber = formatNumber(number);

            // Checking for exact or approximate match
            String remark = binary.equals(decimalToBinary(Double.parseDouble(formatNumber(number))))
                    ? "Exactly" : "Approximately";

            tableModel.addRow(new Object[]{formattedNumber, binary, remark});
        }
    }

    private double[] generateRandomNumbers(int numberOfNumbers) {
        Random random = new Random();
        double[] randomNumbers = new double[numberOfNumbers];

        for (int i = 0; i < numberOfNumbers; i++) {
            double randomNumber = random.nextDouble() * 200.0; // Generate a random number between 0 and 1000
            randomNumber = Math.round(randomNumber * 1000.0) / 1000.0; // Round to three decimal points
            randomNumbers[i] = randomNumber;
        }

        return randomNumbers;
    }

    private String decimalToBinary(double number) {
        long wholePart = (long) number;
        double fractionalPart = number - wholePart;

        StringBuilder binary = new StringBuilder();
        binary.append(Long.toBinaryString(wholePart)).append('.');

        for (int i = 0; i < 2; i++) { // Convert up to five decimal places (nibbles)
            fractionalPart *= 2;
            long bit = (long) fractionalPart;
            binary.append(bit);
            fractionalPart -= bit;
        }

        return binary.toString();
    }

    private String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(number);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DecimalBinary().setVisible(true);
        });
    }
}
