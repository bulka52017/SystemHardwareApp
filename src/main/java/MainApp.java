import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.ExecutingCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainApp {
//    OSHI OBJECTS

    private SystemInfo systemInfo = new SystemInfo();
    private HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
    CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
    PowerSource[] powerSource = hardwareAbstractionLayer.getPowerSources();
    NetworkIF[] networkIFS = hardwareAbstractionLayer.getNetworkIFs();
    private ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();
    Firmware firmware = computerSystem.getFirmware();
    Baseboard baseboard = computerSystem.getBaseboard();

//    END OF OSHI OBJECTS


//    SWING OBJECTS

    private JPanel mainPanel;
    private JTabbedPane appTabs;
    private JPanel systemTab;
    private JPanel othersTab;
    private JLabel systemLabel;
    private JLabel manufacturerLabel;
    private JTabbedPane tabbedPane1;
    private JButton logButton;
    private JLabel serialLabel;

//    END OF OSHI OBJECTS

    private MainApp(){
        manufacturerLabel.setText("Manufacturer: " + operatingSystem.getManufacturer());
        systemLabel.setText("Operating system: " + operatingSystem.getFamily() + " " + operatingSystem.getVersion() + " " + operatingSystem.getBitness() + " bit");
        serialLabel.setText("Serial number: " + ExecutingCommand.getAnswerAt("wmic path softwarelicensingservice get OA3xOriginalProductKey", 2));

        logButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
                Date date = new Date(System.currentTimeMillis());
                String currentDate = formatter.format(date);
                try {
                    PrintWriter writer = new PrintWriter(currentDate + "-log" + ".txt", "UTF-8");
                    writer.write("*************** SYSTEM INFORMATIONS ***************\n");
                    writer.write("Manufacturer: " + operatingSystem.getManufacturer()+"\n");
                    writer.write("Operating system: " + operatingSystem.getFamily() + " " + operatingSystem.getVersion() + " " + operatingSystem.getBitness() + " bit");
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("System & Hardware Info");
        frame.setSize(1000,1000);
        frame.setContentPane(new MainApp().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

}
