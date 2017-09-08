import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JFrame;

public class MainMethod {
	static Logger logger = Logger.getLogger("MyLog");

	public static void main(String[] args) {
		if (args.length > 0) {
			logger.setLevel(args[0].equals("/log") ? Level.INFO : Level.OFF);
			FileHandler fh;

			try {
				fh = new FileHandler("src/boats.log");

				MainMethod.logger.addHandler(fh);
				SimpleFormatter formatter = new SimpleFormatter();
				fh.setFormatter(formatter);

				logger.info("application started");
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			}

		}

		JFrame f = new JFrame("Boat Hire Manager");
		MenuBar mainMenu = new MenuBar();
		f.setJMenuBar(mainMenu.genMenu());
		f.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				MainMethod.logger.info("application closed");
				System.exit(0);
			}
		});

		f.setSize(650, 600);
		f.setVisible(true);
	}

}