import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JPanel implements ActionListener {
    protected  final String textFieldString = "IP Address";
    protected  final String buttonString = "Submit";

    protected JLabel actionLabel;

    protected  JTextArea textArea = new JTextArea(
            "                     Routing table\n" +
            "\n       Address         ||         Destination   " +
            "\n-----------------------------------------------------" +
            "\n135.46.56.0/22    ||          interface 0   " +
            "\n135.46.60.0/22    ||          interface 1   " +
            "\n192.53.40.0/23    ||            router 1    " +
            "\n       default            ||            router 2    ");
    protected  directIP IPAddress = new directIP();

    private GUI() {
        setLayout (new BorderLayout());

        //Create IP entry text field
        JTextField textField = new JTextField(15);
        textField.setActionCommand(textFieldString);
        textField.addActionListener(this);

        //Create Label for field
        JLabel textInputLabel = new JLabel(textFieldString + ": ");
        textInputLabel.setLabelFor(textField);

        //Create Label informing what to do
        actionLabel = new JLabel("Type an IP address to be searched and press Enter");
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        //Lay out text control and label
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        textControlsPane.setLayout(gridBag);

        JLabel[] labels = {textInputLabel};
        JTextField[] textFields = {textField};
        addLabelTextRows(labels, textFields, textControlsPane);

        c.gridwidth = GridBagConstraints.REMAINDER; //last
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        textControlsPane.add(actionLabel, c);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("IP Address Lookup"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        //Create a text area
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setPreferredSize(new Dimension(250,250));
        areaScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("IP Routing Results"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        areaScrollPane.getBorder()));
        textArea.setEditable(false);

        //Put it together
        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(textControlsPane,
                BorderLayout.PAGE_START);
        leftPane.add(areaScrollPane,
                BorderLayout.CENTER);
        add(leftPane, BorderLayout.LINE_START);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String prefix = "Displaying results for:  \"";
        String result;
        if (textFieldString.equals(e.getActionCommand())) {
            JTextField source = (JTextField)e.getSource();
            IPAddress.setSearchedIP(source.getText());
            result = IPAddress.findRoute();
            actionLabel.setText(prefix + source.getText() + "\"");

            textArea.setText("Routing from host: " + source.getText()
                    + "\nIP address in binary: \n" + IPAddress.getBinaryIP()
                    + "\n\nComparing to routing table\n/\n/\n/"
                    + "\nIP address with binary AND: \n" + IPAddress.getBinaryAndResult()
                    + "\n\nMatched routing table addr: \n" + IPAddress.getMatchedIP()
                    + "\nDestination: " + result);

        } else if (buttonString.equals(e.getActionCommand())) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container){
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;

        for(int i = 0; i < numLabels; i++){
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.0;
            container.add(labels[i], c);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }
    private static void createAndShowGUI(){
        //Create and set up window
        JFrame frame = new JFrame("IP route search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Add content
        frame.add(new GUI());

        //Display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            createAndShowGUI();

        });
    }
}
