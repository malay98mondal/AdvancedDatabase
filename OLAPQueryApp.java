package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OLAPQueryApp extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/samplesales";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "malay";
    // ... other variables and components
    private JComboBox<String> queryComboBox;
    private JTextArea resultTextArea;
    private JTextField ageTextField;
    private JPanel agePanel;
    public OLAPQueryApp() {
        // ... constructor code
    	 setTitle("OLAP Query Application");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setSize(600, 400);

         queryComboBox = new JComboBox<>(new String[]{
                 "1. Write a OLAP query to provide total sales by product_category and year",
                 "2. Write a OLAP query to provide total sales by product_category, month and year",
                 "3. Write a OLAP query to provide total sales by product_country, sales_city, and customer_nationality",
                 "4. Write a OLAP query to provide total sales by product_category, sale_city, and customer_age",
                 "5. Write a OLAP query to provide total sales by product_category, customer_age, customer_gender, customer_nationality",
                 "6. Write a OLAP query to provide total sales by product_country, sales_city, customer_nationality according to different customer_age"
         });
         queryComboBox.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String selectedQuery = (String) queryComboBox.getSelectedItem();
                 if (selectedQuery.equals("6. Write a OLAP query to provide total sales by product_country, sales_city, customer_nationality according to different customer_age")) {
                     agePanel.setVisible(true);
                 } else {
                     agePanel.setVisible(false);
                 }
             }
         });

         agePanel = new JPanel(new BorderLayout());
         agePanel.setVisible(false);
         ageTextField = new JTextField(10);
         agePanel.add(new JLabel("Age:"), BorderLayout.WEST);
         agePanel.add(ageTextField, BorderLayout.CENTER);

         JButton executeButton = new JButton("Execute");
   // Set a custom background color for the execute button
         
         executeButton.setBackground(Color.CYAN); // Custom background color
         executeButton.setForeground(Color.BLUE);
         executeButton.setFocusPainted(false);
         executeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
         executeButton.setFont(executeButton.getFont().deriveFont(Font.BOLD));
         // Add some padding to the execute button
         executeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
         executeButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 executeQuery();
             }
         });

         JPanel panel = new JPanel(new BorderLayout());
         panel.setBackground(new Color(240, 240, 240));
         panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         panel.add(queryComboBox, BorderLayout.NORTH);
         panel.add(agePanel, BorderLayout.CENTER);
         panel.add(executeButton, BorderLayout.SOUTH);
         
         JPanel bodyPanel = new JPanel(new BorderLayout());
         bodyPanel.setBackground(Color.BLACK); // Set a custom background color for the body panel
         bodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         bodyPanel.add(panel, BorderLayout.NORTH);
  
  

         resultTextArea = new JTextArea();
         resultTextArea.setEditable(false);
         resultTextArea.setBackground(Color.pink);
         resultTextArea.setForeground(Color.BLACK);
         resultTextArea.setFont(resultTextArea.getFont().deriveFont(20f)); // Increase the font size (16f) as desired
         JScrollPane scrollPane = new JScrollPane(resultTextArea);

         scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

         // Set a custom background color for the main frame
     
         add(panel, BorderLayout.NORTH);
         add(scrollPane, BorderLayout.CENTER);
     
    }

    private void executeQuery() {
        // ... executeQuery() method code
    	  String selectedQuery = (String) queryComboBox.getSelectedItem();
          String query = "";

          switch (selectedQuery) {
              case "1. Write a OLAP query to provide total sales by product_category and year":
                  query = "SELECT p.category, YEAR(s.sale_date) as year, SUM(s.quantity * p.price) as total_sales\r\n"
                  		+ "FROM sales s\r\n"
                  		+ "JOIN products p ON s.product_id = p.product_id\r\n"
                  		+ "GROUP BY p.category, YEAR(s.sale_date)";
                  break;
              case "2. Write a OLAP query to provide total sales by product_category, month and year":
                  query =  "SELECT p.category, MONTH(s.sale_date) as month, YEAR(s.sale_date) as year, SUM(s.quantity * p.price) as total_sales\n" +
                          "FROM sales s\n" +
                          "JOIN products p ON s.product_id = p.product_id\n" +
                          "GROUP BY p.category, MONTH(s.sale_date), YEAR(s.sale_date)";
                  break;
              case "3. Write a OLAP query to provide total sales by product_country, sales_city, and customer_nationality":
                  query = "SELECT p.product_country, s.sale_city, s.customer_nationality, SUM(s.quantity * p.price) as total_sales\r\n"
                  		+ "FROM sales s\r\n"
                  		+ "JOIN products p ON s.product_id = p.product_id\r\n"
                  		+ "GROUP BY p.product_country, s.sale_city, s.customer_nationality";
                  break;
              case "4. Write a OLAP query to provide total sales by product_category, sale_city, and customer_age":
                  query = "SELECT p.category, s.sale_city, s.customer_age, SUM(s.quantity * p.price) as total_sales\r\n"
                  		+ "FROM sales s\r\n"
                  		+ "JOIN products p ON s.product_id = p.product_id\r\n"
                  		+ "GROUP BY p.category, s.sale_city, s.customer_age";
                  break;
              case "5. Write a OLAP query to provide total sales by product_category, customer_age, customer_gender, customer_nationality":
                  query = "SELECT p.category, s.customer_age, s.customer_gender, s.customer_nationality, SUM(s.quantity * p.price) as total_sales\r\n"
                  		+ "FROM sales s\r\n"
                  		+ "JOIN products p ON s.product_id = p.product_id\r\n"
                  		+ "GROUP BY p.category, s.customer_age, s.customer_gender, s.customer_nationality;";
                  break;
                  
                  
              case "6. Write a OLAP query to provide total sales by product_country, sales_city, customer_nationality according to different customer_age":
                  String age = ageTextField.getText();
                  query = "CALL get_SalesByAge(" + age + ")";
                  break;
          }

          try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        	         Statement statement = connection.createStatement();
        	         ResultSet resultSet = statement.executeQuery(query)) {

        	        StringBuilder result = new StringBuilder();
        	        ResultSetMetaData metaData = resultSet.getMetaData();
        	        int columnCount = metaData.getColumnCount();

        	        for (int i = 1; i <= columnCount; i++) {
        	            result.append(String.format("%-20s", metaData.getColumnName(i)));
        	        }
        	        result.append("\n");

        	        boolean dataFound = false; // Flag to check if data is found

        	        while (resultSet.next()) {
        	            dataFound = true; // Set flag to true if data is found
        	            for (int i = 1; i <= columnCount; i++) {
        	                result.append(String.format("%-20s", resultSet.getString(i)));
        	            }
        	            result.append("\n");
        	        }

        	        if (!dataFound) {
        	            result.append("Data not found."); // Append message if data is not found
        	        }

        	        resultTextArea.setText(result.toString());

        	    } catch (SQLException e) {
        	        e.printStackTrace();
        	        JOptionPane.showMessageDialog(this, "Please Enter Age: ", "Error", JOptionPane.ERROR_MESSAGE);
        	    }
        	}
}
